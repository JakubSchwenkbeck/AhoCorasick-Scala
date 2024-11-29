package GUI

import processing.core.PApplet
import processing.core.PConstants.*
import Main.*

object Gui_Main extends PApplet {
  def main(args: Array[String]): Unit = {
    val mainApp = new MainApp
    PApplet.runSketch(Array("Main"), mainApp)
  }
}

class MainApp extends PApplet {
  private var inMainMenu = true
  private val exampleButtonRect = (100, 150, 200, 50)
  private val buildButtonRect = (350, 150, 200, 50)
  private val backButtonRect = (100, 850, 200, 50)
  private val stepButtonRect = (350, 850, 200, 50)

  private var inputField = ""
  private var searchTextField = ""
  private var isTypingInput = false
  private var isTypingSearchText = false

  private var searchString = ""
  private var message = "Output and messages will appear here."
  private var trieVisualizer: VisualizeTrie = _
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

  private def drawFooter(): Unit = {
    fill(200)
    rect(0, height - 50, width, 50)
    fill(100)
    textSize(12)
    textAlign(CENTER, CENTER)
    text("Educational Tool for Understanding String Matching Algorithms", width / 2, height - 25)
  }

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


  private def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
    val (x, y, w, h) = rect
    mx >= x && mx <= x + w && my >= y && my <= y + h
  }

  private def runExampleTrie(): Unit = {
    val keywords = List("hers", "she", "his", "he")
    searchString = "sher is hers"
    message = s"${keywords.mkString(", ")}"
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchString, keywords)
  }

  private def buildCustomTrie(keywords: List[String], searchText: String): Unit = {
    message = s"${keywords.mkString(", ")}"
    searchString = searchText
    trieVisualizer = new VisualizeTrie(buildTrie(keywords), this)
    VPM = new VisualizePatternMatching(buildTrie(keywords), trieVisualizer.statePositions, this, searchString, keywords)
  }

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
