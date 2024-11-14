// This class defines a finite state machine which pattern matches string inputs with the keywords


case class State(ID : Integer , Successor : Map[String,Int],endState : Boolean) // all states have an own ID and a single successor which is mapped by its input, also known by its ID



class FiniteStateMachine(SearchText: String, Keywords : List[String],InputStates : Map[Int, State]) {

  private val states: Map[Int, State] = InputStates
  private var text: String = SearchText
  private var keywords: List[String] = Keywords

  private var currentStateID: Int = 0

  def getCurrentStateID: Int = currentStateID

  def goto(input: String): Int =
    val currentStateOpt: Option[State] = states.get(currentStateID)

    currentStateOpt match {
      case Some(currentState) =>
        // Need to check if EndState!! --> output
        if (currentState.endState) {
          println(s"Reached endState at ${currentStateID}")
          0}
        else {

          // Access the Successor map of the current state
          currentState.Successor.get(input) match {
            case Some(nextStateID) =>
              // Update the current state ID and move to the next state
              currentStateID = nextStateID
              nextStateID
            case None =>
              // If there's no matching input in the Successor map, call the fail function
              fail()
              -1
          }

        }

      case None =>
        fail()
        -1
    }








  def fail(): Unit =
    println("nothing")

}

