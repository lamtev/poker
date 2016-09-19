import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloPokerTest {

    @Test
    public void testHelloPoker() {
        HelloPoker helloPoker = new HelloPoker();
        assertEquals("Hello Poker", helloPoker.helloPoker());
    }

}
