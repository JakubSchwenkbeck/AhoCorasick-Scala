package GUI

import Main.State
import processing.core.PApplet
import processing.core.PApplet.*
import processing.core.PConstants.*
import GUI.DrawingUtils.*

import scala.collection.mutable


/**
 * A visualization class for rendering a trie data structure using Processing.
 *
 * @param trie   A map representing the trie, where keys are state IDs and values are `State` objects.
 * @param parent The Processing `PApplet` instance used for rendering.
 */
class VisualizeTrie(trie: Map[Int, State], parent: PApplet) {
  // Processing Settings

  // Fields of the class:
  private val Trie: Map[Int, State] = trie
  private val root: State = Trie(0) // Assume the root state is ID 0
  var statePositions: mutable.Map[Int, (Int, Int)] = scala.collection.mutable.Map[Int, (Int, Int)]() // Store positions of states
  private var xOffset : Int= 150 // Horizontal spacing between levels
  private var yOffset : Int = 120 // Vertical spacing between siblings
  private var circleR : Int = 40 // Circle radius

  if(Trie.values.size > 12 && Trie.values.size < 40) {
    xOffset = max(xOffset - 4 *( Trie.values.size -12) , 35) // Horizontal spacing between levels
    yOffset = max(yOffset - 9 *( Trie.values .size -12),28)  // Vertical spacing between siblings
    circleR = max(circleR - 3 *( Trie.values.size -12) ,30) // Circle radius
  }
  if(Trie.values.size < 40){parent.text("The input is to big for the GUI, please use th CLI and files", 100,100)}
  
  
  // To track sibling positions at each level
  private var siblingIndex = scala.collection.mutable.Map[Int, Int]().withDefaultValue(0)

  
  def resetVis() : Unit =
    statePositions = scala.collection.mutable.Map[Int, (Int, Int)]()
    siblingIndex = scala.collection.mutable.Map[Int, Int]().withDefaultValue(0)
  
  /**
   * Main method to visualize the entire trie structure.
   * Starts drawing from the root and recursively renders all connected states.
   */
  def visTrie(): Unit = {
    // Draw the root state and recursively visualize the trie
    drawState(root, 50, parent.height / 9) // Start drawing from the left side, center vertically
    if (root.Successor.nonEmpty) {
      for ((input, childID) <- root.Successor) {
        recTrie(Trie(childID), input, 50, parent.height / 9, 1) // Start recursion with level = 1
      }
    }
  }
  
  /**
   * Recursively visualizes the trie structure starting from a given state.
   *
   * @param st       The current state to be drawn.
   * @param input    The input string that leads to this state.
   * @param parentX  X-coordinate of the parent state.
   * @param parentY  Y-coordinate of the parent state.
   * @param level    The current depth level in the trie.
   */
  private def recTrie(st: State, input: String, parentX: Int, parentY: Int, level: Int): Unit = {
    if (st != null) {
      val xPos = parentX + xOffset

      var yPos = parentY + siblingIndex(level) * yOffset
      siblingIndex(level) += 1
      if(yPos >parent.height){yPos = parentY +  yOffset }
      // Draw the current state
      drawState(st, xPos, yPos)

      // Draw a curved line connecting the current state to its parent
      drawCurvedConnection(parentX, parentY, xPos, yPos, input)

      // Recurse on successors
      if (st.Successor.nonEmpty) {
        for ((childInput, childID) <- st.Successor) {
          recTrie(Trie(childID), childInput, xPos, yPos, level + 1)
        }
      }
    }
  }

  /**
   * Draws a state (node) in the trie at the specified position.
   *
   * @param st   The state to be drawn.
   * @param xpos X-coordinate for the state.
   * @param ypos Y-coordinate for the state.
   */
  private def drawState(st: State, xpos: Int, ypos: Int): Unit = {
    val ID = st.ID
    statePositions(ID) = (xpos, ypos) // Save the position of this state

    // Draw the circle representing the state
    if (st.endState) {
      parent.fill(255, 153, 153) // Soft red for end states
      parent.stroke(204, 0, 0) // Bold outline for end states
      parent.strokeWeight(2)
    } else {
      parent.fill(173, 216, 230) // Soft blue for regular states
      parent.stroke(0, 102, 204) // Blue outline
      parent.strokeWeight(1)
    }
    parent.ellipse(xpos, ypos, circleR, circleR)

    // Draw the state's ID inside the circle
    parent.fill(0) // Black text
    parent.textAlign(CENTER, CENTER)
    parent.textSize(14)
    parent.text(ID.toString, xpos, ypos)

    // Draw the keyword next to end states
    if (st.endState && st.keyword.isDefined) {
      parent.textSize(12)
      parent.fill(0)
      parent.text(st.keyword.get, xpos + circleR / 2 + 20, ypos)
    }
  }

  /**
   * Draws a curved connection between two states with an arrowhead and a label.
   *
   * @param x1    X-coordinate of the starting state.
   * @param y1    Y-coordinate of the starting state.
   * @param x2    X-coordinate of the ending state.
   * @param y2    Y-coordinate of the ending state.
   * @param label The label to display on the connection (usually the input string).
   */
  private def drawCurvedConnection(x1: Int, y1: Int, x2: Int, y2: Int, label: String): Unit = {
    DrawingUtils.drawCurvedConnection(parent, x1, y1, x2, y2, label, circleR)
  }


  /**
   * Draws an arrowhead at the end of a curved connection to indicate direction.
   *
   * @param x     X-coordinate of the arrowhead tip.
   * @param y     Y-coordinate of the arrowhead tip.
   * @param angle The angle of rotation for the arrowhead.
   */
  private def drawArrowhead(x: Float, y: Float, angle: Float): Unit = {
    DrawingUtils.drawArrowhead(parent, x, y, angle)
  }

}
