package IO

import Main.searchString
import Main.example
import Main.searchFile
import scala.io.StdIn

// Function to run the command line interface (CLI)
def runCLI(): Unit = {
  println("Welcome to the Aho-Corasick CLI!")
  println("Type `help` for a list of commands or `exit` to quit.")

  var running = true
  while (running) {
    print("cli> ") // Command prompt
    val input = StdIn.readLine().trim // Read user input and trim whitespace

    input match {
      case "help" =>
        println("Available commands:")
        println("  help             - Show this help message")
        println("  exit, q, quit    - Exit the CLI")
        println("  example          - Run the example function")
        println("  search file [input_file] [output_file] - Search file operation")
        println("  search [string] [substring1 substring2 ...] - Search substrings in string")

      case "exit" | "q" | "quit" =>
        println("Goodbye!")
        running = false

      case "example" =>
        example() // Call the example function

      case command if command.startsWith("search file") =>
        val args = command.stripPrefix("search file").trim.split("\\s+")
        if (args.length == 2) {
          val inputFile = args(0)
          val keywordFile = args(1)
          val output = "StandardOutput.txt"
          searchFile(inputFile, keywordFile,output)
        } else if(args.length == 2) {
          val inputFile = args(0)
          val keywordFile = args(1)
          val output = args(2)
          searchFile(inputFile, keywordFile,output)
        }else
        {
          println("Error: Missing file paths. Usage: search file [input_file] [output_file]")
        }

      case command if command.startsWith("search") =>
        // Improved regex to capture the string inside quotes and the rest of the input as substrings
        val pattern = "\"([^\"]+)\"\\s*(.*)".r
        command match {
          case pattern(mainString, substrings) =>
            val mainStr = mainString.trim
            val substringsList = substrings.trim.split("\\s+").toList
            if (mainStr.nonEmpty && substringsList.nonEmpty) {
              searchString(mainStr, substringsList)
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