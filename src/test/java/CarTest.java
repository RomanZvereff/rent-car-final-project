import entity.Car;
import entity.enums.CarLevel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    @Test
    public void shouldCompareCars() {
        Car car = new Car();
        car.setCarLevel(CarLevel.LUX.toString());

        Car car2 = new Car();
        car2.setCarLevel(CarLevel.LUX.toString());

        assertEquals(0, car.compareTo(car2));
    }

}
