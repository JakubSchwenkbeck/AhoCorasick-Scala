package main


import concurrent.concurrent_computeFail

case class State(ID: Integer, Successor: Map[String, Int], endState: Boolean, keyword: Option[String] = None) // all states have an own ID and a single successor which is mapped by its input, also known by its ID


/**
 * Class which holds all the values and logic of building the FiniteStateMachine and performing the PatternMatching
 *
 * @param SearchText String which holds the Text to be searched
 * @param Keywords List of Strings with the keyword which should be matched
 */

class FiniteStateMachine(SearchText: String, Keywords: List[String]) {

  private val states: Map[Int, State] = buildGraph(Keywords) // This map represents the finite state machine
  private val text: String = SearchText
  private var keywords: List[String] = Keywords
  private val fails : Map[Int,Int] = computeFail(states)

  private var currentStateID: Int = 0 // starting State is by default 0 !

  def getCurrentStateID: Int = currentStateID // simple getter for the current ID


  /**
   *  This function performs the PatternMatching of the keywords in the SearchString
   * @return Returns a List of Pairs with Index of the Last char of a keyword and the keyword itself, so List[(Int,String)]
   */
  def PMM(): List[(Int, String)] =

    var Output: List[(Int, String)] = List() // empty list

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


          } // Reassign Output with the new list
          case None => throw Exception(s"This State ID: $currentStateID does not exist!")

        }

      }
    }
    Output

  /**
   * GoTo Function performs the action of taking an input and moving in the finite state machine
   *
   * @param input String which represents a single Char as input for the current state
   * @return Returns the ID of the following State, -1 represents fail state
   */

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
        throw Exception(s"This State ID: $currentStateID does not exist!")
    }




  def fail(stateID: Int): Int = { // actual magic happens in computeFail
    fails(stateID) // Return the fail link for the current state
  }

}

