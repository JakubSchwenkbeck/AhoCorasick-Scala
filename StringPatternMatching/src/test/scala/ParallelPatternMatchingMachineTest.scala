import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import concurrent.ParallelPatternMatchingMachine
class ParallelPatternMatchingMachineTest extends AnyFlatSpec with Matchers {

  "ParallelPatternMatchingMachine" should "find all matching keywords in the text" in {
    // Input for the FSM
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional", "language", "powerful", "expressive", "missing")

    // Expected output
    val expectedMatches = List((5, "Scala"), (21, "functional"), (44, "language"), (61, "powerful"), (86, "expressive"))

    // Instantiate and run the FSM
    val fsm = ParallelPatternMatchingMachine(searchString, keywords)
    val actualMatches = fsm.PMM()

    // Assert that the output matches the expectation
    actualMatches shouldEqual expectedMatches
  }
  it should "be deterministicc in returning the same value for the same inputs (Leibniz principle)" in{
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional", "language", "powerful", "expressive", "missing")


    // Instantiate and run the FSM
    val fsm1 = ParallelPatternMatchingMachine(searchString, keywords)
    val matches1 = fsm1.PMM()
    val fsm2 = ParallelPatternMatchingMachine(searchString, keywords)
    val matches2= fsm2.PMM()

    matches1 shouldEqual matches2
  }

  it should "find all matching keywords with keywords which are substrings in the text" in {
    // Input for the FSM
    val searchString =
      "Scala is a functionally programming language that is powerful, concise, and expressive."
    val keywords = List("Scala", "functional","functionally","language", "powerful", "expressive", "missing")

    // Expected output
    val expectedMatches = List((5, "Scala"), (21, "functional"), (23, "functionally"),(44, "language"), (61, "powerful"), (86, "expressive"))

    // Instantiate and run the FSM
    val fsm = ParallelPatternMatchingMachine(searchString, keywords)
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
    val fsm = ParallelPatternMatchingMachine(searchString, keywords)
    val actualMatches = fsm.PMM()

    // Assert that the output matches the expectation
    actualMatches shouldEqual expectedMatches
  }

}