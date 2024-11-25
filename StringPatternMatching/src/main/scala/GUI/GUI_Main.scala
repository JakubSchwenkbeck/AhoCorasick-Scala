package GUI
import processing.core.PApplet
import Main.*
object Gui_Main extends PApplet {
  def main(args: Array[String]): Unit = {

    val keywords = List("hers", "she", "his")

    val Vis = VisualizeTrie(buildTrie(keywords))
    PApplet.runSketch(Array("VisualizeTrie"),Vis)
  }
  
}



/*
class MySketch extends PApplet {
  var currentFunction: () => Unit = drawDefault // Reference to the current drawing function

  override def settings(): Unit = {
    size(800, 600) // Fenstergröße
  }
  override def setup(): Unit = {
    background(255) // Weißer Hintergrund
  }

  override def draw(): Unit = {
    background(255) // Refresh the screen
    drawButton()    // Draw the button
    currentFunction() // Call the current visualization function
  }

  // Function to handle button drawing
  def drawButton(): Unit = {
    fill(200) // Button color
    rect(10, 10, 100, 40) // Button rectangle
    fill(0)  // Text color
    textSize(16)
    textAlign(processing.core.PConstants.CENTER, processing.core.PConstants.CENTER)
    text("Example", 60, 30) // Button text
  }

  // Mouse click handler
  override def mousePressed(): Unit = {
    if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 50) {
      currentFunction = drawExample // Switch to the "example" function
    }
  }

  // Default drawing function
  def drawDefault(): Unit = {
    fill(0) // Black
    textAlign(processing.core.PConstants.CENTER,processing.core.PConstants.CENTER)
    textSize(24)
    text("Default View", width / 2, height / 2)
  }

  // Example visualization function
  def drawExample(): Unit = {
    fill(100, 150, 255) // Blueish color
    noStroke()
    for (i <- 0 until 10) {
      ellipse(random(width), random(height), 50, 50) // Random circles
    }
  }

}
*/