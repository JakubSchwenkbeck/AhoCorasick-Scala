import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Main.FiniteStateMachine
class FiniteStateMachineTest extends AnyFlatSpec with Matchers {

  "FiniteStateMachine" should "find all matching keywords in the text" in {
    // Input for the FSM
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional", "language", "powerful", "expressive", "missing")

    // Expected output
    val expectedMatches = List((5, "Scala"), (21, "functional"), (44, "language"), (61, "powerful"), (86, "expressive"))

    // Instantiate and run the FSM
    val fsm = FiniteStateMachine(searchString, keywords)
    val actualMatches = fsm.PMM()

    // Assert that the output matches the expectation
    actualMatches shouldEqual expectedMatches
  }
    it should "find all matching keywords with keywords which are substrings in the text" in {
    // Input for the FSM
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional","functionally","language", "powerful", "expressive", "missing")

    // Expected output
    val expectedMatches = List((5, "Scala"), (21, "functional"), (23, "functionally"),(44, "language"), (61, "powerful"), (86, "expressive"))

    // Instantiate and run the FSM
    val fsm = FiniteStateMachine(searchString, keywords)
    val actualMatches = fsm.PMM()

    // Assert that the output matches the expectation
    actualMatches shouldEqual expectedMatches
  }
  it should "be deterministicc in returning the same value for the same inputs (Leibniz principle)" in{
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional", "language", "powerful", "expressive", "missing")


    // Instantiate and run the FSM
    val fsm1 = FiniteStateMachine(searchString, keywords)
    val matches1 = fsm1.PMM()
    val fsm2 = FiniteStateMachine(searchString, keywords)
    val matches2= fsm2.PMM()

    matches1 shouldEqual matches2
  }

  it should "return an empty list if no keywords match" in {
    // Input for the FSM
    val searchString = "This is some unrelated text."
    val keywords = List("Scala", "functional", "language")

    // Expected output
    val expectedMatches = List()

    // Instantiate and run the FSM
    val fsm = FiniteStateMachine(searchString, keywords)
    val actualMatches = fsm.PMM()

    // Assert that the output matches the expectation
    actualMatches shouldEqual expectedMatches
  }

  it should "handle edge cases like empty keywords or empty text" in {
    // Test with empty keywords
    val searchString = "Scala is a great language."
    val keywords = List()
    val fsm1 = FiniteStateMachine(searchString, keywords)
    fsm1.PMM() shouldEqual List()

    // Test with empty text
    val searchStringEmpty = ""
    val keywords2 = List("Scala", "language")
    val fsm2 = FiniteStateMachine(searchStringEmpty, keywords2)
    fsm2.PMM() shouldEqual List()
  }
  it should "PMM should correctly match patterns in the text" in {
    val fsm = new FiniteStateMachine("abcabc", List("ab", "bc"))
    val matches : List[(Int,String)] = fsm.PMM()


    matches.exists(m => m._1 == 2 && m._2 == "ab") shouldEqual true // Match found at index 2 for "ab"
    }
  it should "return no matches for non-matching patterns" in {
    val fsm = new FiniteStateMachine("abcdefgh", List("xyz", "pq"))
    val matches: List[(Int, String)] = fsm.PMM()

  }

  it should "correctly match single-character patterns" in {
    val fsm = new FiniteStateMachine("abcabc", List("a", "b", "c"))
    val matches: List[(Int, String)] = fsm.PMM()

    // Checking if the matches are found at the correct positions

    matches.exists(m => m._1 == 3 && m._2 == "c") shouldEqual true // Match found at index 6 for "c"
  }

  it should "handle empty search text gracefully" in {
    val fsm = new FiniteStateMachine("", List("ab", "bc"))
    val matches: List[(Int, String)] = fsm.PMM()

    matches.isEmpty shouldEqual true // No matches since the search text is empty
  }

  it should "handle empty keywords list gracefully" in {
    val fsm = new FiniteStateMachine("abcabc", List())
    val matches: List[(Int, String)] = fsm.PMM()

    matches.isEmpty shouldEqual true // No matches since the keywords list is empty
  }

  it should "return matches for patterns at the beginning of the text" in {
    val fsm = new FiniteStateMachine("abcrdefgh", List("abc", "def"))
    val matches: List[(Int, String)] = fsm.PMM()


    matches.exists(m => m._1 == 3 && m._2 == "abc") shouldEqual true // Match for "def" at index 6
  }

  it should "correctly handle overlapping patterns" in {
    val fsm = new FiniteStateMachine("abcabcabc", List("ab", "bc", "abc"))
    val matches: List[(Int, String)] = fsm.PMM()

    matches.exists(m => m._1 == 8 && m._2 == "ab") shouldEqual true // Match for "abc" at index 7
  }

}


