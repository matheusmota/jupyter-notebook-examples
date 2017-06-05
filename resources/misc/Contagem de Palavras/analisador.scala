import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object Analisador {

  // Args = path/to/text0.txt path/to/text1.txt
  def main(args: Array[String]) {

    // create Spark context with Spark configuration
    val sc = new SparkContext(new SparkConf().setAppName("Contagem de Palavra"))

    println("TEXT1")

    // read first text file and split into lines
    val lines1 = sc.textFile(args(0))

    // contar palavras do texto 1 e imprimir as 5 palavras com as maiores ocorrencias (ordem DECRESCENTE)

    val words = lines1.flatMap(line => line.split(" "))

    val data = words.map(word => (word.replaceAll("[,.!?:;]",""), 1))
            .reduceByKey(_ + _)
            .filter(_._1.length > 3) // apenas palavras com mais de tres caracteres
            .map(_.swap) // esse trecho é necessário pois não há função sortByValue()
            .sortByKey(false,1)
            .map(_.swap)

    val output = data.take(5) // apenas as 5 mais frequentes

    output.foreach{ x => println(x._1 + "=" + x._2.toString)} // imprimir em cada linha: "palavra=numero"

    println("TEXT2")

    // read second text file and split each document into words
    val lines2 = sc.textFile(args(1))

    // transformações do texto 2 análogas ao texto 1

    val words2 = lines2.flatMap(line => line.split(" "))

    val data2 = words2.map(word => (word.replaceAll("[,.!?:;]",""),1))
            .reduceByKey(_ + _)
            .filter(_._1.length > 3)
            .map(_.swap)
            .sortByKey(false,1)
            .map(_.swap)

    val output2 = data2.take(5)

    output2.foreach{ x => println(x._1 + "=" + x._2.toString)}

    // comparar resultado e imprimir na ordem ALFABETICA todas as palavras que aparecem MAIS que 100 vezes nos 2 textos

    println("COMMON")

    val filt1 = data.filter(_._2 > 100).sortByKey().keys // palavras do texto 1

    val filt2 = data2.filter(_._2 > 100).sortByKey().keys // palavras do texto 2

    val data3 =  filt1.intersection(filt2) // intersecção

    val output3 = data3.map(word => (word,1)).sortByKey() // rdd expandido pois não consegui ordenar um rdd com array de apenas uma string

    output3.foreach{ x => println(x._1)} // imprimir em cada linha: "palavra"

  }

}
