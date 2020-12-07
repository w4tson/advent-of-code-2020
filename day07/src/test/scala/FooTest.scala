import org.junit.Test

class FooTest {

  
  @Test
  def foo(): Unit = {
    val x = new DayX("src/main/resources/testinput.txt")
//    x.solve()
    val graph = new Graph()
    x.parseLine("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.", graph)
    println(graph)
  }

  @Test
  def search() = {
    val x = new DayX("src/main/resources/testinput.txt")
        x.solve()
  }


}
