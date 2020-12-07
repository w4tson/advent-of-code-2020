import scala.io.Source
import scala.util.matching.Regex

class DayX(input: String) {
  
  def solve(): Unit ={
//    val fileContents = Source.fromFile(input).getLines.mkString
    val graph = new Graph()
    val lines = Source.fromFile(input).getLines.toList

    lines.foreach( line => parseLine(line, graph))
    println(graph)
    graph.searchPathsTo("shiny gold")
  }




//  muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
  
  def parseLine(line: String, graph: Graph ) = {
    val pattern = "(\\w+ \\w+) bags contain (no other bags|.*).$".r
    for (m <- pattern.findAllMatchIn(line)) {
//      graph.add(new Vertex(m.group(1)))

      if (!m.group(2).startsWith("no other")) {
        parseBagInfo(m.group(2)).foreach { n =>
          print(n)
          graph.addEdge(m.group(1), n._2, n._1)
        }
      }

      println(s"${m.group(1)} ${m.group(2)}")
    }

  }
  
  def parseBagInfo(bagsInfo: String): List[(Int, String)] = {
    val pattern = "(\\d+) (\\w+ \\w+) bags?(?:, )?".r
    bagsInfo.split(", ").map(bi => {
      pattern.findFirstMatchIn(bi : String).map { m =>
        (m.group(1).toInt, m.group(2))
      }.orNull
    }).toList
    
  }
}

class Graph() {
  var vertices : List[Vertex] = List()
  var edges : List[Edge] = List()

  def addEdge(from: String, to: String, count: Int) = {
    this.edges = new Edge(getVertex(from), getVertex(to), count)::this.edges
  }

  def getVertex(color: String) : Vertex = {
    this.vertices.find{ v: Vertex => v.color == color  } match {
      case Some(vert) => vert
      case None => {
        val vertex = new Vertex(color)
        add(vertex)
        vertex
      }
    }
  }

  def add(vertex: Vertex): Unit = {
    if (!this.vertices.exists(v => v.color == vertex.color)) {
      this.vertices = vertex::this.vertices
    }
  }

  def getAllParents(color: String) : List[Vertex] = {
    this.edges.filter{e => e.to.color == color }
      .collect{ e => e.from }.toList
  }

  def searchPathsTo(color: String) = {
    val visited = Map(color -> true)
    val paths = dfs(color, visited, List())
    println(paths.size)
  }

  def dfs(color: String, visited: Map[String, Boolean], paths : List[List[String]]):  List[List[String]] = {
    val visited_updated = visited + (color -> true)
    val value = getAllParents(color)
    var result = paths

    value.foreach{ c =>
      val has_visited: Boolean = visited_updated.getOrElse(c.color, false)
      if (!has_visited) {
        result = result ++ dfs(c.color, visited_updated, paths)
      }
    }

    value.size match {
      case 0 => visited_updated.keys.toList::result
      case _ => result
    }
  }

  override def toString = s"Graph($vertices, $edges)"
}

class Vertex(val color: String) {
  override def toString = s"Vertex($color)"
}

class Edge(val from: Vertex, val to: Vertex, val count: Int){
  override def toString = s"Edge($from, $to, $count)"
}
