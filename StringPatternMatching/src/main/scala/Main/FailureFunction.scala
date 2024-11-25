package Main

/**
 * Computes the failure function for an Aho-Corasick automaton.
 *
 * The failure function is a key component of the Aho-Corasick algorithm,
 * used to handle mismatched inputs during pattern matching by finding the 
 * longest suffix that is also a prefix.
 *
 * @param states A map representing the automaton's states. Each state has a unique ID
 *               and contains its successor states (transitions).
 *               - `states(0)` is assumed to be the root state.
 * @return A map representing the failure function. Each state ID maps to its
 *         corresponding failure state ID.
 */
def computeFail(states: Map[Int, State]): Map[Int, Int] = {
  // Initialize the fail function, setting all states to fail to the root (state 0)
  var fail = Map[Int, Int]().withDefaultValue(0)

  // Queue for Breadth-First Search (BFS) traversal of the automaton
  val queue = scala.collection.mutable.Queue[Int]()

  // Set failure links for root's direct successors
  for ((input, stateID) <- states(0).Successor) {
    fail += stateID -> 0 // Direct successors of root point back to root
    queue.enqueue(stateID) // Enqueue each direct successor for BFS processing
  }

  // BFS to compute failure links for the remaining states
  while (queue.nonEmpty) {
    val currentStateID = queue.dequeue() // Dequeue the current state for processing
    val currentState = states(currentStateID)

    // Process each transition from the current state
    for ((input, successorID) <- currentState.Successor) {
      queue.enqueue(successorID) // Enqueue the successor state for further processing

      // Start fallback resolution from the failure state of the current state
      var fallbackID = fail(currentStateID)
      while (fallbackID != 0 && !states(fallbackID).Successor.contains(input)) {
        fallbackID = fail(fallbackID) // Move to the next fallback state
      }

      // Update the fail link of the successor state
      val fallbackSuccessorID = states(fallbackID).Successor.getOrElse(input, 0)
      fail += successorID -> fallbackSuccessorID
    }
  }

  fail // Return the computed failure function as a map
}
