



// this file is the main entry point later on

@main
def main() : Unit =
  val TEST_SearchString : String = "This is her Testshe"
  val TEST_Keywords : List[String] = List("his", "her", "she")
  val FSM = FiniteStateMachine(TEST_SearchString,TEST_Keywords)
 // print(s"${FSM.PMM()}" + "\n")
  prettyprint(FSM.PMM(),TEST_SearchString)
  //printGraph(buildGraph(TEST_Keywords))






def prettyprint(ls : List[(Int, String)],fulltext : String) : Unit =
  if(ls.isEmpty){
    println("No keyword could be matched in the String : " + fulltext)
  }else {
    // ANSI escape codes for colors
    val RESET = "\u001B[0m"
    val HIGHLIGHT = "\u001B[31m" // Red text
    println("Following keywords were found:")
    // Sort the list by index for consistent highlighting
    val sortedLs = ls.sortBy(_._1)
    // Build the highlighted string
    val highlightedText = sortedLs.foldLeft(("", 0)) { case ((result, lastIdx), (endIdx, keyword)) =>
      val startIdx = endIdx - keyword.length
      // Append text before the current keyword and highlight the keyword
      val normalText = fulltext.substring(lastIdx, startIdx)
      val highlightedKeyword = s"$HIGHLIGHT${fulltext.substring(startIdx, endIdx )}$RESET"

      (result + normalText + highlightedKeyword, endIdx )
    } match {
      case (result, lastIdx) => result + fulltext.substring(lastIdx) // Append remaining text
    }

    // Print the full highlighted text
    println(highlightedText)
  }
