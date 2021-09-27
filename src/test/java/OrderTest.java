import entity.Order;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class OrderTest {

    @Test
    public void shouldReturnSaltString() {
        String saltString = Order.getSaltString();
        assertNotNull(saltString);
    }

}
