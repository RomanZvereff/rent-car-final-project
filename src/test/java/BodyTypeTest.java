import entity.enums.BodyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BodyTypeTest {

    @Test
    public void shouldReturnCorrectBodyTypeByKey() {
        String expected = "SEDAN";
        String actual = BodyType.findByKey(1).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnBodyType() {
        int expected = 2;
        int actual = BodyType.HATCHBACK.bodyTypeId;
        assertEquals(expected, actual);
    }

}
