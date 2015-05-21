
name := "cassandra-connector-success-demo"

version := "1.1"

scalaVersion := "2.10.5"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/ typesafe/releases",
  "spray repo" at "http://repo.spray.io"
)

resolvers += "spray" at "http://repo.spray.io/"

// won't work unless building right from master currently. release coming that supports 1.3.1
//val sparkVersion = "1.3.1"

val sparkVersion = "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"

libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.1"

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

parallelExecution in assembly := false

assemblyOption in assembly ~= { _.copy(includeScala = false) }

mergeStrategy in assembly <<= (mergeStrategy in assembly) {
    (old) => {
      case PathList("com", "google", xs @ _*) => MergeStrategy.last
      case x => old(x)
    }
  }

//assemblyMergeStrategy in assembly := {
//  case PathList("META-INF", xs@_*) => MergeStrategy.discard
//  case x => MergeStrategy.first
//}