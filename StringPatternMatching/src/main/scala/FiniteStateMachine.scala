// This class defines a finite state machine which pattern matches string inputs with the keywords


case class State(ID : Integer , Successor : Map[String,Int], endState : Boolean) // all states have an own ID and a single successor which is mapped by its input, also known by its ID



class FiniteStateMachine(SearchText: String, Keywords : List[String],InputStates : Map[Int, State]) {

  private val states: Map[Int, State] = InputStates
  private var text: String = SearchText
  private var keywords: List[String] = Keywords // each Keyword needs to be processed char by char by goto

  private var currentStateID: Int = 0

  def getCurrentStateID: Int = currentStateID


  def PMM() : List[Int] =
    val Output: List[Int] = List()
    for (i <- 0 until keywords.length ){
      for (j <- 0 until keywords(i).length) {
        var gotoOutput: Int = goto(keywords(i).charAt(j).toString)
        if (gotoOutput == -1) {
          currentStateID = fail(currentStateID)
        } else {
          currentStateID = gotoOutput
          val currentStateOpt: Option[State] = states.get(currentStateID)
           currentStateOpt match {
             case Some(currentState) => if (currentState.endState){
                Output :+ currentStateID
             }
             case None =>
           }

        }
      }
    }
      Output





  private def goto(input: String): Int =
    val currentStateOpt: Option[State] = states.get(currentStateID)

    currentStateOpt match {
      case Some(currentState) =>
        // Need to check if EndState!! --> output
          if(currentState.Successor != null) {
            // Access the Successor map of the current state
            currentState.Successor.get(input) match {
              case Some(nextStateID) =>
                // Update the current state ID and move to the next state
                currentStateID = nextStateID
                nextStateID
              case None =>
                // If there's no matching input in the Successor map, tell PMM to call fail
                -1
            }
          } else {-1}



      case None =>
        -1 // tell PMM to fail
    }








  def fail(stateID : Int): Int =
    println("nothing")

    -1

}

