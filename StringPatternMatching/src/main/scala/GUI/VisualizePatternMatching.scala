package GUI

import processing.core.PApplet
import processing.core.PConstants.*
import Main.*
import GUI.UTILS.*
import GUI.UTILS.DrawingUtils.drawArrowhead
class VisualizePatternMatching(
                                trie: Map[Int, State],
                                statePositions: scala.collection.mutable.Map[Int, (Int, Int)],
                                parent: PApplet,
                                searchText: String,
                                keywords: List[String]
                              ) {

  // Private variables to maintain state
  private val text: String = searchText
  private val fails: Map[Int, Int] = Main.computeFail(trie)

  private var currentStateID: Int = 0 // Current state in the algorithm
  private var currentCharIndex: Int = -1 // Index of the current character being processed
  private var matches: List[(Int, String)] = List() // Matches found during execution
  private var animationPaused: Boolean = true // Toggle for step-by-step animation
  private var currentMatch: Option[Int] = None // Tracks length of the current match

  // Public getters
  def getCurrentStateID: Int = currentStateID
  def getMatches: List[(Int, String)] = matches

  /**
   * Perform one step of the Aho-Corasick algorithm and visualize it.
   */
  def step(): Unit = {
    if (currentCharIndex < text.length - 1) {
      currentCharIndex += 1
      val char = text.charAt(currentCharIndex).toString
      val gotoOutput = goto(char)

      if (gotoOutput == -1) {
        visualizeFailTransition()
        currentStateID = fail(currentStateID)
      } else {
        visualizeStateTransition(currentStateID, gotoOutput, char)
        currentStateID = gotoOutput

        trie.get(currentStateID).foreach { currentState =>
          if (currentState.endState) {
            currentMatch = Some(currentState.keyword.get.length)
            matches :+= (currentCharIndex + 1, currentState.keyword.get)
            visualizeMatch(currentState.ID, currentState.keyword.get)
          }
        }
      }
    }
  }

  /**
   * Run the animation loop, processing steps if not paused.
   */
  def run(): Unit = {
    if (!animationPaused) step()
  }

  /**
   * Toggle animation between paused and running states.
   */
  def togglePause(): Unit = {
    animationPaused = !animationPaused
  }

  /**
   * The `goto` function: Implements state transition logic.
   */
  private def goto(input: String): Int = {
    trie.get(currentStateID) match {
      case Some(currentState) =>
        currentState.Successor.getOrElse(input, if (currentStateID == 0) 0 else -1)
      case None => throw new Exception(s"State ID $currentStateID does not exist!")
    }
  }

  /**
   * Visualize a state transition during processing.
   */
  private def visualizeStateTransition(fromState: Int, toState: Int, char: String): Unit = {
    val (x1, y1) = statePositions(fromState)
    val (x2, y2) = statePositions(toState)

    // Draw transition line with arrow
    parent.stroke(30, 144, 255) // Dodger blue for transitions
    parent.strokeWeight(4)
    parent.line(x1, y1, x2, y2)

    // Draw arrowhead
    val angle = Math.atan2(y2 - y1, x2 - x1).toFloat
    drawArrowhead(parent, x2, y2, angle)

    // Draw transition label
    parent.fill(0)
    parent.textSize(14)
    parent.textAlign(CENTER, CENTER)
    parent.text(char, (x1 + x2) / 2, (y1 + y2) / 2 - 15)

    parent.noStroke()
  }

  /**
   * Visualize a fail transition between states.
   */
  private def visualizeFailTransition(): Unit = {
    val (x1, y1) = statePositions(currentStateID)
    val failStateID = fail(currentStateID)
    val (x2, y2) = statePositions(failStateID)

    // Draw dashed fail line
    parent.stroke(220, 20, 60) // Crimson for fail links
    parent.strokeWeight(2)
    parent.strokeCap(SQUARE)
    for (i <- 0 to 10) {
      val t = i / 10.0f
      val x = x1 + t * (x2 - x1)
      val y = y1 + t * (y2 - y1)
      if (i % 2 == 0) parent.point(x, y)
    }

    parent.noStroke()
  }

  /**
   * Highlight a match found in the search text.
   */
  private def visualizeMatch(index: Int, keyword: String): Unit = {
    val (x, y) = statePositions(index)

    // Highlight the state with a glowing circle
    parent.fill(0, 191, 255, 150) // Deep sky blue with transparency
    parent.noStroke()
    parent.ellipse(x, y, 50, 50)

    // Display the match keyword in a dedicated right-side panel
    parent.fill(255) // White background for clarity
    parent.stroke(0) // Black border for emphasis
    parent.rect(700, 450, 200, 50, 10) // Rounded rectangle for "Match" box

    parent.fill(0) // Black text
    parent.textSize(16)
    parent.textAlign(CENTER, CENTER)
    parent.text(s"Match: $keyword", 800, 475) // Centered in the box
    parent.noStroke() // reset

  }

  /**
   * Draw the visualization canvas, including states and transitions.
   */
  def draw(): Unit = {
    parent.background(245) // Light gray background for better contrast
    drawSearchText()
    drawKeywords()
    drawMatches()
  }

  /**
   * Render the list of keywords neatly in a right-aligned column.
   */
   def drawKeywords(): Unit = {
    val baseX = 750
    val baseY = 300

    parent.fill(50) // Dark gray text
    parent.textSize(25)
    parent.textAlign(LEFT, CENTER)

    // Title for the section
    parent.text("Keywords:", baseX, baseY - 30)

    // List keywords with spacing
    keywords.zipWithIndex.foreach { case (key, idx) =>
      parent.text(s"${idx + 1}. $key", baseX + 20, baseY + idx * 25)
    }
  }

  /**
   * Display matches found so far in a separate section.
   */
   private def drawMatches(): Unit = {
    val baseX = 750
    val baseY = 500

    parent.fill(50) // Dark gray text
    parent.textSize(16)
    parent.textAlign(LEFT, CENTER)

    // Title for the section
    parent.text("Matches Found:", baseX, baseY - 30)

    // List matches with spacing
    matches.zipWithIndex.foreach { case ((pos, keyword), idx) =>
      parent.text(s"${idx + 1}. '$keyword' at $pos", baseX + 20, baseY + idx * 25)
    }
  }

  /**
   * Display the search text and highlight relevant parts.
   */

   def drawSearchText(): Unit = {
    val baseX = 750
    val baseY = 600
    val lineLength = 32 // Number of characters per line

    text.zipWithIndex.foreach { case (char, i) =>
      val xPos = baseX + 15 * (i % lineLength)
      val yPos = baseY + 50 * (i / lineLength)

      // Set fill color based on state
      if (currentMatch.exists(length => i >= currentCharIndex - length + 1 && i <= currentCharIndex)) {
        parent.fill(50, 205, 50) // Lime green for matched text
      } else if (i == currentCharIndex) {
        parent.fill(255, 140, 0) // Orange for current character
      } else {
        parent.fill(50) // Default dark gray
      }

      parent.textSize(18)
      parent.textAlign(CENTER, CENTER)
      parent.text(char, xPos, yPos)
    }

    currentMatch = None // Clear the current match after rendering
  }

  /**
   * Retrieve the fail link for a given state.
   */
  def fail(stateID: Int): Int = fails(stateID)
}
