package GUI

import GUI.RealWorldApplications.RealWorldMain
import GUI.UTILS.DrawingUtils.{drawButton, drawEducationalText, drawInputField}
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
  sealed trait AppState

  object AppState {
    case object MainMenu extends AppState
    case object TrieVis extends AppState
    case object RealWorldApp extends AppState
  }
  private var currentState: AppState = AppState.MainMenu


  private val exampleButtonRect = (100, 150, 200, 50)
  private val buildButtonRect = (350, 150, 200, 50)
  private val backButtonRect = (900, 800, 200, 50)
  private val stepButtonRect = (150, 800, 200, 50)
  private val RWButtonRect = (600,150,200,50)
  private val malwareButtonRect = (500,800,200,50)

  // Input Fields
  private var inputField = ""
  private var searchTextField = ""
  private var isTypingInput = false
  private var isTypingSearchText = false

  // Visualization and Messages
  private var searchString = ""
  private var message = ""
  private var trieVisualizer: VisualizeTrie = _
  private var VPM: VisualizePatternMatching = _
  private var RWM : RealWorldApplications.RealWorldMain = new RealWorldMain(this)
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


      currentState match {
        case AppState.MainMenu =>
          drawMainMenu()
        case AppState.TrieVis =>
          drawVisualization()
        case AppState.RealWorldApp =>
          drawRealWorld()
      }


  }


  /**
   * Renders the main menu interface, including buttons, input fields, and an educational overview.
   */
  private def drawMainMenu(): Unit = {
    // Subtitle
    fill(50)
    textSize(16)
    textAlign(CENTER)
    text("Select an option or enter your input below", width / 2, 120)

    // Buttons
    drawButton(this,exampleButtonRect, "Run Example", isInside(mouseX, mouseY, exampleButtonRect))
    drawButton(this,buildButtonRect, "Build Trie", isInside(mouseX, mouseY, buildButtonRect))
    drawButton(this,RWButtonRect,"Real World Examples",isInside(mouseX, mouseY, RWButtonRect))

    // Input Fields
    drawInputField(this,"Keywords", inputField, 100, 250, isTypingInput)
    drawInputField(this,"Search Text", searchTextField, 100, 350, isTypingSearchText)

    // Educational Overview Section
    fill(30, 144, 255)
    textAlign(LEFT, TOP)
    textSize(14)
    drawEducationalText(this)
    // Message Section
    fill(70)
    textAlign(LEFT, TOP)
    text(message, 100, 415, width - 200, height - 550)

    drawFooter()
  }


  /**
   * Renders the visualization interface for the trie and pattern matching process.
   */
  private def drawVisualization(): Unit = {
    // Visualization Area
    fill(230)
    noStroke()
    rect(50, 120, width - 180, height - 175, 10)
    // Header
    fill(30, 144, 255)
    rect(0, 0, width, 100)
    fill(255)
    textSize(22)
    text("Aho-Corasick Algorithm Visualization", width / 2, 50)

    drawFooter()

    trieVisualizer.visTrie()
    VPM.step()


    drawButton(this,stepButtonRect, "Step", isInside(mouseX, mouseY, stepButtonRect))
    drawButton(this,backButtonRect, "Back", isInside(mouseX, mouseY, backButtonRect))

    VPM.drawSearchText()
    VPM.drawKeywords()

    noLoop()
    trieVisualizer.resetVis()
  }




  private def drawRealWorld(): Unit = {

    RWM.draw()

    drawButton(this,backButtonRect, "Back", isInside(mouseX, mouseY, backButtonRect))
    if(RWM.currState != RWM.Malware) {
      drawButton(this, malwareButtonRect, "Malware Example", isInside(mouseX, mouseY, malwareButtonRect))
    }
    // noLoop()

  }





  /**
   * Draws a footer section with educational information.
   */
  private def drawFooter(): Unit = {
    fill(200)
    rect(0, height - 120, width, 50)
    fill(100)
    textSize(12)
    textAlign(CENTER, CENTER)
    text("Educational Tool for Understanding String Matching Algorithms", width / 2, height - 95)
  }



  /**
   * Handles mouse clicks and performs actions based on button or input field interaction.
   */
  override def mousePressed(): Unit = {
    if (isInside(mouseX, mouseY, exampleButtonRect)) {
      currentState = AppState.TrieVis
      runExampleTrie()
    } else if (isInside(mouseX, mouseY, buildButtonRect)) {
      val keywords = inputField.split(",").map(_.trim).filter(_.nonEmpty).toList
      if (keywords.nonEmpty && searchTextField.nonEmpty) {
        currentState = AppState.TrieVis

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
    } else if (isInside(mouseX, mouseY, RWButtonRect)) {
      currentState = AppState.RealWorldApp
    } else if (isInside(mouseX, mouseY, malwareButtonRect)) {
      RWM.setMalware()
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
    val keywords = List("sub", "in", "ext")
    val searchString = "searching for substrings in my text"

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
    currentState = AppState.MainMenu
    message = ""
    trieVisualizer = null
    VPM = null
    isTypingInput = false
    isTypingSearchText = false
    RWM.setHome()
    redraw()
    loop() // Restart the draw loop for main menu interactivity
  }
}
