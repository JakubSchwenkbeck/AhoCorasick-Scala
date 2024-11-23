package Main
import IO.*
import java.nio.file.{Paths, Files}
import scala.io.Source
import java.nio.file.StandardOpenOption._
def searchString(str : String, ls : List[String]): Unit = prettyprint(FiniteStateMachine(str,ls).PMM(),str)


def example() : Unit =
  val TEST_SearchString: String = "Scala is a functionally programming language that is powerful, concise, and expressive. Many developers enjoy using Scala because of its support for both object-oriented and functional paradigms."
  val TEST_Keywords: List[String] = List("Scala", "functional", "language", "powerful", "object-oriented", "paradigms", "developers", "support")
  
  val FSM = FiniteStateMachine(TEST_SearchString, TEST_Keywords)
  println("given a text about the Scala programming language, we want to filter some keywords from the String:")
  prettyprint(FSM.PMM(), TEST_SearchString)


def searchFile(inputFile : String,keywordsFile : String, outputFile: String): Unit =
  val inputPath = Paths.get(inputFile).toAbsolutePath
  val keywordsPath = Paths.get(keywordsFile).toAbsolutePath
  val outputPath = Paths.get(outputFile).toAbsolutePath

  val str = readFile_String(inputPath.toString)
  val ls = readFile_List(keywordsPath.toString)

  val res = FiniteStateMachine(str,ls).PMM()
  
  writeToFile(outputPath.toString,str + res)