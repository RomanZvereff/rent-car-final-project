import entity.enums.TransmissionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransmissionTypeTest {

    @Test
    public void shouldReturnCorrectTransmissionTypeByKey() {
        String expected = "AUTOMATIC";
        String actual = TransmissionType.findByKey(2).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTransmissionType() {
        int expected = 1;
        int actual = TransmissionType.MANUAL.getTransmissionTypeId();
        assertEquals(expected, actual);
    }

}
