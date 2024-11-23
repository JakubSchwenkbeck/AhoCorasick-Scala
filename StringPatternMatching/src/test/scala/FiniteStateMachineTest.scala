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
}
