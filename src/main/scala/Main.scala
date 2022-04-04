package org.example

import scala.io.Source
import scala.collection.mutable.{ListBuffer, MutableList}
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import java.io._

case class Film(User: Int, ID: Int, Score: Int, Num: Int)

object FileReader{
  def readFromTextFile(filename: String, sep: String="\t"): ListBuffer[Film] ={
    val table: ListBuffer[Film] = ListBuffer();
    val file = Source.fromFile(filename);
    for(line<-file.getLines()){
      var cur_line = line.split(sep).toList
      table+=Film(cur_line(0).toInt, cur_line(1).toInt,cur_line(2).toInt,cur_line(3).toInt);
    }
    table;
  }
}

object ScoreCounter{
  def getScores(data: ListBuffer[Film], filmID: Int = -1) = {
    if (filmID == -1){
      val grouped_by_score = data.groupBy(_.Score)
      val possible_scores = grouped_by_score.keys.toSeq.sorted
      val score_table: ListBuffer[Int] = ListBuffer();
      for (a <- 0 to possible_scores.size-1) {
        score_table += grouped_by_score(possible_scores(a)).size
      }
      score_table;
    }
    else {
      val grouped_by_film_id = data.groupBy(_.ID)
      val grouped_by_score = grouped_by_film_id(filmID).groupBy(_.Score)
      val possible_scores = grouped_by_score.keys.toSeq.sorted
      val score_table: ListBuffer[Int] = ListBuffer();
      for (a <- 0 to possible_scores.size-1) {
        score_table += grouped_by_score(possible_scores(a)).size
      }
      score_table;
    }
  }
}

object Main extends App {
  val data = FileReader.readFromTextFile("D:/DE_NPL_course/lab1/src/resources/data/u.data")
  val score_table_by_ID = ScoreCounter.getScores(data,257)
  val score_table_all = ScoreCounter.getScores(data)
  val json = (
    ("hist_film" -> score_table_by_ID) ~
      ("hist_all" -> score_table_all)
  )
  val file = new File("lab01.json")
  val bw = new BufferedWriter(new FileWriter(file))
  bw.write(compact(render(json)))
  bw.close()
  println("done")
}
