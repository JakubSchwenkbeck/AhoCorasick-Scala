

val ahoCorasickGraph: Map[Int, State] = Map(
  0 -> State(0, Map("h" -> 1, "s" -> 3), endState = false),
  1 -> State(1, Map("i" -> 6, "e" -> 2), endState = false),
  2 -> State(2, Map("r" -> 8), endState = false),
  3 -> State(3, Map("h" -> 4), endState = false),
  4 -> State(4, Map("e" -> 5), endState = false),
  5 -> State(5, Map(), endState = true), // "she" ends here
  6 -> State(6, Map("s" -> 7), endState = false),
  7 -> State(7, Map(), endState = true), // "his" ends here
  8 -> State(8, Map("s" -> 9), false),
  9 -> State(9, Map(), true) // "this" ends here

)


def buildGraph(keywords: List[String]): Map[Int, State] = {
  var nextID = 0
  var states = Map[Int, State](nextID -> State(nextID, Map(), endState = false)) // Root state

  for (keyword <- keywords) {
    var currentStateID = 0 // Start at the root state

    for (char <- keyword) {
      val currentState = states(currentStateID)

      // Check if there's already a state for this character, else create a new state
      val nextStateID = currentState.Successor.getOrElse(char.toString, {
        nextID += 1
        nextID
      })

      // Update the current state to include the new successor
      states = states.updated(currentStateID, currentState.copy(Successor = currentState.Successor + (char.toString -> nextStateID)))

      // Add the new state if it doesn't exist
      if (!states.contains(nextStateID)) {
        states += nextStateID -> State(nextStateID, Map(), endState = false)
      }

      // Move to the next state
      currentStateID = nextStateID
    }

    // Mark the last state of the keyword as an end state
    val finalState = states(currentStateID)
    states += currentStateID -> finalState.copy(endState = true, keyword = Some(keyword))  }

  states
}
def printGraph(states: Map[Int, State]): Unit = {
  states.foreach { case (id, state) =>
    val keywordStr = state.keyword match {
      case Some(kw) => s", Keyword = $kw"
      case None => ""
    }
    println(s"State $id: Successors = ${state.Successor}, End State = ${state.endState}$keywordStr")
  }
}
