package main






def computeFail( states: Map[Int, State]): Map[Int, Int] = {
  // Initialize fail function, setting all states to fail to root (state 0)
  var fail = Map[Int, Int]().withDefaultValue(0)
  val queue = scala.collection.mutable.Queue[Int]() // Using queue as suggested in the aho - corsick paper

  // Set failure links for root's direct successors
  for ((input, stateID) <- states(0).Successor) {
    fail += stateID -> 0 // direct successors of root point back to root!
    queue.enqueue(stateID)
  }


  // Breadth First Search : -----------------


  // BFS to compute failure links
  while (queue.nonEmpty) {
    val currentStateID = queue.dequeue()
    val currentState = states(currentStateID)

    // For each input and its successor state
    for ((input, successorID) <- currentState.Successor) {
      queue.enqueue(successorID)

      // Find the fail link for the current state
      var fallbackID = fail(currentStateID)
      while (fallbackID != 0 && !states(fallbackID).Successor.contains(input)) {
        fallbackID = fail(fallbackID)
      }


      // Update the fail link of the successor
      val fallbackSuccessorID = states(fallbackID).Successor.getOrElse(input, 0)
      fail += successorID -> fallbackSuccessorID

    }
  }

  fail
}