package GUI

import processing.core.PApplet

object Gui_Main extends PApplet {

  def main(args: Array[String]): Unit = {
    val sketch = new MySketch()
    PApplet.runSketch(Array("MySketch"), sketch)
  }
}

class MySketch extends PApplet {
  override def settings(): Unit = {
    size(800, 600) // Fenstergröße
  }

  override def setup(): Unit = {
    background(255) // Weißer Hintergrund
  }

  override def draw(): Unit = {
    ellipse(width / 2, height / 2, 50, 50) // Zeichne einen Kreis
  }
}
