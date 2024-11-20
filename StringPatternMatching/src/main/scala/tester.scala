import org.scalajs.dom

object SimpleSquareVisualizer {
  def main(): Unit = {
    // Add an event listener to wait for the DOM to be fully loaded
    dom.document.addEventListener("DOMContentLoaded", { (_: dom.Event) =>
      dom.console.log("DOM fully loaded and parsed!")
      val square = dom.document.createElement("div")
      square.setAttribute("id", "test-square")
      square.setAttribute(
        "style",
        "width: 100px; height: 100px; background-color: red; margin: 20px; position: relative;"
      )
      dom.console.log("Square is being added!")
      dom.document.body.appendChild(square)
    })
  }
}
