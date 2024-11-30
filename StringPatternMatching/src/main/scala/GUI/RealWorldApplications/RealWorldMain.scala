package GUI.RealWorldApplications
import processing.core.PApplet
// Function to display introduction text about real-world applications of Aho-Corasick
import GUI.UTILS.DrawingUtils.*
import GUI.RealWorldApplications.*
class RealWorldMain(parent : PApplet) {

  val Mw: MalwareDetection = new MalwareDetection(parent)

  sealed trait RWState

  case object Home extends RWState

  case object Malware extends RWState

  case object DNA extends RWState

  var currState: RWState = Home

  def draw(): Unit = {
    currState match {
      case Home =>
        displayIntroText(parent)

      case Malware =>
        Mw.displayMalwareDetectionExample(parent)

      case DNA =>
        // Add a function to display DNA-related visualizations
        println("DNA visualization not yet implemented")
    }
  }


  def displayIntroText (parent: PApplet): Unit
  =
  {
    val yPosition = 150 // Starting position for the text block
    val xPosition = 50
    parent.textAlign(0, 0) // Align text to the left and center
    parent.fill(0) // Default text color

    // Larger text for title
    parent.textSize(20)
    parent.text("Aho-Corasick Algorithm in Real-World Applications:", xPosition, yPosition)
    var currentY = yPosition + 40

    // Set color for regular text (dark gray)
    parent.fill(50)
    parent.textSize(16)

    // Description of the algorithm
    parent.text("The Aho-Corasick algorithm is a fast, efficient multi-pattern string matching algorithm.", xPosition, currentY)
    currentY += 30
    parent.text("It is widely used for searching multiple keywords in a large corpus of text. Unlike traditional string matching algorithms that search for one pattern at a time,", xPosition, currentY)
    currentY += 30
    parent.text("the Aho-Corasick algorithm builds a finite automaton from a set of patterns and processes the text in one pass.", xPosition, currentY)
    currentY += 30
    parent.text("This makes it significantly faster when dealing with multiple patterns simultaneously.", xPosition, currentY)
    currentY += 60

    // More color for headings (Blue)
    parent.fill(0, 0, 255)
    parent.textSize(18)
    parent.text("Real-World Applications:", xPosition, currentY)
    currentY += 30

    // Regular text color again
    parent.fill(50)
    parent.textSize(16)

    // List of applications with explanations
    parent.text("- **Spam Filtering**: Quickly detect spam messages by searching for multiple spam keywords in real-time email filtering systems.", xPosition, currentY)
    currentY += 30
    parent.text("- **Malware Signature Detection**: Identify malicious files by matching known malware signatures against large data sets.", xPosition, currentY)
    currentY += 30
    parent.text("- **DNA Sequence Analysis**: Search for known DNA patterns or mutations in genetic data for medical research.", xPosition, currentY)
    currentY += 30
    parent.text("- **Log File Scanning**: Efficiently analyze system logs for error patterns, suspicious activities, or critical warnings.", xPosition, currentY)
    currentY += 30

    // More specific examples section (use bold here)
    parent.fill(0, 0, 255) // Change color to blue for example heading
    parent.textSize(18)
    parent.text("Let’s explore two specific examples of how Aho-Corasick can be used:", xPosition, currentY)
    currentY += 30

   /* parent.fill(50) // Reset color for the list
    parent.textSize(16)
    parent.text("- **Spam Filtering**: Identifying multiple spam keywords within email content to block spam.", xPosition, currentY)
    currentY += 30
    parent.text("- **Log File Scanning**: Detecting critical log patterns such as ERROR or WARNING in real-time log files.", xPosition, currentY)
    currentY += 30
    */


    // Final summary
    parent.fill(50)
    parent.textSize(16)
    parent.text("The Aho-Corasick algorithm offers a powerful solution for these and many other use cases where fast and efficient pattern matching is required.", xPosition, currentY)
    currentY += 40

    // Ending note
    parent.text("Let’s dive into the details of each of these examples to see how Aho-Corasick can improve performance and accuracy.", xPosition, currentY)
  }

  def setMalware() :Unit = currState = Malware

  def setHome(): Unit = currState = Home

}