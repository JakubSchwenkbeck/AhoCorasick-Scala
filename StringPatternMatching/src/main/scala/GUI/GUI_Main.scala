package GUI
import processing.core.PApplet
import processing.event.MouseEvent
import processing.core.PConstants.*
import Main.*

object Gui_Main extends PApplet {
  def main(args: Array[String]): Unit = {
    var ma = new MainApp
    PApplet.runSketch(Array("Main"),ma)
  }

}
class MainApp extends PApplet {
  var inMainMenu = true
  private var exampleButtonRect = (100, 120, 150, 40) // x, y, width, height
  private var buildButtonRect = (420, 200, 150, 40)
  private var inputField = ""
  private var isTyping = false
  private var message = "Output and messages will appear here."
  private var trieVisualizer: VisualizeTrie = _
  private var backButtonRect = (100, 400, 150, 40) // Back button location

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

    if(inMainMenu) {
      // Draw Example Button
      drawButton(exampleButtonRect, "Run Example")

      // Draw Input Field
      drawInputField()

      // Draw Build Trie Button
      drawButton(buildButtonRect, "Build Trie")

      // Display messages
      fill(0)
      textAlign(LEFT, TOP)
      text(message, 100, 320, width - 200, height - 400)
      loop()
    }else{
      trieVisualizer.visTrie()
      drawButton(backButtonRect, "Back")
      noLoop()
    }
  }

  private def drawButton(rectangle: (Int, Int, Int, Int), label: String): Unit = {
    val (x, y, w, h) = rectangle

    // Draw button background
    fill(200) // Light gray color for the button
    stroke(0) // Black border
    strokeWeight(1) // Set a thin border
    rect(x, y, w, h, 10) // Rounded rectangle with 10px corner radius

    // Draw button label
    fill(0) // Black color for the text
    textAlign(CENTER, CENTER) // Center text horizontally and vertically
    textSize(14) // Set font size
    text(label, x + w / 2, y + h / 2) // Draw text in the center of the button
  }


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

  override def mousePressed(): Unit = {
    // Check if example button is clicked
    if (isInside(mouseX, mouseY, exampleButtonRect)) {
      inMainMenu = false
      runExampleTrie()
    }

    // Check if build button is clicked
    if (isInside(mouseX, mouseY, buildButtonRect)) {
      val keywords = inputField.split(",").map(_.trim).filter(_.nonEmpty).toList
      if (keywords.nonEmpty) {
        inMainMenu = false
        buildCustomTrie(keywords)
      } else {
        message = "Please enter valid keywords separated by commas!"
      }
    }

    // Check if input field is clicked
    if (isInside(mouseX, mouseY, (100, 200, 300, 40))) {
      isTyping = true
    } else {
      isTyping = false
    }
    // Check if back button is clicked
    if (isInside(mouseX, mouseY, backButtonRect)) {
      goToMainMenu()
    }
  }

  override def keyPressed(): Unit = {
    if (isTyping) {
      if (key == BACKSPACE && inputField.nonEmpty) {
        inputField = inputField.dropRight(1)
      } else if (key != BACKSPACE && key != ENTER && key != RETURN) {
        inputField += key
      }
    }
  }

  private def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
    val (x, y, w, h) = rect
    mx >= x && mx <= x + w && my >= y && my <= y + h
  }

  private def runExampleTrie(): Unit = {
    val keywords = List("hers", "she", "his", "he")
    message = s"Running example trie with keywords: ${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)

  }

  private def buildCustomTrie(keywords: List[String]): Unit = {
    message = s"Building trie with custom keywords: ${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)

  }

  // Function to switch back to the main menu
  private def goToMainMenu(): Unit = {
    inMainMenu = true // Switch to main menu
    message = "Output and messages will appear here."
    trieVisualizer = null // Clear the trie visualizer
    redraw() // Force a redraw of the main menu

  }
}
