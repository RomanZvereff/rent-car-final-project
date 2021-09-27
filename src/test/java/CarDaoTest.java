import dao.CarDao;
import entity.Car;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CarDaoTest {

    @Test
    public void shouldReturnCorrectCar() {
        CarDao carDao = new CarDao();
        Optional<Car> optionalCar = carDao.get(1);
        assertEquals("SKODA", optionalCar.get().getManufacturer().getManufacturerName());
        assertNotNull(optionalCar.get());
    }

    @Test
    public void shouldReturnAllCars() {
        CarDao carDao = new CarDao();
        List<Car> carList = carDao.getAll();
        assertFalse(carList.isEmpty());
    }

}
