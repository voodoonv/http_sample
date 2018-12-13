
val dependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.play" %% "play-json" % "2.6.10",
  "net.liftweb" %% "lift-json" % "3.3.0"
)

lazy val root = (project in file(".")).settings(
  name := "http_sample",
  version := "1.0",
  scalaVersion := "2.12.4",
  libraryDependencies ++= dependencies
)