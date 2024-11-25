package GUI

import processing.core.PApplet
import processing.core.PApplet.*
import processing.core.PConstants.*


object DrawingUtils {
  def drawArrowhead(parent: PApplet, x: Float, y: Float, angle: Float): Unit = {
    parent.pushMatrix()
    parent.translate(x, y)
    parent.rotate(angle)
    parent.fill(0)
    parent.noStroke()
    parent.beginShape()
    parent.vertex(0, 0)
    parent.vertex(-10, 5)
    parent.vertex(-10, -5)
    parent.endShape(CLOSE)
    parent.popMatrix()
  }

  def drawCurvedConnection(
                            parent: PApplet,
                            x1: Int, y1: Int, x2: Int, y2: Int,
                            label: String, circleRadius: Int
                          ): Unit = {
    val controlX = (x1 + x2) / 2
    val controlY = if (y1 < y2) y1 - 40 else y2 - 40

    parent.noFill()
    parent.stroke(0)
    parent.strokeWeight(1)
    parent.beginShape()
    parent.vertex(x1 + circleRadius / 2, y1)
    parent.quadraticVertex(controlX, controlY, x2 - circleRadius / 2, y2)
    parent.endShape()

    drawArrowhead(parent, x2 - circleRadius / 2, y2, Math.atan2(y2 - controlY, x2 - controlX).toFloat)

    val midX = (x1 + x2) / 2
    val midY = (y1 + y2) / 2
    parent.fill(0)
    parent.textAlign(CENTER, CENTER)
    parent.textSize(12)
    parent.text(label, midX, midY - 20)
  }
}
