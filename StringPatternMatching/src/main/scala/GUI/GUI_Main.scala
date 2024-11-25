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
  private var inputField = "" // Stores the user input
  private var isTyping = false // Tracks whether the user is typing in the input field
  private var message = "Output and messages will appear here." // Displays messages to the user
  private var trieVisualizer: VisualizeTrie = _ // Visualizer for the trie
  private val backButtonRect = (100, 400, 150, 40) // Back button location

  /**
   * Configures the settings for the Processing sketch, including window size.
   */
  override def settings(): Unit = {
    size(1400, 1000)
  }

  /**
   * Sets up the initial state of the GUI, including background color and text alignment.
   */
  override def setup(): Unit = {
    background(240)
    textAlign(CENTER, CENTER)
    textSize(14)
  }

  /**
   * Continuously draws the GUI components and handles the visualization logic.
   */
  override def draw(): Unit = {
    background(240)

    // Title
    fill(0)
    textSize(18)
    text("Hello! This is my project on Aho-Corasick Algorithm.", width / 2, 40)
    textSize(14)
    text("You can either run an example visualization or input your own strings to build a trie.", width / 2, 70)

    if (inMainMenu) {
      // Draw buttons and input field in the main menu
      drawButton(exampleButtonRect, "Run Example")
      drawInputField()
      drawButton(buildButtonRect, "Build Trie")

      // Display messages
      fill(0)
      textAlign(LEFT, TOP)
      text(message, 100, 320, width - 200, height - 400)
      loop()
    } else {
      // Display trie visualization
      trieVisualizer.visTrie()
      drawButton(backButtonRect, "Back")
      noLoop()
    }
  }

  /**
   * Draws a button with the given rectangle coordinates and label.
   *
   * @param rectangle Tuple containing x, y, width, and height of the button.
   * @param label     Text to display on the button.
   */
  private def drawButton(rectangle: (Int, Int, Int, Int), label: String): Unit = {
    val (x, y, w, h) = rectangle

    // Draw button background
    fill(200)
    stroke(0)
    strokeWeight(1)
    rect(x, y, w, h, 10)

    // Draw button label
    fill(0)
    textAlign(CENTER, CENTER)
    textSize(14)
    text(label, x + w / 2, y + h / 2)
  }

  /**
   * Draws an input field where the user can type custom strings for trie construction.
   */
  private def drawInputField(): Unit = {
    val x = 100
    val y = 200
    val w = 300
    val h = 40
    fill(255)
    rect(x, y, w, h, 5)
    fill(0)
    textAlign(LEFT, CENTER)
    text(if (isTyping) inputField + "|" else inputField, x + 5, y + h / 2)
  }

  /**
   * Handles mouse click events to interact with GUI components such as buttons and input fields.
   */
  override def mousePressed(): Unit = {
    if (isInside(mouseX, mouseY, exampleButtonRect)) {
      inMainMenu = false
      runExampleTrie()
    } else if (isInside(mouseX, mouseY, buildButtonRect)) {
      val keywords = inputField.split(",").map(_.trim).filter(_.nonEmpty).toList
      if (keywords.nonEmpty) {
        inMainMenu = false
        buildCustomTrie(keywords)
      } else {
        message = "Please enter valid keywords separated by commas!"
      }
    } else if (isInside(mouseX, mouseY, (100, 200, 300, 40))) {
      isTyping = true
    } else if (isInside(mouseX, mouseY, backButtonRect)) {
      goToMainMenu()
    } else {
      isTyping = false
    }
  }

  /**
   * Handles keyboard input for typing into the input field.
   */
  override def keyPressed(): Unit = {
    if (isTyping) {
      if (key == BACKSPACE && inputField.nonEmpty) {
        inputField = inputField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN) {
        inputField += key
      }
    }
  }

  /**
   * Checks if the mouse is inside a given rectangle.
   *
   * @param mx   Mouse x-coordinate.
   * @param my   Mouse y-coordinate.
   * @param rect Tuple containing x, y, width, and height of the rectangle.
   * @return True if the mouse is inside the rectangle, false otherwise.
   */
  private def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
    val (x, y, w, h) = rect
    mx >= x && mx <= x + w && my >= y && my <= y + h
  }

  /**
   * Runs the example visualization of the Aho-Corasick algorithm using predefined keywords.
   */
  private def runExampleTrie(): Unit = {
    val keywords = List("hers", "she", "his", "he")
    message = s"Running example trie with keywords: ${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
  }

  /**
   * Builds a custom trie based on user-provided keywords.
   *
   * @param keywords List of strings provided by the user for trie construction.
   */
  private def buildCustomTrie(keywords: List[String]): Unit = {
    message = s"Building trie with custom keywords: ${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
  }

  /**
   * Returns to the main menu, clearing the current trie visualization.
   */
  private def goToMainMenu(): Unit = {
    inMainMenu = true
    message = "Output and messages will appear here."
    trieVisualizer = null
    redraw()
  }
}
