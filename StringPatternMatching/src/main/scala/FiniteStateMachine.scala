// This class defines a finite state machine which pattern matches string inputs with the keywords


case class State(ID: Integer, Successor: Map[String, Int], endState: Boolean, keyword: Option[String] = None) // all states have an own ID and a single successor which is mapped by its input, also known by its ID


class FiniteStateMachine(SearchText: String, Keywords: List[String]) {

  private val states: Map[Int, State] = buildGraph(Keywords)
  private val text: String = SearchText
  private var keywords: List[String] = Keywords // each Keyword needs to be processed char by char by goto

  private var currentStateID: Int = 0

  def getCurrentStateID: Int = currentStateID


  def PMM(): List[(Int, String)] =
    var Output: List[(Int, String)] = List()
    var charPos: Int = 0
    for (index <- 0 until text.length) {
      //  println(currentStateID)
      charPos += 1
      val gotoOutput: Int = goto(text.charAt(index).toString)
      if (gotoOutput == -1) {
        currentStateID = fail(currentStateID)
      } else {
        currentStateID = gotoOutput
        val currentStateOpt: Option[State] = states.get(currentStateID)
        currentStateOpt match {
          case Some(currentState) => if (currentState.endState) {
           Output =  Output :+ (charPos, currentState.keyword.get)


          } // Reassign Output with the new list             }
          case None => throw Exception(s"This State ID: ${currentStateID} does not exist!")

        }

      }
    }
    Output


  private def goto(input: String): Int =
    val currentStateOpt: Option[State] = states.get(currentStateID)

    currentStateOpt match {
      case Some(currentState) =>
        // Need to check if EndState!! --> output
        if (currentState.Successor != null) {
          // Access the Successor map of the current state
          currentState.Successor.get(input) match {
            case Some(nextStateID) =>
              // Update the current state ID and move to the next state
              currentStateID = nextStateID
              nextStateID
            case None =>
              // If there's no matching input in the Successor map, tell PMM to call fail
              if (currentStateID == 0) {
                0
              } else {
                -1
              }
          }
        } else {
          -1
        }


      case None =>
        throw Exception(s"This State ID: ${currentStateID} does not exist!")
    }


  // TODO Implement fail function from state to State!!

  def fail(stateID: Int): Int =
    //println(s"nothing ${stateID}")



    0

}

