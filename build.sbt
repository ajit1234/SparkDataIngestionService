name := "DataIngestionService"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.neo4j.driver" % "neo4j-java-driver" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies +="org.apache.spark" %% "spark-core" % "2.1.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.1.3"
resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
libraryDependencies += "neo4j-contrib" % "neo4j-spark-connector" % "2.1.0-M4"