


// this file is the main entry point for this Project
/**
 * The entry point for the Finite State Machine (FSM) demonstration.
 *
 * This method:
 * 1. Defines a test search string (`TEST_SearchString`) and a list of keywords (`TEST_Keywords`).
 * 2. Initializes a `FiniteStateMachine` instance with the search string and keywords.
 * 3. Executes the pattern-matching mechanism (`PMM`) of the FSM to identify matches.
 * 4. Prints the results of the FSM's pattern-matching function using `prettyprint`.
 */
@main
def main() : Unit =
  // input for the machine:
  val TEST_SearchString: String = "Scala is a functionally programming language that is powerful, concise, and expressive. Many developers enjoy using Scala because of its support for both object-oriented and functional paradigms."
  val TEST_Keywords: List[String] = List("Scala", "functional", "functionally","language", "powerful", "object-oriented", "paradigms", "developers", "support")


  // create an object of the FinitStateMachine:
  val FSM = FiniteStateMachine(TEST_SearchString,TEST_Keywords)

  // print some information :


  // print(s"${FSM.PMM()}" + "\n")
  prettyprint(FSM.PMM(),TEST_SearchString)
  //printGraph(buildGraph(TEST_Keywords))


/**
 * Pretty-Print function for the result of the StringPatternMatching, prints the string with highlighted keywords
 *
 * @param ls A list of pairs : (index of last Char where keyword was found, keyword) ,so (Integer,String)
 * @param fulltext The String where substring search is performed on
 */

def prettyprint(ls : List[(Int, String)], fulltext : String) : Unit =
  if(ls.isEmpty){
    println("No keyword could be matched in the String : " + fulltext)
  }else {
    try{

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
    println(highlightedText)}


    catch{

      case e : Throwable =>
      println("Some Error occurred! Here are the original string and a list of keywords and their last index inside the string: ")
      println(fulltext)
      println(ls)
    }
  }
