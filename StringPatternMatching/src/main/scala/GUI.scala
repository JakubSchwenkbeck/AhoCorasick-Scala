import org.scalajs.dom
import org.scalajs.dom.document

object FSMVisualizer {

  def main(): Unit = {
    // Ensure the DOM is fully loaded before running the visualization logic
    document.addEventListener("DOMContentLoaded", { (_: dom.Event) =>
      dom.console.log("DOM fully loaded and parsed!")

      val TEST_SearchString: String = "Scala is fun and fun"
      val TEST_Keywords: List[String] = List("Scala", "fun", "ala")


      // create an object of the FinitStateMachine:
      val FSM = FiniteStateMachine(TEST_SearchString, TEST_Keywords)

      // Create a container for the FSM visualization
      val graphContainer = document.createElement("div")
      graphContainer.setAttribute("id", "fsm-graph")
      graphContainer.setAttribute(
        "style",
        "display: flex; flex-direction: column; gap: 10px; margin: 20px;"
      )
      document.body.appendChild(graphContainer)

      // Render the FSM states and transitions
      renderFSM(FSM.getStates)
    })
  }

  def renderFSM(states: Map[Int, State]): Unit = {
    val graphContainer = document.getElementById("fsm-graph")

    // Clear the container before rendering the FSM
    graphContainer.innerHTML = ""

    // Create a div for each state
    states.foreach { case (stateID, state) =>
      val stateDiv = document.createElement("div")
      stateDiv.setAttribute("class", "state")
      stateDiv.setAttribute(
        "style",
        "padding: 10px; border: 1px solid black; display: inline-block;"
      )
      stateDiv.setAttribute("id", s"state-$stateID")
      stateDiv.textContent = s"State $stateID"

      if (state.endState) {
        stateDiv.setAttribute("class", "state end-state")
        stateDiv.setAttribute("style", stateDiv.getAttribute("style") + " background-color: lightgreen;")
      }

      graphContainer.appendChild(stateDiv)

      // Render transitions for each state
      state.Successor.foreach { case (input, successorID) =>
        renderTransition(stateID, successorID, input)
      }
    }
  }

  def renderTransition(fromStateID: Int, toStateID: Int, input: String): Unit = {
    // Get the states from the DOM
    val fromState = document.getElementById(s"state-$fromStateID")
    val toState = document.getElementById(s"state-$toStateID")

    if (fromState != null && toState != null) {
      // Create an arrow to represent the transition
      val arrowDiv = document.createElement("div")
      arrowDiv.setAttribute("class", "transition")
      arrowDiv.setAttribute(
        "style",
        "color: blue; font-size: 12px; margin-left: 10px; display: inline-block;"
      )
      arrowDiv.textContent = s"$fromStateID --($input)--> $toStateID"

      // Append to the fromState div
      fromState.appendChild(arrowDiv)
    } else {
      dom.console.warn(s"Could not render transition: $fromStateID -> $toStateID for input '$input'")
    }
  }

}
