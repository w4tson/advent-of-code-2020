import scala.io.Source

class Day7(input: String) {
  
  def solve(): Unit ={
    val graph = new Graph()
    val lines = Source.fromFile(input).getLines.toList

    lines.foreach( line => parseLine(line, graph))
    val shinyGold = graph.getVertex("shiny gold")
    graph.part01(shinyGold)
    graph.part02(shinyGold)
  }

  def parseLine(line: String, graph: Graph ) = {
    val pattern = "(\\w+ \\w+) bags contain (no other bags|.*).$".r
    for (m <- pattern.findAllMatchIn(line)) {
      if (!m.group(2).startsWith("no other")) {
        parseBagInfo(m.group(2)).foreach { n =>
          graph.addEdge(m.group(1), n._2, n._1)
        }
      }
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

  def part01(vertex: Vertex) = {
    val visited = Map(vertex -> true)
    val paths = dfs(getVertex(vertex.color), visited, List())
    println(paths.flatMap{ v => v.collect{ (x: Vertex) => x.color} }.distinct.size-1)
  }

  def part02(vertex: Vertex) = {
    val count = bfs(getVertex(vertex.color), 1) -1
    println(count)
  }

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

  def getAllChildren(color: String) : List[Edge] = {
    this.edges.filter{e => e.from.color == color }
  }

  def dfs(current: Vertex, visited: Map[Vertex, Boolean], paths: List[List[Vertex]]):  List[List[Vertex]] = {
    val visited_updated = visited + (current -> true)
    val parents = getAllParents(current.color)
    var result = paths

    parents.foreach{ parent =>
      val has_visited: Boolean = visited_updated.getOrElse(parent, false)
      if (!has_visited) {
        result = result ++ dfs(parent, visited_updated, paths)
      }
    }

    parents.size match {
      case 0 => visited_updated.keys.toList::result
      case _ => result
    }
  }

  def bfs(current: Vertex, multiplier: Int):  Int = {
    val children = getAllChildren(current.color)
    children.size match {
      case 0 => multiplier
      case _ => multiplier + children.map(child => {
        multiplier * bfs(child.to, child.count)
      }).sum
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
