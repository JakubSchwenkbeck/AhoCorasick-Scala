package concurrent

import scala.collection.concurrent.TrieMap
import scala.collection.parallel.CollectionConverters._
import main.buildGraph
import main.State
import scala.collection.mutable.ListBuffer
class ParallelPatternMatchingMachine(SearchText: String, Keywords: List[String]) {

  private val states: Map[Int, State] = buildGraph(Keywords)
  private val text: String = SearchText
  private val keywords: List[String] = Keywords
  private val fails: Map[Int, Int] = con_computeFail(states)
  private val maxKeywordLength: Int = keywords.map(_.length).max

  def PMM(): List[(Int, String)] = {
    // Define the segment size with overlap
    val segmentPercentage = 0.1 // Each segment will be 10% of the total text length
    val segmentSize = (text.length * segmentPercentage).toInt

    val overlap = maxKeywordLength - 1

    // Create overlapping segments
    val segments = text.sliding(segmentSize + overlap, segmentSize).zipWithIndex.toArray

    // Process segments in parallel
    val segmentResults = segments.par.flatMap { case (segment, segmentIndex) =>
      val localFSM = new SegmentFSM(states, fails, segment, keywords)
      val localResults = localFSM.PMM()

      // Adjust positions for this segment
      localResults.map { case (pos, keyword) =>
        val adjustedPos = pos + segmentIndex * segmentSize
        (adjustedPos, keyword)
      }
    }

    // Combine and sort results
    segmentResults.toList.distinct.sortBy(_._1)

  }

  private class SegmentFSM(
                            states: Map[Int, State],
                            fails: Map[Int, Int],
                            segment: String,
                            keywords: List[String]
                          ) {
    private var localStateID: Int = 0

    def PMM(): List[(Int, String)] = {
      val output = ListBuffer[(Int, String)]()
      for ((char, index) <- segment.zipWithIndex) {
        var gotoOutput = goto(localStateID, char.toString)
        while (gotoOutput == -1) {
          localStateID = fail(localStateID)
          gotoOutput = goto(localStateID, char.toString)
        }
        localStateID = gotoOutput
        states.get(localStateID).foreach { currentState =>
          if (currentState.endState) {
            currentState.keyword.foreach { keyword =>
              output += ((index + 1, keyword))
            }
          }
        }
      }
      output.toList
    }

    private def goto(stateID: Int, input: String): Int = {
      states.get(stateID) match {
        case Some(currentState) =>
          currentState.Successor.get(input).getOrElse {
            if (stateID == 0) 0 else -1
          }
        case None =>
          throw new Exception(s"This State ID: $stateID does not exist!")
      }
    }

    private def fail(stateID: Int): Int = {
      fails.getOrElse(stateID, 0)
    }
  }
}
