ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"

lazy val root = (project in file("."))
  .settings(
    name := "lab1",
    idePackagePrefix := Some("org.example")
  )

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.11"