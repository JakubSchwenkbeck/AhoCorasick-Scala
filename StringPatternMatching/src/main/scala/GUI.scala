import scala.scalajs.js
import scala.scalajs.*
import org.scalajs.dom
import org.scalajs.dom.document

object FSMVisualizer  {

  def main(): Unit = {
    val fsm = new FiniteStateMachine("abac", List("ab", "bc"))

    val graphContainer = document.createElement("div")
    graphContainer.setAttribute("id", "fsm-graph")
    document.body.appendChild(graphContainer)

    renderFSM(fsm.getStates)
  }

  def renderFSM( states : Map[Int,State]): Unit = {
   
    val graphContainer = document.getElementById("fsm-graph")

    // Clear the container before rendering the FSM
    graphContainer.innerHTML = ""

    // Create a div for each state
    states.foreach { case (stateID, state) =>
      val stateDiv = document.createElement("div")
      stateDiv.setAttribute("class", "state")
      stateDiv.setAttribute("id", s"state-$stateID")
      stateDiv.textContent = s"State $stateID"

      if (state.endState) {
        stateDiv.setAttribute("class", "state end-state")
      }

      graphContainer.appendChild(stateDiv)

      // Render transitions for each state
      state.Successor.foreach { case (input, successorID) =>
        renderTransition(stateID, successorID, input)
      }
    }
  }

  def renderTransition(fromStateID: Int, toStateID: Int, input: String): Unit = {
    val fromState = document.getElementById(s"state-$fromStateID")
    val toState = document.getElementById(s"state-$toStateID")

    // Create an arrow to represent the transition
    val arrowDiv = document.createElement("div")
    arrowDiv.setAttribute("class", "transition")
    arrowDiv.textContent = input

    // Position the transition (you may need to adjust for actual layout)
    fromState.appendChild(arrowDiv)
    toState.appendChild(arrowDiv)
  }

}
