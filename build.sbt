ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "FLanguage"
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.12.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.0"