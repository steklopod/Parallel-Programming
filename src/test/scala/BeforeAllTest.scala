import org.scalatest.{BeforeAndAfterAll, FunSpec}

class BeforeAllTest extends FunSpec with BeforeAndAfterAll {

  override def beforeAll: Unit = { println("1. beforeAll") }

  describe("running a test") {
    println("0. in describe")

    it("should do something") {
      println("2. in my test")
    }
  }

}