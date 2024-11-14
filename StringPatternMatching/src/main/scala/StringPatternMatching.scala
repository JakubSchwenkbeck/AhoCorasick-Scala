



// this file is the main entry point later on

@main
def main() : Unit =
  println("nothing implemented yet")
  val TEST_SearchString : String = "This is her Testshe"
  val TEST_Keywords : List[String] = List("his", "her", "she")
  var FSM = FiniteStateMachine(TEST_SearchString,TEST_Keywords)
  printGraph(buildGraph(TEST_Keywords))

