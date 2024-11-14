



// this file is the main entry point later on

@main
def main() : Unit =
  println("nothing implemented yet")
  var TEST_States : Map[Int, State] = Map(0 -> State(0,Map("h" -> 1), false),1 -> State(1,null,true) )
  var TEST_SearchString : String = "This is her Testshe"
  var TEST_Keywords : List[String] = List("his", "her", "she")
  var FSM = FiniteStateMachine(TEST_SearchString,TEST_Keywords,ahoCorasickGraph)
  println(s"${FSM.PMM()}")

  print(ahoCorasickGraph)