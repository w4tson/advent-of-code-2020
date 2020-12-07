import org.junit.Test

class Day7Test {

  @Test
  def testParseLine(): Unit = {
    val x = new Day7("src/main/resources/input.txt")
    val graph = new Graph()
    x.parseLine("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.", graph)
    println(graph)
  }

  @Test
  def testSearch() = {
    new Day7("src/main/resources/input.txt").solve()
  }


}
