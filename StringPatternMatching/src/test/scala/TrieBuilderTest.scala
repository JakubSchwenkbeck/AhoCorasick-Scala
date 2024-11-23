import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Main.buildTrie
import Main.State

import scala.collection.immutable.HashMap


class TrieBuilderTest extends AnyFlatSpec with Matchers {

  "buildTrie" should "build a correct Trie based on keywords" in {
    // Input for the trie
    val keywords = List("hers", "she", "his")

    // Expected output
    val expectedMatches = HashMap(0 -> State(0, Map("h" -> 1, "s" -> 5), false, None), 5 -> State(5, Map("h" -> 6), false, None), 1 -> State(1, Map("e" -> 2, "i" -> 8), false, None), 6 -> State(6, Map("e" -> 7), false, None), 9 -> State(9, Map(), true, Some("his")), 2 -> State(2, Map("r" -> 3), false, None), 7 -> State(7, Map(), true, Some("she")), 3 -> State(3, Map("s" -> 4), false, None), 8 -> State(8, Map("s" -> 9), false, None), 4 -> State(4, Map(), true, Some("hers")))


    // Instantiate and run the FSM
    val trie = buildTrie(keywords)


    // Assert that the output matches the expectation
    trie shouldEqual expectedMatches
  }
}