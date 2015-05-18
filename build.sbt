name := "cassandra-connector-failure-demo"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/ typesafe/releases",
  "spray repo" at "http://repo.spray.io"
)

resolvers += "spray" at "http://repo.spray.io/"

val sparkVersion = "1.3.1"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % sparkVersion % "provided"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % sparkVersion % "provided"

libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.2.0"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}