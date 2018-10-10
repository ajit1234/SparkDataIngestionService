
package DataIngestionPackage
//import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.Encoder
// $example off:schema_inferring$
import org.apache.spark.sql.Row

import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.types._
import org.neo4j.spark._
import org.apache.spark.sql._
import org.graphframes._

object POCForNeo4j {
  case class Person(name: String, age: Long)
  def main(args: Array[String]): Unit = {
     // val conf = new SparkConf().setMaster("local").setAppName("POC for neo4j").
       // set("spark.neo4j.bolt.password","hadoop")

      //val sc= new SparkContext(conf)
      val spark = SparkSession
        .builder()
        .appName("POC for neo4j")
        .config("spark.master","local")
        .config("spark.neo4j.bolt.url","bolt://localhost:7687")
        .config("spark.neo4j.bolt.user","neo4j")
        .config("spark.neo4j.bolt.password","hadoop")
        .getOrCreate()


    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._
   // val neojContext = new Neo4j(spark.sparkContext)
    //simulating dataframe to be exported to neo4j-consider agillion csv is converted to dataframe

    //val df = spark.read.json("C:\\spark-2.1.3-bin-hadoop2.7\\examples\\src\\main\\resources\\people.json")
    val peopleDF = spark.sparkContext
      .textFile("C:\\spark-2.1.3-bin-hadoop2.7\\examples\\src\\main\\resources\\people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()
    // Displays the content of the DataFrame to stdout
    peopleDF.show(3)
    //val deptList = Seq(department1,department2,department3,department4)

    val neo = new Neo4j(spark.sparkContext)

    //val rowRDD = neo.cypher("MATCH (n:Person) RETURN n.name as name limit 10").loadRowRdd
    //rowRDD.map(t => "Name: " + t(0)).collect.foreach(println)
    //save to neo4j from spark
    Neo4jDataFrame.mergeEdgeList(spark.sparkContext,peopleDF,("name",Seq("name")), ("BELONG_TO",Seq.empty),("age",Seq("age")))

    //read from neo4j to spark
    val queryToReadFromNeo4j = "match(n) where n.age>30 return(n)"
    val readDF = neo.cypher(queryToReadFromNeo4j).loadDataFrame()
    readDF.show()
  }

}
