
// Parse function to create a Map from IDs to State objects
def Parse(input: String): Map[Int, State] = {
  // Split the input string by " | " to get individual state entries
  val stateStrings = input.split("""\s*\|\s*""").filter(_.nonEmpty)

  // Parse each state entry and build the Map
  stateStrings.map { stateString =>
    // Extract the components of each state entry
    // Format: "ID, input -> SuccID , bool"
    val pattern = """(\d+),\s*(\w+)\s*->\s*(\d+)\s*,\s*(true|false)""".r

    stateString match {
      case pattern(idStr, input, succIdStr, boolStr) =>
        val id = idStr.toInt
        val succId = succIdStr.toInt
        val endState = boolStr.toBoolean
        val successor = Map(input.toString -> succId)

        // Return the ID and the corresponding State instance
        id -> State(id, successor, endState)

      case _ =>
        throw new IllegalArgumentException(s"Invalid state format: $stateString")
    }
  }.toMap
}

val ahoCorasickGraph = Map(
  0 -> State(0, Map("h" -> 1, "s" -> 6), endState = false),
  1 -> State(1, Map("i" -> 2, "e" -> 4), endState = false),
  2 -> State(2, Map("s" -> 3), endState = false),
  3 -> State(3, Map(), endState = true),       // "his" ends here
  4 -> State(4, Map("r" -> 5), endState = false),
  5 -> State(5, Map("s" -> 3), endState = true), // "hers" ends here
  6 -> State(6, Map("h" -> 7), endState = false),
  7 -> State(7, Map("e" -> 3), endState = true) // "she" ends here
)
