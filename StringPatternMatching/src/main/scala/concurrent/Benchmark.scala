package concurrent

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable
import scala.util.Random
import concurrent._
import Main._

object Benchmark {

  // Function to benchmark the normal FSM
  def benchmarkNormal(fsm: FiniteStateMachine): Long = {
    val start = System.currentTimeMillis()
    fsm.PMM()  // Perform pattern matching
    val end = System.currentTimeMillis()
    end - start
  }

  // Function to benchmark the concurrent FSM
  def benchmarkConcurrent(fsm: ParallelPatternMatchingMachine): Long = {
    val start = System.currentTimeMillis()
    fsm.PMM()  // Perform concurrent pattern matching
    val end = System.currentTimeMillis()
    end - start
  }

  // Function to compare the benchmarks and pretty print the results
  def compareBenchmarks(): Unit = {
    // List of test cases: (text file, keyword file)
    val testCases = List(
      ("normalText.txt", "normalKeywords.txt"),
      ("LongText.txt", "LongKeywords.txt"),
      ("ExtremeLongText.txt", "ExtremeLongKeywords.txt")
    )

    // Run the benchmark for each test case
    testCases.foreach { case (textFile, keywordFile) =>
      println(s"\nRunning benchmark for: $textFile with keywords from $keywordFile")

      val keywords = loadKeywords(s"src/main/scala/concurrent/TestFiles/$keywordFile")
      val text = loadText(s"src/main/scala/concurrent/TestFiles/$textFile")

      // Instantiate the normal FSM and concurrent FSM
      val normalFSM = new FiniteStateMachine(text, keywords)
      val concurrentFSM = new ParallelPatternMatchingMachine(text, keywords)

      // Benchmark the normal FSM
      val normalTime = benchmarkNormal(normalFSM)
      println(s"Normal FSM took: $normalTime ms")

      // Benchmark the concurrent FSM
      val concurrentTime = benchmarkConcurrent(concurrentFSM)
      println(s"Concurrent FSM took: $concurrentTime ms")

      // Compare and pretty print the results
      if (normalTime < concurrentTime) {
        println("Normal FSM is faster!")
      } else if (normalTime > concurrentTime) {
        println("Concurrent FSM is faster!")
      } else {
        println("Both FSMs took the same time!")
      }

      // Save the results to a file
     /* val writer = new BufferedWriter(new FileWriter(new File(s"benchmark_results_${textFile}_vs_${keywordFile}.txt")))
      writer.write(s"Normal FSM: $normalTime ms\n")
      writer.write(s"Concurrent FSM: $concurrentTime ms\n")
      writer.close()*/
    }
  }

  // Function to load keywords from a file
  def loadKeywords(filename: String): List[String] = {
    val source = scala.io.Source.fromFile(filename)
    val keywords = source.mkString.split(",").map(_.trim).toList
    source.close()
    keywords
  }

  // Function to load the text from a file
  def loadText(filename: String): String = {
    val source = scala.io.Source.fromFile(filename)
    val text = source.mkString
    source.close()
    text
  }

  def main(args: Array[String]): Unit = {
    // Run the comparison for all test cases
    compareBenchmarks()
  }
}
