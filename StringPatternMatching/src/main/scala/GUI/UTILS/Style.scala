package GUI.UTILS
import processing.core.PApplet
import processing.core.PConstants.*

object Style{
def drawNewInputField(parent: PApplet,label: String, value: String, x: Int, y: Int, isActive: Boolean): Unit = {
  val fieldWidth = 600
  val fieldHeight = 40
  val padding = 10

  parent.fill(80)
  parent.textAlign(LEFT)
  parent.text(label, x, y - 25)

  parent.stroke(if (isActive) parent.color(30, 130, 230) else parent.color(200))
  parent.fill(255)
  parent.rect(x, y, fieldWidth, fieldHeight, 8)

  parent.fill(50)
  parent.textAlign(LEFT, CENTER)
  parent.text(value, x + padding, y + fieldHeight / 2)
}
 def drawRoundedButton(parent : PApplet,recta: (Int, Int, Int, Int), label: String): Unit = {
  val (x, y, w, h) = recta
  val isHover = isInside(parent.mouseX, parent.mouseY, recta)
   parent.fill(if (isHover) parent.color(30, 130, 230) else parent.color(20, 120, 200))
   parent.stroke(255)
   parent.strokeWeight(2)
   parent.rect(recta._1, recta._2, recta._3, recta._4, 12)
   parent.fill(255)
   parent.noStroke()
   parent.textSize(16)
   parent.textAlign(CENTER, CENTER)
   parent.text(label, x + w / 2, y + h / 2)
}
/**
 * Checks if a point is within a rectangle.
 * @param mx X-coordinate of the point.
 * @param my Y-coordinate of the point.
 * @param rect Rectangle defined as a tuple (x, y, width, height).
 * @return True if the point is inside the rectangle, otherwise false.
 */
def isInside(mx: Int, my: Int, rect: (Int, Int, Int, Int)): Boolean = {
  val (x, y, w, h) = rect
  mx >= x && mx <= x + w && my >= y && my <= y + h
}}