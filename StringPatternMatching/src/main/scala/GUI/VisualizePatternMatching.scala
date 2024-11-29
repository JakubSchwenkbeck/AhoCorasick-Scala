package GUI

import processing.core.PApplet
import processing.core.PConstants._
import Main._

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
        currentState.Successor.get(input).getOrElse(if (currentStateID == 0) 0 else -1)
      case None => throw new Exception(s"State ID $currentStateID does not exist!")
    }
  }

  /**
   * Visualize a state transition during processing.
   */
  private def visualizeStateTransition(fromState: Int, toState: Int, char: String): Unit = {
    val (x1, y1) = statePositions(fromState)
    val (x2, y2) = statePositions(toState)

    // Draw transition line
    parent.stroke(0, 200, 50)
    parent.strokeWeight(3)
    parent.line(x1, y1, x2, y2)

    // Draw transition label
    parent.fill(0, 255, 0)
    parent.text(char, (x1 + x2) / 2, (y1 + y2) / 2 - 10)

    parent.noStroke()
    parent.fill(0)
  }

  /**
   * Visualize a fail transition between states.
   */
  private def visualizeFailTransition(): Unit = {
    val (x1, y1) = statePositions(currentStateID)
    val failStateID = fail(currentStateID)
    val (x2, y2) = statePositions(failStateID)

    // Draw fail link
    parent.stroke(255, 0, 0)
    parent.strokeWeight(2)
    parent.line(x1, y1, x2, y2)
    parent.noStroke()
  }

  /**
   * Highlight a match found in the search text.
   */
  private def visualizeMatch(index: Int, keyword: String): Unit = {
    val (x, y) = statePositions(index)

    // Draw match indicator
    parent.fill(0, 0, 255)
    parent.stroke(0, 102, 204)
    parent.ellipse(x, y, 40, 40)

    // Display match text
    parent.fill(0)
    parent.text(s"Match: $keyword", 700, 500)
  }

  /**
   * Draw the visualization canvas, including states and transitions.
   */
  def draw(): Unit = {
    parent.background(255)
    drawSearchText()
    drawKeywords()
  }

  /**
   * Render the list of keywords.
   */
   def drawKeywords(): Unit = {
    val baseX = 700
    val baseY = 400
    keywords.zipWithIndex.foreach { case (key, idx) =>
      parent.text(key, baseX, baseY + idx * 25)
    }
  }

  /**
   * Display the search text and highlight relevant parts.
   */
   def drawSearchText(): Unit = {
    val baseX = 600
    val baseY = 600

    text.zipWithIndex.foreach { case (char, i) =>
      val xPos = baseX + 15 * (if (i >= 20) i - 20 else i)
      val yPos = if (i < 20) baseY else baseY + 50

      // Set fill color based on state
      if (currentMatch.exists(length => i >= currentCharIndex - length + 1 && i <= currentCharIndex)) {
        parent.fill(0, 0, 255) // Highlight matched text
      } else if (i == currentCharIndex) {
        parent.fill(0, 255, 0) // Highlight current character
      } else {
        parent.fill(50) // Default color
      }

      parent.text(char, xPos, yPos)
    }

    currentMatch = None
  }

  /**
   * Retrieve the fail link for a given state.
   */
  def fail(stateID: Int): Int = fails(stateID)
}
