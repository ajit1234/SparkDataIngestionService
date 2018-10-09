package org.neo4j.spark
import org.neo4j.spark.Neo4j._
import org.apache.spark._
import org.apache.spark.{SparkConf, SparkContext}

object ExportSparkToNeoJ {
  def main(args: Array[String]): Unit = {
    println("sprak to -> " + args(0).toString())
    val UNDEFINED = Long.MaxValue

    val conf = new SparkConf().
      setMaster(args(0)).
      setAppName("Export spark to neoj")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val neo = Neo4j(sc)
    }

}
