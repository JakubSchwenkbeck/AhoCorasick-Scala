package GUI
import Main.State
import processing.core.PApplet

class VisualizeTrie(trie: Map[Int,State]) extends  PApplet  {
  // Processing Settings:
  override def settings(): Unit = {
    size(1200, 1000) // Windowsize
  }

  override def setup(): Unit = {
    background(255) // white backgorund
    noLoop() // not looping, as picture is static
  }

  override def draw(): Unit = {
    visTrie()
  }

  // fields of class:
  private val Trie : Map[Int, State] = trie
  private val first: State = Trie(0)
  private var x = 40
  private var y = 40


  // main handler function which calls helper functions
  private def visTrie(): Unit=

      // Draw first trie as circle!

    drawState(first,x,y) // init State

    if (first.Successor.nonEmpty) {
      for (elem <- first.Successor) {
        // for each State in our trie,
        // Draw the State and recurse on its successors
        recTrie(Trie(elem._2))

      }
    }
    println(Trie)

    // recursive function to make a BFS like rec on the Trie and draw all states accordingly
  private def recTrie(st : State) : Unit =
    // draw s, rec on Successors
    if (st != null) {
      val succ = st.Successor
      try {
        drawState(st, x, y)
      }catch {
        case e : NullPointerException =>
          println(st)
          println("x : "+x + "   y : " + y)
      }

      if (succ.nonEmpty) {
        for (elem <- succ) {
          recTrie(Trie(elem._2))
        }
      }
    }


  // drawing function for a state at given pos
  private def drawState(st: State, xpos : Int, ypos :Int): Unit ={

      val ID = st.ID
      if(st.endState){
        fill(150)
      }else {
        fill(255)
      }
      ellipse(xpos, ypos,30,30)

      // Set text alignment to center
      fill(0) // Text color
      textAlign(xpos, ypos)
      textSize(12) // Optional: Adjust text size if needed
      text(ID.toString, xpos, ypos) // Draw text at the center of the ellipse
      x += 50
      y += 50
  }
}


