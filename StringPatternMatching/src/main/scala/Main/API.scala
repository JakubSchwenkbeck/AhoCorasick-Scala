package Main

import IO.*
import java.nio.file.{Paths, Files}
import scala.io.Source
import java.nio.file.StandardOpenOption._

/**
 * Searches for occurrences of keywords within a string and prints the result in a formatted way.
 *
 * @param str The string in which to search for keywords.
 * @param ls A list of keywords to search for in the string.
 */
def searchString(str: String, ls: List[String]): Unit =
  prettyprint(FiniteStateMachine(str, ls).PMM(), str)

/**
 * Demonstrates keyword searching with a predefined example.
 *
 * This function creates a test string about Scala programming and searches for specific
 * keywords within it. The results are printed to the console in a formatted manner.
 */
def example(): Unit =
  val TEST_SearchString: String = "Scala is a functionally programming language that is powerful, concise, and expressive. Many developers enjoy using Scala because of its support for both object-oriented and functional paradigms."
  val TEST_Keywords: List[String] = List("Scala", "functional", "language", "powerful", "object-oriented", "paradigms", "developers", "support")

  val FSM = FiniteStateMachine(TEST_SearchString, TEST_Keywords)
  println("Given a text about the Scala programming language, we want to filter some keywords from the string:")
  prettyprint(FSM.PMM(), TEST_SearchString)

/**
 * Searches for keywords within the content of an input file and writes the results to an output file.
 *
 * @param inputFile The path to the input file containing the text to be searched.
 * @param keywordsFile The path to the file containing keywords, one per line.
 * @param outputFile The path to the output file where results will be saved. The output file will contain
 *                   the original text followed by a formatted representation of the matches.
 */
def searchFile(inputFile: String, keywordsFile: String, outputFile: String): Unit =
  val inputPath = Paths.get(inputFile).toAbsolutePath
  val keywordsPath = Paths.get(keywordsFile).toAbsolutePath
  val outputPath = Paths.get(outputFile).toAbsolutePath

  // Read the content of the input and keywords files
  val str = readFile_String(inputPath.toString)
  val ls = readFile_List(keywordsPath.toString)

  // Perform keyword searching using the Finite State Machine
  val res = FiniteStateMachine(str, ls).PMM()



  // Write the original text and search results to the output file
   writeToFile(outputPath.toString,   highlightSubstrings(str,res))
