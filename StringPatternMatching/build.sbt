ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin) // Enable the Scala.js plugin in this project

  .settings(
    name := "StringPatternMatching",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0",

  )
