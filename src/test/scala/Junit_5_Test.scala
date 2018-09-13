import org.junit.Ignore
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.runner.RunWith
import org.scalatest.junit.{JUnitRunner, JUnitSuite}
import org.scalatest.{BeforeAndAfterAll, Matchers}

@RunWith(classOf[JUnitRunner])
class Junit_5_Test extends JUnitSuite with Matchers with BeforeAndAfterAll{

  object ExceptionTest {
    @throws(classOf[RuntimeException])
    def throwRunEx = throw new RuntimeException
  }

  @Test
  @DisplayName("Example with JUnitSuite")
  def throwsExceptionWhenCalled_With_JUnitSuite() {
    import ExceptionTest._
    assertThrows[RuntimeException]{ throwRunEx}
  }

}
