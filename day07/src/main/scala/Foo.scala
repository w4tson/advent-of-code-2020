import scala.io.Source
import scala.util.matching.Regex

class DayX(input: String) {
  
  def solve(): Unit ={
//    val fileContents = Source.fromFile(input).getLines.mkString
    val lines = Source.fromFile(input).getLines.toList

    lines.foreach(parseLine)
  }


//  muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
  
  def parseLine(line: String ) = {
    val pattern = "(\\w+ \\w+) bags contain (no other bags|.*).$".r
    for (m <- pattern.findAllMatchIn(line)) {
      if (!m.group(2).startsWith("no other")) {
        println(parseBagInfo(m.group(2)))
      }
      println(s"${m.group(1)} ${m.group(2)}")
    }
  }
  
  def parseBagInfo(bagsInfo: String): List[(Int, String)] = {
    val pattern = "(\\d+) (\\w+ \\w+) bags?(?:, )?".r
    bagsInfo.split(", ").map(bi => {
      pattern.findFirstMatchIn(bi).map { m =>
        (m.group(1).toInt, m.group(2))
      }.orNull
    }).toList
    
  }
}

class Vertex(color: String) {
  override def toString = s"Vertex($color)"
}

class Edge(from: Vertex, to: Vertex, count: Int){
  override def toString = s"Edge($from, $to, $count)"
}
