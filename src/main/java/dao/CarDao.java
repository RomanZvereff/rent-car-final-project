package dao;

import appUtils.db.DBManager;
import entity.*;
import entity.enums.BodyType;
import entity.enums.CarLevel;
import entity.enums.FuelType;
import entity.enums.TransmissionType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarDao implements Dao<Car> {

    private static final Logger logger = LogManager.getLogger(CarDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();;

    @Override
    public Optional<Car> get(long id) {
        Optional<Car> optionalCar = Optional.empty();
        ResultSet resultSet;
        String getCarQuery = "select  t1.ID,\n" +
                                    "t1.MANUF_ID,\n" +
                                    "t2.MANUF_NAME,\n" +
                                    "t1.MODEL_ID,\n" +
                                    "t3.MODEL_NAME,\n" +
                                    "t3.PASSENG_NUM,\n" +
                                    "t1.CAR_LVL_ID,\n" +
                                    "t1.BODY_TYPE_ID,\n" +
                                    "t1.TRANSM_TYPE_ID,\n" +
                                    "t1.FUEL_TYPE_ID,\n" +
                                    "t1.`ENGINE`,\n" +
                                    "t1.FUEL_CONSUM,\n" +
                                    "t1.PROD_YEAR,\n" +
                                    "t1.BRANCH_ID,\n" +
                                    "t4.BRANCH_NAME,\n" +
                                    "t4.CITY_NAME,\n" +
                                    "t4.ADDRESS,\n" +
                                    "t4.MANAGER_ID,\n" +
                                    "t1.PRICE,\n" +
                                    "t1.IMG_NAME\n" +
                                    "from CARS as t1\n" +
                                    "join MANUFACTURERS as t2 on t1.MANUF_ID = t2.ID\n" +
                                    "join MODELS as t3 on t1.MODEL_ID = t3.ID\n" +
                                    "join BRANCHES as t4 on t1.BRANCH_ID = t4.ID\n" +
                                    "where t1.ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getCarQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Model model = new Model();
                model.setModelId(resultSet.getInt("MODEL_ID"));
                model.setModelName(resultSet.getString("MODEL_NAME"));
                model.setNumberOfPassengers(resultSet.getInt("PASSENG_NUM"));

                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerId(resultSet.getInt("MANUF_ID"));
                manufacturer.setManufacturerName(resultSet.getString("MANUF_NAME"));
                manufacturer.setModel(model);

                Branch branch = new Branch();
                branch.setBranchId(resultSet.getInt("BRANCH_ID"));
                branch.setBranchName(resultSet.getString("BRANCH_NAME"));
                branch.setCityName(resultSet.getString("CITY_NAME"));
                branch.setAddress(resultSet.getString("ADDRESS"));

                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(resultSet.getLong("MANAGER_ID"));
                if(optionalProfile.isPresent()) {
                    Profile profile = optionalProfile.get();
                    branch.setProfile(profile);
                }

                Car car = new Car();
                car.setCarId(resultSet.getInt("ID"));
                car.setManufacturer(manufacturer);
                car.setModel(model);
                car.setCarLevel(CarLevel.findByKey(resultSet.getInt("CAR_LVL_ID")).toString());
                car.setCarLvlId(resultSet.getInt("CAR_LVL_ID"));
                car.setBodyType(BodyType.findByKey(resultSet.getInt("BODY_TYPE_ID")).toString());
                car.setTransmissionType(TransmissionType.findByKey(resultSet.getInt("TRANSM_TYPE_ID")).toString());
                car.setFuelType(FuelType.findByKey(resultSet.getInt("FUEL_TYPE_ID")).toString());
                car.setEngine(resultSet.getFloat("ENGINE"));
                car.setFuelConsumption(resultSet.getFloat("FUEL_CONSUM"));
                car.setProductionYear(resultSet.getInt("PROD_YEAR"));
                car.setBranch(branch);
                car.setPrice(resultSet.getFloat("PRICE"));
                car.setImageName(resultSet.getString("IMG_NAME"));
                optionalCar = Optional.of(car);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalCar;
    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();
        String getAllCarsQuery = "select  t1.ID,\n" +
                                        "t1.MANUF_ID,\n" +
                                        "t2.MANUF_NAME,\n" +
                                        "t1.MODEL_ID,\n" +
                                        "t3.MODEL_NAME,\n" +
                                        "t3.PASSENG_NUM,\n" +
                                        "t1.CAR_LVL_ID,\n" +
                                        "t1.BODY_TYPE_ID,\n" +
                                        "t1.TRANSM_TYPE_ID,\n" +
                                        "t1.FUEL_TYPE_ID,\n" +
                                        "t1.`ENGINE`,\n" +
                                        "t1.FUEL_CONSUM,\n" +
                                        "t1.PROD_YEAR,\n" +
                                        "t1.BRANCH_ID,\n" +
                                        "t4.BRANCH_NAME,\n" +
                                        "t4.CITY_NAME,\n" +
                                        "t4.ADDRESS,\n" +
                                        "t4.MANAGER_ID,\n" +
                                        "t1.PRICE,\n" +
                                        "t1.IMG_NAME\n" +
                                        "from CARS as t1\n" +
                                        "join MANUFACTURERS as t2 on t1.MANUF_ID = t2.ID\n" +
                                        "join MODELS as t3 on t1.MODEL_ID = t3.ID\n" +
                                        "join BRANCHES as t4 on t1.BRANCH_ID = t4.ID;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllCarsQuery);
            while(resultSet.next()) {
                Model model = new Model();
                model.setModelId(resultSet.getInt("MODEL_ID"));
                model.setModelName(resultSet.getString("MODEL_NAME"));
                model.setNumberOfPassengers(resultSet.getInt("PASSENG_NUM"));

                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerId(resultSet.getInt("MANUF_ID"));
                manufacturer.setManufacturerName(resultSet.getString("MANUF_NAME"));
                manufacturer.setModel(model);


                Branch branch = new Branch();
                branch.setBranchId(resultSet.getInt("BRANCH_ID"));
                branch.setBranchName(resultSet.getString("BRANCH_NAME"));
                branch.setCityName(resultSet.getString("CITY_NAME"));
                branch.setAddress(resultSet.getString("ADDRESS"));

                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(resultSet.getLong("MANAGER_ID"));
                if(optionalProfile.isPresent()) {
                    Profile profile = optionalProfile.get();
                    branch.setProfile(profile);
                }

                Car car = new Car();
                car.setCarId(resultSet.getInt("ID"));
                car.setManufacturer(manufacturer);
                car.setModel(model);
                car.setCarLevel(CarLevel.findByKey(resultSet.getInt("CAR_LVL_ID")).toString());
                car.setCarLvlId(resultSet.getInt("CAR_LVL_ID"));
                car.setBodyType(BodyType.findByKey(resultSet.getInt("BODY_TYPE_ID")).toString());
                car.setTransmissionType(TransmissionType.findByKey(resultSet.getInt("TRANSM_TYPE_ID")).toString());
                car.setFuelType(FuelType.findByKey(resultSet.getInt("FUEL_TYPE_ID")).toString());
                car.setEngine(resultSet.getFloat("ENGINE"));
                car.setFuelConsumption(resultSet.getFloat("FUEL_CONSUM"));
                car.setProductionYear(resultSet.getInt("PROD_YEAR"));
                car.setBranch(branch);
                car.setPrice(resultSet.getFloat("PRICE"));
                car.setImageName(resultSet.getString("IMG_NAME"));
                carList.add(car);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return carList;
    }

    @Override
    public long create(Car car) {
        long carId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createCarQuery = "insert into CARS values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createCarQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, car.getManufacturer().getManufacturerId());
            preparedStatement.setInt(3, car.getModel().getModelId());
            preparedStatement.setInt(4, CarLevel.valueOf(car.getCarLevel()).getLevel());
            preparedStatement.setInt(5, BodyType.valueOf(car.getBodyType()).getBodyTypeId());
            preparedStatement.setInt(6, TransmissionType.valueOf(car.getTransmissionType()).getTransmissionTypeId());
            preparedStatement.setInt(7, FuelType.valueOf(car.getFuelType()).getFuelTypeId());
            preparedStatement.setFloat(8, car.getEngine());
            preparedStatement.setFloat(9, car.getFuelConsumption());
            preparedStatement.setInt(10, car.getProductionYear());
            preparedStatement.setInt(11, car.getBranch().getBranchId());
            preparedStatement.setFloat(12, car.getPrice());
            preparedStatement.setString(13, car.getImageName());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                carId = generatedKeys.getInt(1);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return carId;
    }

    @Override
    public void update(Car car, String[] params) {
        CONNECTION_LOCK.lock();
        String updateCarQuery = "update CARS set CAR_LVL_ID=?, ENGINE=?, FUEL_CONSUM=?, PROD_YEAR=?, PRICE=? where ID=?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateCarQuery)) {
            preparedStatement.setInt(1, CarLevel.valueOf(params[0]).getLevel());
            preparedStatement.setFloat(2, Float.parseFloat(params[1]));
            preparedStatement.setFloat(3, Float.parseFloat(params[2]));
            preparedStatement.setInt(4, Integer.parseInt(params[3]));
            preparedStatement.setFloat(5, Float.parseFloat(params[4]));
            preparedStatement.setInt(6, car.getCarId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void delete(long id) {
        CONNECTION_LOCK.lock();
        String deleteCarQuery = "delete from CARS where ID = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCarQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

}
