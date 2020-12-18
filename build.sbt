name := "akkademy-db-scala"

organization := "com.github.sherlock.akkademy-db"

version := "1.2-SNAPSHOT"

javacOptions ++= Seq("-source","1.8","-target","1.8")

scalaVersion := "2.12.11"

libraryDependencies ++= Seq (
	"junit" % "junit" % "4.12" % "test",
	"org.scalatest" %% "scalatest" % "3.1.1" % "test",
	"org.projectlombok" % "lombok" % "1.16.20" % "provided",
	"com.typesafe.akka" %% "akka-actor" % "2.5.31",
	"com.typesafe.akka" %% "akka-actor-typed" % "2.5.31",
	"com.typesafe.akka" %% "akka-remote" % "2.5.31",
	"com.typesafe.akka" %% "akka-testkit" % "2.5.31" % "test"
)

mainClass in Compile := Some("com.akkademy.Main")

// assembly 打包时跳过测试 (默认会对所有测试类进行测试)
test in assembly := {}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true)