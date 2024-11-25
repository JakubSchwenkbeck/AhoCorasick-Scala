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
  var x = 0
  var y = 0

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
      ellipse(xpos, ypos,30,30)

      text(ID.toString,xpos,ypos)
      x += 50
      y += 50
  }
}


