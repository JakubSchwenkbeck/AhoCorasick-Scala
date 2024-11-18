




def computeFail(): Map[Int, Int] = {
  // Initialize fail function, setting all states to fail to root (state 0)
  var fail = Map[Int, Int]().withDefaultValue(0)
  val queue = scala.collection.mutable.Queue[Int]()
