import entity.enums.FuelType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FuelTypeTest {

    @Test
    public void shouldReturnCorrectFuelTypeByKey() {
        String expected = "PETROL";
        String actual = FuelType.findByKey(1).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnFuelType() {
        int expected = 3;
        int actual = FuelType.ELECTRICAL.getFuelTypeId();
        assertEquals(expected, actual);
    }

}
