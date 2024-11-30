package IO
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets
// Function to highlight substrings in the input string
def highlightSubstrings(input: String, pairs: List[(Int, String)]): String = {
  // Sort pairs by the index of the last character of the substring (ascending order)
  val sortedPairs = pairs.sortBy(_._1)

  // A mutable StringBuilder to build the highlighted string efficiently
  val result = new StringBuilder

  var currentIndex = 0

  // Iterate through the sorted pairs
  for ((index, substring) <- sortedPairs) {
    val startIndex = index - substring.length

    // Ensure indices are valid and non-overlapping
    if (startIndex >= currentIndex && index < input.length) {
      // Append the part of the original string before the current substring
      result.append(input.substring(currentIndex, startIndex))

      // Append the highlighted substring with delimiters
      result.append(s"[[${substring}]] ")

      // Update currentIndex to the position after the highlighted substring
      currentIndex = index + 1
    } else {
      // ONLY FOR DEBUG, as normal workflow shouldn't be interrupted by Exceptions
      /*throw new IllegalArgumentException(
        s"Invalid indices: startIndex=$startIndex, index=$index, currentIndex=$currentIndex, input.length=${input.length}"
      )*/
    }
  }

  // Append any remaining part of the string after the last highlighted substring
  if (currentIndex < input.length) {
    result.append(input.substring(currentIndex))
  }

  result.toString()
}




// Function to write content to a file at a given path
def writeToFile(path: String, content: String): Unit = {
  try {
    // Write the content to the file using Files.write method
    Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8))
    println(s"Content successfully written to $path")
  } catch {
    case e: Exception => println(s"Error writing to file: ${e.getMessage}")
  }
}