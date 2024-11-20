# **String Pattern Matching with Aho-Corasick Algorithm**

## **Overview**
This project implements the Aho-Corasick algorithm for efficient **multi-pattern string matching**. The algorithm, introduced in the seminal [Aho-Corasick paper](https://cr.yp.to/bib/1975/aho.pdf), is widely used in fields such as text searching, bioinformatics, and network security for its ability to match multiple patterns simultaneously in linear time.

I chose **Scala** for this implementation due to its powerful combination of **functional programming** paradigms and robust **data modeling** capabilities. This project also serves as a hands-on exploration of Scala's strengths in algorithmic problem-solving.

---

## **Features**
- **Core Implementation**: Implements the Aho-Corasick algorithm, including:
  - Construction of the trie (state machine).
  - Efficient transition and failure functions for pattern matching.
  - Handling of multiple patterns and outputs simultaneously.
- **Planned Enhancements** (Open Issues):
  - **GUI Integration**: Intuitive graphical representation of the trie and live demonstration of pattern matching.
  - **Concurrency**: Utilizing Scala's concurrency features (e.g., Futures, Akka) to improve performance for large-scale input.

  Checkout the different branches to follow the progress of each new feature!
---

## **Why Aho-Corasick?**
The Aho-Corasick algorithm is fundamental for applications requiring fast sub-string or pattern matching across massive datasets. Its efficient **O(n + m + z)** runtime (where `n` is the length of the text, `m` is the total length of patterns, and `z` is the number of matches) makes it ideal for:
- Intrusion detection systems.
- DNA sequencing analysis.
- Real-time text filtering.

---

## **Why Scala?**
Scala was chosen for its balance of **functional programming** and **object-oriented paradigms**, which simplifies:
- **Data Modeling**: Representing the trie and transitions elegantly using immutable data structures.
- **High-Level Abstractions**: Clean handling of recursive and stateful algorithms.
- **Concurrency**: Powerful tools like Akka Streams and Futures for optimizing performance in planned enhancements.

---

## **Learnings and Challenges**
This project represents a deep dive into:
- **The Aho-Corasick Algorithm**: Understanding its theoretical underpinnings and translating its pseudo-code into working software.
- **Scala**: Leveraging the language's strengths while overcoming challenges, such as functional state management and type safety.
- **Algorithm Design and Optimization**: Ensuring efficient memory use and maintaining clarity in the implementation.

---

## **Usage**
1. Clone the repository:
   ```bash
   git clone https://github.com/JakubSchwenkbeck/AhoCorsick-Scala.git
   cd StringPatternMatching
  
2. Run (and compile) the project:
  ```bash
  sbt run 
  ```

---

## **Open Issues**
1. **GUI Integration**:
   - **Objective**: Develop an intuitive graphical interface to:
     - Visualize the trie structure dynamically as patterns are added.
     - Provide a step-by-step visualization of how patterns are matched in a given text.
   - **Learning Goal**: Explore Scala GUI frameworks, in particular Scala.JS for a clean webbased visualization.

2. **Concurrency**:
   - **Objective**: Optimize the algorithm for large-scale text inputs by leveraging Scala's concurrency capabilities.
     - Use features like **Futures**, **parallel collections**, or **Akka** for multi-threaded processing.
     - Ensure thread safety and minimize contention during state transitions.
   - **Learning Goal**: Understand and apply Scala's concurrency model effectively to enhance performance.

---

## **References**
- Alfred V. Aho and Margaret J. Corasick, "Efficient String Matching: An Aid to Bibliographic Search," _Communications of the ACM_, 1975. [Read the Paper (PDF)](https://cr.yp.to/bib/1975/aho.pdf).

---

## **License**
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
