package GUI

import processing.core.PApplet
import processing.core.PConstants.*
import Main.*

/**
 * The entry point for the Aho-Corasick visualization GUI.
 * This object launches the main Processing sketch.
 */
object Gui_Main extends PApplet {
  /**
   * Main entry point for the GUI application.
   * @param args Command-line arguments (not used).
   */
  def main(args: Array[String]): Unit = {
    val mainApp = new MainApp
    PApplet.runSketch(Array("Main"), mainApp)
  }
}

/**
 * Main application class for the Aho-Corasick visualization.
 * Provides an interactive GUI for running examples or building custom tries.
 */
class MainApp extends PApplet {
  // GUI State and Dimensions
  private var inMainMenu = true
  private val exampleButtonRect = (100, 150, 200, 50)
  private val buildButtonRect = (350, 150, 200, 50)
  private val backButtonRect = (100, 850, 200, 50)
  private val stepButtonRect = (350, 850, 200, 50)

  // Input Fields
  private var inputField = ""
  private var searchTextField = ""
  private var isTypingInput = false
  private var isTypingSearchText = false

  // Visualization and Messages
  private var searchString = ""
  private var message = "Output and messages will appear here."
  private var trieVisualizer: VisualizeTrie = _
  private var VPM: VisualizePatternMatching = _

  /**
   * Configures the canvas size.
   */
  override def settings(): Unit = {
    size(1400, 1000)
  }

  /**
   * Sets up the canvas background and text alignment.
   */
  override def setup(): Unit = {
    background(240)
    textAlign(CENTER, CENTER)
    textSize(14)
  }

  /**
   * The main rendering loop for the application.
   * Draws either the main menu or the visualization depending on the state.
   */
  override def draw(): Unit = {
    background(255)

    // Header
    fill(30, 144, 255)
    rect(0, 0, width, 100)
    fill(255)
    textSize(22)
    text("Aho-Corasick Algorithm Visualization", width / 2, 50)

    if (inMainMenu) {
      drawMainMenu()
    } else {
      drawVisualization()
    }
  }

  /**
   * Renders the main menu interface, including buttons and input fields.
   */
  private def drawMainMenu(): Unit = {
    // Subtitle
    fill(50)
    textSize(16)
    textAlign(CENTER)
    text("Select an option or enter your input below", width / 2, 120)

    // Buttons
    drawButton(exampleButtonRect, "Run Example", isInside(mouseX, mouseY, exampleButtonRect))
    drawButton(buildButtonRect, "Build Trie", isInside(mouseX, mouseY, buildButtonRect))

    // Input Fields
    drawInputField("Keywords", inputField, 100, 250, isTypingInput)
    drawInputField("Search Text", searchTextField, 100, 350, isTypingSearchText)

    // Message Section
    fill(70)
    textAlign(LEFT, TOP)
    text(message, 100, 450, width - 200, height - 550)

    drawFooter()
  }

  /**
   * Renders the visualization interface for the trie and pattern matching process.
   */
  private def drawVisualization(): Unit = {
    // Visualization Area
    fill(230)
    rect(50, 120, width - 100, height - 200, 10)

    trieVisualizer.visTrie()
    VPM.step()

    drawButton(stepButtonRect, "Step", isInside(mouseX, mouseY, stepButtonRect))
    drawButton(backButtonRect, "Back", isInside(mouseX, mouseY, backButtonRect))

    VPM.drawSearchText()
    VPM.drawKeywords()

    noLoop()
    trieVisualizer.resetVis()
  }

  /**
   * Draws a footer section with educational information.
   */
  private def drawFooter(): Unit = {
    fill(200)
    rect(0, height - 50, width, 50)
    fill(100)
    textSize(12)
    textAlign(CENTER, CENTER)
    text("Educational Tool for Understanding String Matching Algorithms", width / 2, height - 25)
  }

  /**
   * Draws a button with a hover effect.
   * @param rectangle Tuple containing the button's x, y, width, and height.
   * @param label Text label displayed on the button.
   * @param isHovered True if the mouse is hovering over the button.
   */
  private def drawButton(rectangle: (Int, Int, Int, Int), label: String, isHovered: Boolean): Unit = {
    val (x, y, w, h) = rectangle
    fill(if (isHovered) color(70, 130, 180) else 200)
    stroke(0)
    rect(x, y, w, h, 10)
    fill(255)
    textAlign(CENTER, CENTER)
    textSize(14)
    text(label, x + w / 2, y + h / 2)
  }

  /**
   * Draws an input field with a label and typing indicator.
   * @param label Label for the input field.
   * @param fieldValue Current value of the input field.
   * @param x X-coordinate for the input field.
   * @param y Y-coordinate for the input field.
   * @param isActive True if the field is currently active for typing.
   */
  private def drawInputField(label: String, fieldValue: String, x: Int, y: Int, isActive: Boolean): Unit = {
    val w = 600
    val h = 50

    // Label
    fill(50)
    textAlign(LEFT, CENTER)
    textSize(14)
    text(s"$label:", x, y - 30)

    // Input Field
    fill(255)
    stroke(if (isActive) color(30, 144, 255) else 150) // Highlight active field
    strokeWeight(2)
    rect(x, y, w, h, 10)

    fill(50)
    textAlign(LEFT, CENTER)
    text(if (isActive) fieldValue + "|" else fieldValue, x + 10, y + h / 2)
  }

  /**
   * Handles mouse clicks and performs actions based on button or input field interaction.
   */
  override def mousePressed(): Unit = {
    if (isInside(mouseX, mouseY, exampleButtonRect)) {
      inMainMenu = false
      runExampleTrie()
    } else if (isInside(mouseX, mouseY, buildButtonRect)) {
      val keywords = inputField.split(",").map(_.trim).filter(_.nonEmpty).toList
      if (keywords.nonEmpty && searchTextField.nonEmpty) {
        inMainMenu = false
        buildCustomTrie(keywords, searchTextField)
      } else {
        message = "Please enter valid keywords separated by commas and a non-empty Search Text!"
      }
    } else if (isInside(mouseX, mouseY, (100, 250, 600, 50))) {
      isTypingInput = true
      isTypingSearchText = false
    } else if (isInside(mouseX, mouseY, (100, 350, 600, 50))) {
      isTypingInput = false
      isTypingSearchText = true
    } else if (isInside(mouseX, mouseY, backButtonRect)) {
      goToMainMenu()
    } else if (isInside(mouseX, mouseY, stepButtonRect)) {
      redraw()
    } else {
      isTypingInput = false
      isTypingSearchText = false
    }
  }

  /**
   * Handles key input and updates the active input field.
   */
  override def keyPressed(): Unit = {
    if (isTypingInput) {
      if (key == BACKSPACE && inputField.nonEmpty) {
        inputField = inputField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN && key != TAB) {
        inputField += key
      }
    } else if (isTypingSearchText) {
      if (key == BACKSPACE && searchTextField.nonEmpty) {
        searchTextField = searchTextField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN && key != TAB) {
        searchTextField += key
      }
    }
  }

  /**
   * Checks if a point is within a rectangle.
   * @param mx X-coordinate of the point.
   * @param my Y-coordinate of the point.
   * @param rect Rectangle defined as a tuple (x, y, width, height).
   * @return True if the point is inside the rectangle, otherwise false.
   */
  private def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
    val (x, y, w, h) = rect
    mx >= x && mx <= x + w && my >= y && my <= y + h
  }

  /**
   * Runs a pre-defined example visualization of the Aho-Corasick algorithm.
   */
  private def runExampleTrie(): Unit = {
    val keywords = List("hers", "she", "his", "he")
    searchString = "sher is hers"
    message = s"${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchString, keywords)
  }

  /**
   * Builds a custom trie and sets up the visualization with user-defined keywords and search text.
   * @param keywords List of keywords to build the trie.
   * @param searchText Text to search using the trie.
   */
  private def buildCustomTrie(keywords: List[String], searchText: String): Unit = {
    message = s"${keywords.mkString(", ")}"
    searchString = searchText
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchString, keywords)
  }

  /**
   * Resets the state and returns to the main menu.
   */
  private def goToMainMenu(): Unit = {
    inMainMenu = true
    message = "Output and messages will appear here."
    trieVisualizer = null
    VPM = null
    isTypingInput = false
    isTypingSearchText = false
    redraw()
    loop() // Restart the draw loop for main menu interactivity
  }
}
