# spark-cassandra-cnx-faildemo
I created this to demonstrate an unrecoverable exception that occurs in the spark-cassandra connector when saving high-volume datasets to tables

# Preparation
in the cql folder there is a file called schema.cql, makes ure to execute it on your cassandra cluster to set the schema for the application
Run these statements on your cassandra cluster to prepare it to accept your data

Update src/main/scala/FakeDataStreamer.scala
make sure that line 20 shows the ip address for the head node on your cassandra cluster

````
config.set("spark.cassandra.connection.host", yourClusterHost)
````

when the host is properly set, run ``` sbt assembly ``` on the repo's root directory

#Execution
run ```./launch.sh```

this will generate a bunch of random data and attempt to save it to the tables that you created on your cluster

note the stream of unrecoverable exceptions

#Results
It seems like the connection to cassandra keeps dying.  I've confirmed this a bunch of different ways using spark in local mode, and there seems to be some other problems running it on standalone

As a result of the thrown exceptions, only one of the tables receives any writes.

the errlog.log file included is a capture of the stderr channel while the process was run on my macbook pro.

