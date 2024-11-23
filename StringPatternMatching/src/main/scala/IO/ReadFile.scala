package IO


import scala.io.Source
import java.io.File

  def readFile_String(path: String): String = {
    val source = Source.fromFile(new File(path))
    try {
      source.mkString  // Reads the entire file as a single string
    } finally {
      source.close()  // Always close the source after reading
    }
  }
def readFile_List(path: String): List[String] = {
  val source = Source.fromFile(new File(path))
  try {
    source.mkString.split("[,\\s]+").toList // Splits by commas or spaces and converts to a List
  } finally {
    source.close() // Always close the source after reading
  }
}