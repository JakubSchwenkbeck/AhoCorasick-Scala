import org.scalajs.dom
import org.scalajs.dom.document

object FSMVisualizer {

  def main(): Unit = {
    // Ensure the DOM is fully loaded before running the visualization logic
    document.addEventListener("DOMContentLoaded", { (_: dom.Event) =>
      dom.console.log("DOM fully loaded and parsed!")

      // Example test case
      val TEST_SearchString: String = "Scala is fun and fun"
      val TEST_Keywords: List[String] = List("Scala", "fun", "ala")

      // Create an object of the FiniteStateMachine
      val FSM = FiniteStateMachine(TEST_SearchString, TEST_Keywords)

      // Create a container for the FSM visualization
      val graphContainer = document.createElement("div")
      graphContainer.setAttribute("id", "fsm-graph")
      graphContainer.setAttribute(
        "style",
        "position: relative; width: 100%; height: 600px; margin: 20px;"
      )
      document.body.appendChild(graphContainer)

      // Render the FSM using dynamic placement for trie-like structure
      renderFSM(FSM.getStates)
    })
  }

  private def renderFSM(states: Map[Int, State]): Unit = {
    val graphContainer = document.getElementById("fsm-graph")

    // Clear the container before rendering the FSM
    graphContainer.innerHTML = ""

    // Initialize the starting position for State 0
    var currentXOffset = 50
    var currentYOffset = 50
    val nodeSpacingX = 200 // Distance between nodes horizontally
    val nodeSpacingY = 100 // Vertical space between rows

    // A queue to manage state processing (like BFS)
    var stateQueue: List[(Int, Int, Int)] = List((0, currentXOffset, currentYOffset))  // Start with State 0
    var processedStates: Set[Int] = Set()

    // Process the states one by one and place them dynamically
    while (stateQueue.nonEmpty) {
      val (stateID, xOffset, yOffset) = stateQueue.head
      stateQueue = stateQueue.tail

      // Render the current state
      renderState(stateID, xOffset, yOffset, states)

      // Process each successor of the current state
      val state = states(stateID)
      var nextXOffset = xOffset + nodeSpacingX

      state.Successor.foreach { case (input, successorID) =>
        if (!processedStates.contains(successorID)) {
          // Mark the successor as processed and add it to the queue
          processedStates += successorID
          stateQueue = stateQueue :+ (successorID, nextXOffset, yOffset + nodeSpacingY)

          // Update the horizontal offset for the next state at the same level
          nextXOffset += nodeSpacingX
        }
      }
    }
  }

  private def renderState(stateID: Int, xOffset: Int, yOffset: Int, states: Map[Int, State]): Unit = {
    val graphContainer = document.getElementById("fsm-graph")

    // Create a div for each state
    val stateDiv = document.createElement("div")
    stateDiv.setAttribute("class", "state")
    stateDiv.setAttribute(
      "style",
      s"position: absolute; left: ${xOffset}px; top: ${yOffset}px; padding: 10px; border: 1px solid black; display: inline-block;"
    )
    stateDiv.setAttribute("id", s"state-$stateID")
    stateDiv.textContent = s"State $stateID"

    // Mark end states with a different style
    val state = states(stateID)
    if (state.endState) {
      stateDiv.setAttribute("style", stateDiv.getAttribute("style") + " background-color: lightgreen;")
      val endLabel = document.createElement("span")
      endLabel.textContent = " (End)"
      endLabel.setAttribute("style", "font-size: small; color: red;")
      stateDiv.appendChild(endLabel)
    }

    graphContainer.appendChild(stateDiv)
  }

  private def renderTransition(fromStateID: Int, toStateID: Int, input: String, xOffset: Int, yOffset: Int): Unit = {
    // Get the states from the DOM
    val fromState = document.getElementById(s"state-$fromStateID")
    val toState = document.getElementById(s"state-$toStateID")

    if (fromState != null && toState != null) {
      // Create an arrow to represent the transition
      val arrowDiv = document.createElement("div")
      arrowDiv.setAttribute("class", "transition")
      arrowDiv.setAttribute(
        "style",
        s"position: absolute; top: ${yOffset + 30}px; left: ${xOffset + 50}px; font-size: 12px; color: blue;"
      )
      arrowDiv.textContent = s"($input)"

      // Draw the arrow (just for visual simplicity)
      val arrowLine = document.createElement("div")
      arrowLine.setAttribute(
        "style",
        s"position: absolute; top: ${yOffset + 30}px; left: ${xOffset + 120}px; width: 50px; height: 2px; background-color: blue;"
      )
      document.getElementById("fsm-graph").appendChild(arrowLine)

      // Append the transition to the "from" state div
      fromState.appendChild(arrowDiv)
    } else {
      dom.console.warn(s"Could not render transition: $fromStateID -> $toStateID for input '$input'")
    }
  }
}
