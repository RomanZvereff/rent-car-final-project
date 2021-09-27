import entity.enums.CarLevel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarLevelTest {

    @Test
    public void shouldReturnCorrectCarLevelByKey() {
        String expected = "ECONOM";
        String actual = CarLevel.findByKey(1).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnCarLevel() {
        int expected = 1;
        int actual = CarLevel.ECONOM.getLevel();
        assertEquals(expected, actual);
    }

}
