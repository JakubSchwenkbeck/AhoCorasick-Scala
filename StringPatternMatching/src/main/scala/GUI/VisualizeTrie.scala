package GUI

import Main.State
import processing.core.PApplet

class VisualizeTrie(trie: Map[Int, State]) extends PApplet {
  // Processing Settings:
  override def settings(): Unit = {
    size(1200, 1000) // Window size
  }

  override def setup(): Unit = {
    background(255) // White background
    noLoop() // Static image
  }

  override def draw(): Unit = {
    visTrie()
  }

  // Fields of the class:
  private val Trie: Map[Int, State] = trie
  private val root: State = Trie(0) // Assume the root state is ID 0
  private val statePositions = scala.collection.mutable.Map[Int, (Int, Int)]() // Store positions of states
  private val xOffset = 200 // Horizontal spacing between states
  private val yOffset = 100 // Vertical spacing between levels

  // Main handler function
  private def visTrie(): Unit = {
    // Draw the root state and recursively visualize the trie
    drawState(root, width / 2, 50) // Center the root at the top
    if (root.Successor.nonEmpty) {
      for ((input, childID) <- root.Successor) {
        recTrie(Trie(childID),input, width / 2, 50, 1) // Start recursion with level = 1
      }
    }
    println(statePositions)
  }


  // Recursive function to traverse and draw the trie
  private def recTrie(st: State,input: String ,parentX: Int, parentY: Int, level: Int): Unit = {
    if (st != null) {
      val xPos = parentX + (xOffset / Math.pow(2, level).toInt) // Dynamic horizontal position
      val yPos = parentY + yOffset // Vertical position based on level

      // Draw the current state
      drawState(st, xPos, yPos)

      // Draw a line connecting the current state to its parent
      drawConnection(parentX, parentY, xPos, yPos,input)

      // Recurse on successors
      if (st.Successor.nonEmpty) {
        for ((input, childID) <- st.Successor) {
          recTrie(Trie(childID),input, xPos, yPos, level + 1)
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
      fill(150) // Gray for end states
    } else {
      fill(255) // White for regular states
    }
    ellipse(xpos, ypos, 30, 30)

    // Draw the state's ID inside the circle
    fill(0) // Black text
    textAlign(processing.core.PConstants.CENTER, processing.core.PConstants.CENTER)
    textSize(12)
    text(ID.toString, xpos, ypos)
    println(st.ID)
  }

  // Draw a connection between two states
  private def drawConnection(x1: Int, y1: Int, x2: Int, y2: Int, label: String): Unit = {
    stroke(0) // Black lines
    line(x1, y1, x2, y2) // Draw the line

    // Calculate the midpoint of the line
    val midX = (x1 + x2) / 2
    val midY = (y1 + y2) / 2

    // Draw the label at the midpoint of the line
    fill(0) // Text color
    textAlign(processing.core.PConstants.CENTER, processing.core.PConstants.CENTER) // Center the text
    text(label, midX + 10, midY -25) // Draw the label slightly above the line
  }

}
