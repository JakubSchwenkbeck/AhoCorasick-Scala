package IO

import API.{example, searchFile, searchString,con_searchString,con_searchFile}
import scala.io.StdIn
import GUI.Gui_Main
import concurrent.Benchmark
// Function to run the command line interface (CLI)
def runCLI(): Unit = {
  println("Welcome to the Aho-Corasick CLI!")
  println("Type `help` for a list of commands or `exit` to quit.")
  println("Quick Start : type `example` for an example of the algorithm")


  var running = true
  while (running) {
    print("cli> ") // Command prompt
    val input = StdIn.readLine().trim // Read user input and trim whitespace

    input match {
      case "help" =>
        println("Available commands:")
        println("  help             - Show this help message")
        println("  exit, q, quit    - Exit the CLI")
        println("  gui              - start the GUI")
        println("  example          - Run the example function")
        println("  benchmark        - Run the Benchmark for normal vs concurrent")
        println("  search file [input_file] [keyword_file] [output_file]  - Search file operation")
        println("  search [string] [substring1 substring2 ...] - Search substrings in string")
        println("  searchcon file [input_file] [keyword_file] [output_file]  - Search file concurrently")
        println("  searchcon [string] [substring1 substring2 ...] - Search concurrently substrings in string")


      case "exit" | "q" | "quit" =>
        println("Goodbye!")
        running = false

      case "gui" => Gui_Main.main(Array())

      case "example" =>
        example() // Call the example function

      case "benchmark" =>
         Benchmark.main(Array())

      case command if command.startsWith("search file") =>
        val args = command.stripPrefix("search file").trim.split("\\s+")
        if (args.length == 1) {
          // standard
          searchFile("src/main/scala/files/search.txt", "src/main/scala/files/keywords.txt", "src/main/scala/files/output.txt")
        } else if(args.length == 3) {
          val inputFile = args(0)
          val keywordFile = args(1)
          val output = args(2)
          searchFile(inputFile, keywordFile,output)
        }else
        {
          println("Error: Missing file paths. Usage: search file [input_file] [output_file]")
        }

      case command if command.trim.startsWith("search") =>
        // Enhanced regex: Match optional quoted string and optional trailing substrings
        val pattern = """(?i)search\s+\"([^\"]*)\"(?:\s+(.*))?""".r

        command.trim match {
          case pattern(mainString, substrings) =>
            val mainStr = Option(mainString).map(_.trim).getOrElse("")
            val substringsList = Option(substrings)
              .map(_.trim.split("\\s+").filter(_.nonEmpty).toList)
              .getOrElse(Nil)

            println(s"Main String: $mainStr")
            println(s"Substrings List: $substringsList")

            if (mainStr.nonEmpty && substringsList.nonEmpty) {
              searchString(mainStr, substringsList)
            } else {
              println("Error: Missing string or substrings. Usage: search [\"string\"] [substring1 substring2 ...]")
            }
          case _ =>
            println("Error: Invalid syntax. Usage: search [\"string\"] [substring1 substring2 ...]")
        }
      case command if command.startsWith("searchcon file") =>
        val args = command.stripPrefix("searchcon file").trim.split("\\s+")
        if (args.length == 1) {
          // standard
          con_searchFile("src/main/scala/files/search.txt", "src/main/scala/files/keywords.txt", "src/main/scala/files/output.txt")
        } else if(args.length == 3) {
          val inputFile = args(0)
          val keywordFile = args(1)
          val output = args(2)
          searchFile(inputFile, keywordFile,output)
        }else
        {
          println("Error: Missing file paths. Usage: search file [input_file] [output_file]")
        }

      case command if command.trim.startsWith("searchcon") =>
        // Enhanced regex: Match optional quoted string and optional trailing substrings
        val pattern = """(?i)searchcon\s+\"([^\"]*)\"(?:\s+(.*))?""".r

        command.trim match {
          case pattern(mainString, substrings) =>
            val mainStr = Option(mainString).map(_.trim).getOrElse("")
            val substringsList = Option(substrings)
              .map(_.trim.split("\\s+").filter(_.nonEmpty).toList)
              .getOrElse(Nil)

            println(s"Main String: $mainStr")
            println(s"Substrings List: $substringsList")

            if (mainStr.nonEmpty && substringsList.nonEmpty) {
              con_searchString(mainStr, substringsList)
            } else {
              println("Error: Missing string or substrings. Usage: search [\"string\"] [substring1 substring2 ...]")
            }
          case _ =>
            println("Error: Invalid syntax. Usage: search [\"string\"] [substring1 substring2 ...]")
        }
      case "" => // Do nothing on empty input


      case unknown =>
        println(s"Unknown command: $unknown")
    }
  }
}