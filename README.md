# **String Pattern Matching with Aho-Corasick Algorithm**

## **Overview**
This project implements the Aho-Corasick algorithm for **efficient multi-pattern string matching**. The Aho-Corasick algorithm is widely used in applications such as text searching, bioinformatics, and network security, as it allows matching multiple patterns simultaneously in linear time. This project demonstrates its practical use by providing both an **educational** and **performance-focused** approach to the algorithm.

The implementation is written in **Scala**, chosen for its elegant combination of **functional programming** and **object-oriented paradigms**. This project serves not only as an exercise in algorithmic problem-solving but also as a demonstration of how to apply Scala’s strengths to real-world problems.

## **Project Structure**
The project is divided into two main parts:

### 1. **Educational Part (GUI-based Visualization)**
   - A **graphical user interface (GUI)** built with **Processing** to visualize the Aho-Corasick algorithm, including the trie structure, state transitions, and failure links.
   - This visualization helps users understand how the algorithm works step-by-step by presenting a dynamic representation of the pattern matching process.

### 2. **Concurrent Version (Performance and Benchmarking)**
   - A **concurrent version** of the Aho-Corasick algorithm that leverages Scala’s powerful concurrency features  for efficient parallel processing.
   - This implementation is optimized for large-scale datasets, allowing for benchmarking and comparison of performance across different input sizes.
   - Ideal for real-world applications requiring fast and scalable pattern matching, such as intrusion detection or text filtering.

## **Features**
- **Core Aho-Corasick Algorithm**:
  - Construction of the trie (state machine) for multiple pattern matching.
  - Efficient transition and failure functions for string matching.
  - Handles multiple patterns and outputs results simultaneously.
  
- **GUI Visualization** (Educational):
  - Visualizes the trie structure dynamically as patterns are processed.
  - Shows state transitions and failure links in real-time.
  - Step-by-step mode to follow the algorithm’s progress.

- **Concurrent Implementation** (Performance):
  - Efficient multi-pattern matching with Scala’s concurrency tools.
  - Optimized for large datasets, perfect for high-performance applications.
  - Benchmarkable to test the speed and efficiency of the algorithm.

- **Planned Enhancements**:
  - **Jupyter Notebook Integration**: Interactive environment to explore the Aho-Corasick algorithm and Scala code step-by-step.

## **Why Aho-Corasick?**
The Aho-Corasick algorithm is essential for efficiently searching multiple patterns within large datasets. It’s used in various domains:
- **Text Searching**: For rapid pattern matching in search engines or text editors.
- **Bioinformatics**: For matching DNA or protein sequences.
- **Network Security**: In intrusion detection systems for real-time pattern matching.

Its **O(n + m + z)** time complexity (where `n` is the length of the text, `m` is the total length of the patterns, and `z` is the number of matches) makes it ideal for these applications.

## **Why Scala?**
Scala was chosen for its unique combination of **functional programming** and **object-oriented features**, which makes it ideal for implementing complex algorithms:
- **Immutability and Pattern Matching**: Helps represent the trie and transitions cleanly and efficiently.
- **Concise and Expressive Code**: Scala's syntax allows for the elegant representation of the Aho-Corasick algorithm.
- **Concurrency**: Scala's powerful concurrency tools, such as **Akka** and **Futures**, are leveraged in the concurrent version to handle large datasets efficiently.


## **Usage**
### 1. Clone the Repository
```bash
git clone https://github.com/JakubSchwenkbeck/AhoCorsick-Scala.git
cd StringPatternMatching
```
### 2. Run the Project via [sbt](https://www.scala-sbt.org/)
```bash
sbt run
```
---
### This GIF displays the trie structure and step-by-step pattern matching, illustrating how the algorithm processes the input string and identifies matches.

![Gif](https://github.com/user-attachments/assets/5111f7f9-b473-44a9-a888-e7d65173e78a)

---

## **References**
- Alfred V. Aho and Margaret J. Corasick, "Efficient String Matching: An Aid to Bibliographic Search," _Communications of the ACM_, 1975. [Read the Paper (PDF)](https://cr.yp.to/bib/1975/aho.pdf).

---

## **License**
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
