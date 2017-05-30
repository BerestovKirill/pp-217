import ru.rsreu.berestov.lab6.PiCalculating;
import org.junit.Assert;
import org.junit.Test;

public class PiCalculatingTest {

  @Test
  public void calc() throws Exception {
    double del = 1e-7;
    for (int i = 0; i < 50; i++) {
      double actual = PiCalculating.calc(0, 100000000L);
      Assert.assertEquals(3.1415926525880504, actual, del);
    }

  }
}