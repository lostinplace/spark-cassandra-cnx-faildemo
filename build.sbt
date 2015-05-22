
name := "cassandra-connector-bug-demo"

version := "1.1"

scalaVersion := "2.10.5"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/ typesafe/releases",
  "spray repo" at "http://repo.spray.io"
)

resolvers += "spray" at "http://repo.spray.io/"

val sparkVersion = "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"

libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.1"

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

parallelExecution in assembly := false

assemblyOption in assembly ~= { _.copy(includeScala = false) }

assemblyOutputPath in assembly := new File("output/demo.jar")

assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) {
    (old) => {
      case PathList("com", "google", xs @ _*) => MergeStrategy.last
      case x => old(x)
    }
  }

