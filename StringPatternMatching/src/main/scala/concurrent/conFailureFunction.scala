package concurrent

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.collection.concurrent.TrieMap
import scala.concurrent.duration._

import main.State

def concurrent_computeFail(states: Map[Int, State]): Map[Int, Int] = {
  // Use an immutable map for fail and a thread-safe structure for updates
  val fail = TrieMap[Int, Int]().withDefaultValue(0)
  val queue = mutable.Queue[Int]()

  // Set failure links for root's direct successors
  for ((input, stateID) <- states(0).Successor) {
    fail += stateID -> 0 // direct successors of root point back to root!
    queue.enqueue(stateID)
  }

  // Define a helper function to process each state
  def processState(currentStateID: Int): Unit = {
    val currentState = states(currentStateID)

    // For each input and its successor state
    for ((input, successorID) <- currentState.Successor) {
      // Enqueue successor
      queue.enqueue(successorID)

      // Find the fail link for the current state
      var fallbackID = fail(currentStateID)
      while (fallbackID != 0 && !states(fallbackID).Successor.contains(input)) {
        fallbackID = fail(fallbackID)
      }

      // Update the fail link of the successor
      val fallbackSuccessorID = states(fallbackID).Successor.getOrElse(input, 0)
      fail.put(successorID, fallbackSuccessorID)
    }
  }

  // Start BFS and process successors concurrently
  val futures = mutable.ListBuffer[Future[Unit]]()

  while (queue.nonEmpty) {
    val currentStateID = queue.dequeue()
    futures += Future {
      processState(currentStateID)
    }
  }

  // Wait for all futures to complete
  Await.result(Future.sequence(futures), Duration.Inf)

  // Convert the fail map to immutable and return
  fail.toMap
}
