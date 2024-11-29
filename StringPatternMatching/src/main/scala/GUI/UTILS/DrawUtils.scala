package GUI.UTILS

import processing.core.PApplet
import processing.core.PConstants._

object DrawingUtils {

  // Function to draw an arrowhead with PApplet parameter
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

  // Function to draw a curved connection with PApplet parameter
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

    // Draw the arrowhead at the end of the connection
    drawArrowhead(parent, x2 - circleRadius / 2, y2, Math.atan2(y2 - controlY, x2 - controlX).toFloat)

    // Display the label in the middle of the curve
    val midX = (x1 + x2) / 2
    val midY = (y1 + y2) / 2
    parent.fill(0)
    parent.textAlign(CENTER, CENTER)
    parent.textSize(12)
    parent.text(label, midX, midY - 20)
  }

  // Function to draw a button with hover effect, using PApplet for all Processing calls
  def drawButton(parent: PApplet, rectangle: (Int, Int, Int, Int), label: String, isHovered: Boolean): Unit = {
    val (x, y, w, h) = rectangle
    parent.fill(if (isHovered) parent.color(70, 130, 180) else 200)
    parent.stroke(0)
    parent.rect(x, y, w, h, 10)
    parent.fill(255)
    parent.textAlign(CENTER, CENTER)
    parent.textSize(14)
    parent.text(label, x + w / 2, y + h / 2)
  }

  // Function to draw an input field with label and typing indicator, using PApplet for all Processing calls
  def drawInputField(parent: PApplet, label: String, fieldValue: String, x: Int, y: Int, isActive: Boolean): Unit = {
    val w = 600
    val h = 50

    // Draw label
    parent.fill(50)
    parent.textAlign(LEFT, CENTER)
    parent.textSize(14)
    parent.text(s"$label:", x, y - 30)

    // Draw input field rectangle
    parent.fill(255)
    parent.stroke(if (isActive) parent.color(30, 144, 255) else 150) // Highlight active field
    parent.strokeWeight(2)
    parent.rect(x, y, w, h, 10)

    // Draw text in the input field
    parent.fill(50)
    parent.textAlign(LEFT, CENTER)
    parent.text(if (isActive) fieldValue + "|" else fieldValue, x + 10, y + h / 2)
  }

  def drawEducationalText(parent: PApplet): Unit = {
    val title = "Educational Overview"
    val introText =
      """This tool visualizes the Aho-Corasick Algorithm, a method for efficient multi-pattern string matching.
        |Inspired by the seminal 1975 paper by Aho and Corasick, the algorithm constructs a state machine to
        |process patterns and find matches in a given text in linear time.""".stripMargin

    val whatYouWillLearn = "What You’ll Learn:"
    val learnPoints = Seq(
      "How a trie (prefix tree) is built from input keywords.",
      "How failure transitions are added to create a state machine.",
      "How the algorithm navigates through the state machine to identify matches step-by-step."
    )

    val closingText =
      """This visualization bridges theory and practice, offering insights into one of the most efficient
        |algorithms for pattern matching in fields like bioinformatics, text searching, and network security.""".stripMargin

    val learnMoreLink = "Learn more: https://cr.yp.to/bib/1975/aho.pdf"
    val GithubLink = "This Project on Github : https://github.com/JakubSchwenkbeck/AhoCorasick-Scala"
    // Start drawing
    var yOffset = 450 // Starting vertical position
    val xOffset = 100 // Horizontal margin

    // Draw the title
    parent.fill(0) // Black
    parent.textSize(24)
    //parent.textAlign(PApplet.LEFT, PApplet.TOP)
    parent.text(title, xOffset, yOffset)
    yOffset += 40 // Space below the title

    // Draw the introduction text
    parent.fill(50) // Dark gray
    parent.textSize(16)
    val introLines = introText.split("\n")
    introLines.foreach { line =>
      parent.text(line, xOffset, yOffset)
      yOffset += 24
    }

    yOffset += 20 // Add spacing before the next section

    // Draw the "What You'll Learn" header
    parent.fill(0) // Black
    parent.textSize(20)
    parent.text(whatYouWillLearn, xOffset, yOffset)
    yOffset += 30

    // Draw the bullet points
    parent.textSize(16)
    learnPoints.foreach { point =>
      parent.fill(30, 144, 255) // Blue for bullet point
      parent.text("•", xOffset, yOffset)
      parent.fill(50) // Dark gray for text
      parent.text(point, xOffset + 20, yOffset)
      yOffset += 24
    }

    yOffset += 20 // Add spacing before the closing text

    // Draw the closing text
    parent.fill(50) // Dark gray
    val closingLines = closingText.split( "\n")
    closingLines.foreach { line =>
      parent.text(line, xOffset, yOffset)
      yOffset += 24
    }

    yOffset += 30 // Add spacing before the link

    // Draw the "Learn More" link
    parent.fill(0, 0, 255) // Blue for the link
    parent.textSize(16)
    parent.text(learnMoreLink, xOffset, yOffset)
    yOffset += 20 // Add spacing before the link
    parent.text(GithubLink, xOffset, yOffset)

  }


}
