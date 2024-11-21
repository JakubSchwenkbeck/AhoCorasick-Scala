package Main
import IO.*

def searchString(str : String, ls : List[String]): Unit = prettyprint(FiniteStateMachine(str,ls).PMM(),str)


def example() : Unit =
  val TEST_SearchString: String = "Scala is a functionally programming language that is powerful, concise, and expressive. Many developers enjoy using Scala because of its support for both object-oriented and functional paradigms."
  val TEST_Keywords: List[String] = List("Scala", "functional", "language", "powerful", "object-oriented", "paradigms", "developers", "support")
  
  val FSM = FiniteStateMachine(TEST_SearchString, TEST_Keywords)

  prettyprint(FSM.PMM(), TEST_SearchString)


def searchFile(SearchText : String,Keywords : String, output: String): Unit =
  val str = readFile_String(SearchText)
  val ls = readFile_List(Keywords)

  val res = FiniteStateMachine(str,ls).PMM()
  
  writeToFile(output,highlightSubstrings(str,res))