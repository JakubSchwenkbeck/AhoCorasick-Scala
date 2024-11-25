package GUI

import Main.State
import processing.core.PApplet
import processing.core.PApplet.*

import processing.core.PConstants.*

class VisualizeTrie(trie: Map[Int, State],parent : PApplet) {
  // Processing Settings
 

  // Fields of the class:
  private val Trie: Map[Int, State] = trie
  private val root: State = Trie(0) // Assume the root state is ID 0
  private val statePositions = scala.collection.mutable.Map[Int, (Int, Int)]() // Store positions of states
  private val xOffset = 150 // Horizontal spacing between levels
  private val yOffset = 120 // Vertical spacing between siblings

  private val circleR = 40 // Circle radius

  // To track sibling positions at each level
  private val siblingIndex = scala.collection.mutable.Map[Int, Int]().withDefaultValue(0)

  // Main handler function
  def visTrie(): Unit = {
    // Draw the root state and recursively visualize the trie
    drawState(root, 100, parent.height / 4) // Start drawing from the left side, center vertically
    if (root.Successor.nonEmpty) {
      for ((input, childID) <- root.Successor) {
        recTrie(Trie(childID), input, 100, parent.height / 4, 1) // Start recursion with level = 1
      }
    }
  }

  // Recursive function to traverse and draw the trie
  private def recTrie(st: State, input: String, parentX: Int, parentY: Int, level: Int): Unit = {
    if (st != null) {
      val xPos = parentX + xOffset
      val yPos = parentY + siblingIndex(level) * yOffset
      siblingIndex(level) += 1

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

  // Draw a state at the given position
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

  // Draw a curved connection using arcs and smooth transitions
  private def drawCurvedConnection(x1: Int, y1: Int, x2: Int, y2: Int, label: String): Unit = {
    parent.noFill()
    parent.stroke(0)
    parent.strokeWeight(1)

    // Calculate control points for a smooth arc
    val controlX = (x1 + x2) / 2 // Midpoint on the x-axis
    val controlY = if (y1 < y2) y1 - 40 else y2 - 40 // Offset the arc upwards

    // Draw the arc-like curve
    parent.beginShape()
    parent.vertex(x1 + circleR / 2, y1) // Start point (adjusted for the circle's radius)
    parent.quadraticVertex(controlX, controlY, x2 - circleR / 2, y2) // Control point for smooth curve
    parent.endShape()

    // Add an arrowhead for direction
    drawArrowhead(x2 - circleR / 2, y2, atan2(y2 - controlY, x2 - controlX))

    // Calculate the midpoint for the label
    val midX = (x1 + x2) / 2
    val midY = (y1 + y2) / 2

    // Draw the label slightly above the curve
    parent.fill(0)
    parent.textAlign(CENTER, CENTER)
    parent.textSize(12)
    parent.text(label, midX, midY - 20)
  }

  // Helper function to draw an arrowhead at the end of the curve
  private def drawArrowhead(x: Float, y: Float, angle: Float): Unit = {
    parent.pushMatrix()
    parent.translate(x, y)
    parent.rotate(angle)

    // Draw arrowhead triangle
    parent.fill(0)
    parent.noStroke()
    parent.beginShape()
    parent.vertex(0, 0)
    parent.vertex(-10, 5)
    parent.vertex(-10, -5)
    parent.endShape(CLOSE)

    parent.popMatrix()
  }

}
