name := "akkademy-db-scala"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq (
	"junit" % "junit" % "4.12" % "test",
	"org.scalatest" %% "scalatest" % "3.1.1" % "test",
	"org.projectlombok" % "lombok" % "1.16.20" % "provided",
	"com.typesafe.akka" %% "akka-actor" % "2.5.31",
	"com.typesafe.akka" %% "akka-testkit" % "2.5.31" % "test"
)