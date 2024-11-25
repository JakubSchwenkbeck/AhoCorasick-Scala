package GUI
import Main.State
import processing.core.PApplet

class VisualizeTrie(trie: Map[Int,State]) extends  PApplet  {
  override def settings(): Unit = {
    size(1800, 1000) // Fenstergröße
  }

  override def setup(): Unit = {
    background(255) // Weißer Hintergrund
    noLoop()
  }

  override def draw(): Unit = {
    visTrie()
  }
  private val Trie : Map[Int, State] = trie
  private val first: State = Trie(0)
  private var x = 40
  private var y = 40

  def visTrie(): Unit=

      // Draw first trie as circle!

    drawState(first,x,y)
    if (first.Successor.nonEmpty) {
      for (elem <- first.Successor) {
        // for each State in our trie,


        // Draw the State and recurse on its successors
        recTrie(Trie(elem._2))

      }
    }
    println(Trie)
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


