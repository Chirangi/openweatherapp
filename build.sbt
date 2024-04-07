ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

val http4sVersion = "0.23.26"
val http4sBlazeVersion = "0.23.13"
val circeVersion = "0.14.6"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sBlazeVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sBlazeVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalatest" %% "scalatest" % "3.2.17" % Test
)


lazy val root = (project in file("."))
  .settings(
    name := "weatherapp"
  )
