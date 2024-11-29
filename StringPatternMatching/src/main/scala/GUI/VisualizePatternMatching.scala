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
  private val text: String = searchText
  private val fails: Map[Int, Int] = Main.computeFail(trie)

  private var currentStateID: Int = 0 // Starting state
  private var currentCharIndex: Int = -1 // Index of the character being processed
  private var matches: List[(Int, String)] = List() // To store matches found
  private var animationPaused: Boolean = true // Control step-by-step animation
  var currentMatch : Option[Int] = None
  // Getters
  def getCurrentStateID: Int = currentStateID
  def getMatches: List[(Int, String)] = matches

  /**
   * Perform one step of the pattern matching algorithm and visualize it.
   */
  def step(): Unit = {
    if (currentCharIndex < text.length - 1) {
      currentCharIndex += 1
      val char = text.charAt(currentCharIndex).toString
      val gotoOutput: Int = goto(char)

      // Visualize transition
      if (gotoOutput == -1) {
        visualizeFailTransition()
        currentStateID = fail(currentStateID)
      } else {
        visualizeStateTransition(currentStateID, gotoOutput, char)
        currentStateID = gotoOutput
        trie.get(currentStateID).foreach { currentState =>
          if (currentState.endState) {
            currentMatch = Some(currentState.keyword.get.length)
            matches = matches :+ (currentCharIndex + 1, currentState.keyword.get)
            visualizeMatch(currentState.ID, currentState.keyword.get)
          }
        }
      }
    }
  }

  /**
   * Visualize the entire process in a loop.
   */
  def run(): Unit = {
    if (!animationPaused) {
      step()
    }
  }

  /**
   * Toggle animation pause.
   */
  def togglePause(): Unit = {
    animationPaused = !animationPaused
  }

  /**
   * GoTo function with state transition logic.
   */
  private def goto(input: String): Int = {
    trie.get(currentStateID) match {
      case Some(currentState) =>
        currentState.Successor.get(input) match {
          case Some(nextStateID) => nextStateID
          case None => if (currentStateID == 0) 0 else -1
        }
      case None => throw new Exception(s"State ID $currentStateID does not exist!")
    }
  }

  /**
   * Visualize a transition from one state to another.
   */
  private def visualizeStateTransition(fromState: Int, toState: Int, char: String): Unit = {
    val (x1, y1) = statePositions(fromState)
    val (x2, y2) = statePositions(toState)
    parent.fill(0, 255, 0) // Bright green fill for the transition text
    parent.stroke(0, 200, 50) // Vivid green stroke for the line
    parent.strokeWeight(3) // Thicker line
    parent.line(x1, y1, x2, y2) // Draw the line
    parent.text(char, (x1 + x2) / 2, (y1 + y2) / 2 - 10) // Draw the character
    parent.noStroke()
    parent.fill(0) // Reset fill color
  }

  /**
   * Visualize a fail transition.
   */
  private def visualizeFailTransition(): Unit = {
    val (x1, y1) = statePositions(currentStateID)
    val failStateID = fail(currentStateID)
    val (x2, y2) = statePositions(failStateID)
    parent.stroke(255, 0, 0) // Bright red stroke
    parent.strokeWeight(2) // Thicker line for fail transition
    parent.line(x1, y1, x2, y2) // Draw the fail link
    parent.noStroke()
  }

  /**
   * Visualize a match found.
   */
  private def visualizeMatch(index: Int, keyword: String): Unit = {
    println("match found at " + keyword)

    val charX = 700
    val charY = 500
    parent.fill(0, 0, 255) // Bright blue for matches
    parent.stroke(0, 102, 204) // Blue outline
    parent.strokeWeight(1)
    parent.ellipse(statePositions(index)._1,statePositions(index)._2,40,40)
    
    parent.text(s"Match: $keyword", charX, charY)
    parent.fill(0) // Reset fill color
  }
  /**
   * Draw the current state of the search text and states.
   */
  def draw(): Unit = {
    parent.background(255)
    drawSearchText()
  }


  def drawKeywords(): Unit = {
    var offset = 0
    val baseX = 700
    val baseY = 400
    for(key <- keywords){
      parent.text(key,baseX,baseY + offset)
      offset += 25

    }


  }


   def drawSearchText(): Unit = {
    println(currentCharIndex)
    val baseX = 600
    val baseY = 600

    for (i <- text.indices) {
      if(currentMatch.nonEmpty){

        if (i >= (currentCharIndex - currentMatch.getOrElse(0) +1 ) && i <= currentCharIndex ) {

            parent.fill(0, 0, 255)

          }else {
            parent.fill(50)
          }
        }else if(currentCharIndex == i ){
        parent.fill(0, 255, 0)

          }
        else {
          parent.fill(50)
        }


      if (baseX + 15 * i < parent.width || baseY < parent.height) {
        parent.text(text.charAt(i), baseX + 15 * i, baseY)
      }else{
       // throw new Exception("the text is too long")
        parent.text(text.charAt(i), baseX + 15 * (i - 20) , baseY+50)
      }
    }
     currentMatch = None
  }
  def fail(stateID: Int): Int = { // actual magic happens in computeFail
    fails(stateID) // Return the fail link for the current state
  }
}
