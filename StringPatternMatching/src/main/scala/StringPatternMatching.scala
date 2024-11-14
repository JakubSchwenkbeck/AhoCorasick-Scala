



// this file is the main entry point later on

@main
def main() : Unit =
  val TEST_SearchString : String = "This is her Testshe"
  val TEST_Keywords : List[String] = List("his", "her", "she")
  var FSM = FiniteStateMachine(TEST_SearchString,TEST_Keywords)
  print(s"${FSM.PMM()}" + "\n")
  printGraph(buildGraph(TEST_Keywords))
