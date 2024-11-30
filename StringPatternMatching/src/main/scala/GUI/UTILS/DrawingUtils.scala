package GUI.UTILS

import processing.core.PApplet
import processing.core.PConstants._
import Style.*
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
   /* val (x, y, w, h) = rectangle
    parent.fill(if (isHovered) parent.color(70, 130, 180) else 200)
    parent.stroke(0)
    parent.rect(x, y, w, h, 10)
    parent.fill(255)
    parent.textAlign(CENTER, CENTER)
    parent.textSize(14)
    parent.text(label, x + w / 2, y + h / 2)
    */
    drawRoundedButton(parent,rectangle,label)
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

  // Helper function to display the bolded keyword
  def displayBoldKeywordText(parent: PApplet, xPosition: Float, currentY: Float, keyword: String, explanation: String): Unit = {
    parent.textSize(16)
    // Split the keyword into the bold part and the rest
    val boldKeyword = keyword.substring(2) // Skip "- " at the beginning of the keyword
    val normalText = explanation

    // Set the text to bold for the keyword
    parent.textFont(parent.createFont("Arial", 16, true)) // Make font bold
    parent.text(boldKeyword, xPosition, currentY) // Display the keyword in bold
    parent.textFont(parent.createFont("Arial", 16, false)) // Reset the font back to normal

    // Now display the explanation after the keyword
    parent.text(normalText, xPosition + parent.textWidth(boldKeyword) + 5, currentY) // Adjust position to the right of the keyword
  }

  /**
   * Render the full text with highlighted keywords directly on the Processing canvas.
   * Each new line in the input text will start on a new line in the rendered output.
   *
   * @param parent     The PApplet canvas to render the text on
   * @param ls         A list of pairs: (index of last Char where keyword was found, keyword), so (Integer, String)
   * @param fulltext   The full String where substring search is performed on
   * @param xPosition  X coordinate for text rendering
   * @param yPosition  Y coordinate for text rendering
   * @param lineHeight The height of each line for rendering
   * @param maxWidth   The maximum width of the text area before wrapping to a new line
   */
  def prettyprint(
                   parent: PApplet,
                   ls: List[(Int, String)],
                   fulltext: String,
                   xPosition: Float,
                   yPosition: Float,
                   lineHeight: Float,
                   maxWidth: Float
                 ): Unit = {
    // Split the full text into lines
    val lines = fulltext.split("\n")

    // Track current rendering position
    var currentY = yPosition

    // Process each line
    for (line <- lines) {
      // Extract matches relevant to this line
      val lineStartIdx = fulltext.indexOf(line)
      val lineEndIdx = lineStartIdx + line.length
      val lineMatches = ls.filter { case (index, _) =>
        index >= lineStartIdx && index < lineEndIdx
      }.map { case (index, keyword) =>
        (index - lineStartIdx, keyword) // Adjust indices relative to the line
      }

      // Render the line
      renderLine(parent, lineMatches, line, xPosition, currentY, lineHeight, maxWidth)
      currentY += lineHeight
    }
  }

  /**
   * Render a single line with highlighted keywords.
   *
   * @param parent     The PApplet canvas to render the text on
   * @param ls         A list of pairs: (index of last Char where keyword was found, keyword), so (Integer, String)
   * @param line       The current line to render
   * @param xPosition  X coordinate for text rendering
   * @param yPosition  Y coordinate for text rendering
   * @param lineHeight The height of each line for rendering
   * @param maxWidth   The maximum width of the text area before wrapping to a new line
   */
  def renderLine(
                  parent: PApplet,
                  ls: List[(Int, String)],
                  line: String,
                  xPosition: Float,
                  yPosition: Float,
                  lineHeight: Float,
                  maxWidth: Float
                ): Unit = {
    // Sort the list by index for consistent highlighting
    val sortedLs = ls.sortBy(_._1)

    // Variables to track rendering state
    var currentX = xPosition
    var lastIdx = 0

    // Highlight the text as per matches
    for ((endIdx, keyword) <- sortedLs) {
      val startIdx = endIdx - keyword.length

      // Render normal text before the match
      val normalText = line.substring(lastIdx, startIdx)
      for (char <- normalText) {
        val charWidth = parent.textWidth(char.toString)
        if (currentX + charWidth > maxWidth) {
          currentX = xPosition
        }
        parent.fill(0) // Black color for normal text
        parent.text(char, currentX, yPosition)
        currentX += charWidth
      }

      // Render the highlighted keyword
      val highlightedKeyword = line.substring(startIdx, endIdx)
      for (char <- highlightedKeyword) {
        val charWidth = parent.textWidth(char.toString)
        if (currentX + charWidth > maxWidth) {
          currentX = xPosition
        }
        parent.fill(255, 0, 0) // Red color for highlighted text
        parent.text(char, currentX, yPosition)
        currentX += charWidth
      }

      // Update last index
      lastIdx = endIdx
    }

    // Render any remaining normal text after the last match
    val remainingText = line.substring(lastIdx)
    for (char <- remainingText) {
      val charWidth = parent.textWidth(char.toString)
      if (currentX + charWidth > maxWidth) {
        currentX = xPosition
      }
      parent.fill(0) // Black color
      parent.text(char, currentX, yPosition)
      currentX += charWidth
    }
  }

  /**
   * Displays a list of keywords on the Processing canvas at a specified position.
   *
   * @param parent     The PApplet canvas to render the text on
   * @param keywords   The list of keywords to display
   * @param xPosition  X coordinate for rendering the list
   * @param yPosition  Y coordinate for rendering the list
   * @param lineHeight Spacing between each keyword
   */
  def displayKeywords(
                       parent: PApplet,
                       keywords: List[String],
                       xPosition: Float,
                       yPosition: Float,
                       lineHeight: Float
                     ): Unit = {
    parent.textSize(18) // Set text size
    parent.fill(0,0,255) // Black color for text
    parent.text("Keywords searched : ",xPosition,yPosition)
    parent.fill(0)
    parent.textSize(14) // Set text size

    var currentY = yPosition + 40
    for (keyword <- keywords) {
      parent.text(keyword, xPosition, currentY)
      currentY += lineHeight // Move down for the next keyword
    }
  }

}
