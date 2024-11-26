package GUI

import processing.core.PApplet
import processing.core.PConstants.*
import Main.*

/**
 * The main entry point for the GUI application demonstrating the Aho-Corasick algorithm.
 * This object initializes and launches the main Processing sketch.
 */
object Gui_Main extends PApplet {
  /**
   * Entry point for the application.
   *
   * @param args Command-line arguments (not used).
   */
  def main(args: Array[String]): Unit = {
    val mainApp = new MainApp
    PApplet.runSketch(Array("Main"), mainApp)
  }
}

/**
 * The main Processing sketch that provides an interactive GUI for visualizing the Aho-Corasick algorithm.
 * The GUI includes a main menu where users can either run an example or input their own strings to build a trie.
 */
class MainApp extends PApplet {
  var inMainMenu = true
  private val exampleButtonRect = (100, 120, 150, 40) // x, y, width, height for the "Run Example" button
  private val buildButtonRect = (420, 200, 150, 40) // x, y, width, height for the "Build Trie" button
  private val backButtonRect = (100, 400, 150, 40) // Back button location
  private val stepButtonRect = (400, 600, 150, 40) // Step button location

  private var inputField = "" // Stores the user input for keywords
  private var searchTextField = "" // Stores the user input for SearchText
  private var isTypingInput = false // Tracks typing state for keywords
  private var isTypingSearchText = false // Tracks typing state for SearchText

  private var message = "Output and messages will appear here." // Displays messages to the user
  private var trieVisualizer: VisualizeTrie = _ // Visualizer for the trie
  private var VPM: VisualizePatternMatching = _

  override def settings(): Unit = {
    size(1400, 1000)
  }

  override def setup(): Unit = {
    background(240)
    textAlign(CENTER, CENTER)
    textSize(14)
  }

  override def draw(): Unit = {
    background(240)

    // Title
    fill(0)
    textSize(18)
    text("Hello! This is my project on Aho-Corasick Algorithm.", width / 2, 40)
    textSize(14)
    text("You can either run an example visualization or input your own strings to build a trie.", width / 2, 70)

    if (inMainMenu) {
      // Draw buttons and input fields in the main menu
      drawButton(exampleButtonRect, "Run Example")
      drawInputField("Keywords", inputField, 100, 200, isTypingInput)
      drawInputField("Search Text", searchTextField, 100, 300, isTypingSearchText)
      drawButton(buildButtonRect, "Build Trie")

      // Display messages
      fill(0)
      textAlign(LEFT, TOP)
      text(message, 100, 400, width - 200, height - 400)
      loop()
    } else {
      // Display trie visualization
      trieVisualizer.visTrie()
      VPM.step()
      drawButton(stepButtonRect, "Step")
      drawButton(backButtonRect, "Back")

      VPM.drawSearchText()
      noLoop()
      trieVisualizer.resetVis()
    }
  }

  private def drawButton(rectangle: (Int, Int, Int, Int), label: String): Unit = {
    val (x, y, w, h) = rectangle
    fill(200)
    stroke(0)
    strokeWeight(1)
    rect(x, y, w, h, 10)
    fill(0)
    textAlign(CENTER, CENTER)
    textSize(14)
    text(label, x + w / 2, y + h / 2)
  }

  private def drawInputField(label: String, fieldValue: String, x: Int, y: Int, isActive: Boolean): Unit = {
    val w = 300
    val h = 40

    // Label
    fill(0)
    textAlign(LEFT, CENTER)
    textSize(14)
    text(s"$label:", x, y - 20)

    // Input field
    fill(255)
    rect(x, y, w, h, 5)
    fill(0)
    textAlign(LEFT, CENTER)
    text(if (isActive) fieldValue + "|" else fieldValue, x + 5, y + h / 2)
  }

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
    } else if (isInside(mouseX, mouseY, (100, 200, 300, 40))) {
      isTypingInput = true
      isTypingSearchText = false
    } else if (isInside(mouseX, mouseY, (100, 300, 300, 40))) {
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

  override def keyPressed(): Unit = {
    if (isTypingInput) {
      if (key == BACKSPACE && inputField.nonEmpty) {
        inputField = inputField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN) {
        inputField += key
      }
    } else if (isTypingSearchText) {
      if (key == BACKSPACE && searchTextField.nonEmpty) {
        searchTextField = searchTextField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN) {
        searchTextField += key
      }
    }
  }

  private def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
    val (x, y, w, h) = rect
    mx >= x && mx <= x + w && my >= y && my <= y + h
  }

  private def runExampleTrie(): Unit = {
    val keywords = List("hers", "she", "his", "he")
    val searchText = "sherishers"
    message = s"Running example trie with keywords: ${keywords.mkString(", ")} and search text: $searchText"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchText, keywords)
  }

  private def buildCustomTrie(keywords: List[String], searchText: String): Unit = {
    message = s"Building trie with custom keywords: ${keywords.mkString(", ")} and search text: $searchText"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchText, keywords)
  }

  private def goToMainMenu(): Unit = {
    inMainMenu = true
    message = "Output and messages will appear here."
    trieVisualizer = null
    redraw()
  }
}
