// This class defines a finite state machine which pattern matches string inputs with the keywords

case class State(ID : Integer , Successor : Map[String,Int]) // all states have an own ID and a single successor which is mapped by its input, also known by its ID
class FiniteStateMachine(SearchText: String, Keywords : List[String],InputStates : Map[Int, State]){

  private var states : Map[Int, State] = InputStates
  private var text : String = SearchText
  private var keywords : List[String] = Keywords


  private val currentStateID : Int = 0
  def getCurrentStateID : Int = currentStateID

  def goto(input : String) : Int =
    val currentState = states.getOrElse(currentStateID,0)
    if(currentState == 0){
      ???
      // fail function
    }else {
      //check input with successor map
    }





    0




}

