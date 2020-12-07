import org.junit.Test

class FooTest {

  
  @Test
  def foo(): Unit = {
    val x = new DayX("src/main/resources/testinput.txt")
//    x.solve()
    x.parseLine("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.")
    assert(new Vertex("blue") == new Vertex("blue"))
  }
}
