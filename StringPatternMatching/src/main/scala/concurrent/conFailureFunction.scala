package main

import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._

def con_computeFail(states: Map[Int, State]): Map[Int, Int] = {
  // Initialize fail function, setting all states to fail to root (state 0)
  val fail = TrieMap[Int, Int]().withDefaultValue(0)
  val queue = mutable.Queue[Int]() // Using queue as suggested in the Aho-Corasick paper

  // Set failure links for root's direct successors
  for ((input, stateID) <- states(0).Successor) {
    fail += stateID -> 0 // Direct successors of root point back to root!
    queue.enqueue(stateID)
  }

  // Function to process a single state
  def processState(currentStateID: Int): Unit = {
    val currentState = states(currentStateID)

    // For each input and its successor state
    for ((input, successorID) <- currentState.Successor) {
      queue.synchronized { queue.enqueue(successorID) }

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

  // Process the queue concurrently
  while (queue.nonEmpty) {
    val tasks = queue.synchronized {
      val batch = queue.dequeueAll(_ => true) // Fetch all queued states
      batch
    }

    tasks.par.foreach(processState) // Process the batch in parallel
  }

  fail.toMap // Convert TrieMap to regular Map for final result
}
