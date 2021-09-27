package dao;

import appUtils.db.DBManager;
import entity.Manufacturer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ManufacturerDao implements Dao<Manufacturer> {

    private static final Logger logger = LogManager.getLogger(ManufacturerDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<Manufacturer> get(long id) {
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        ResultSet resultSet;
        String getManufacturerQuery = "select  * from MANUFACTURERS where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getManufacturerQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            while(resultSet.next()) {
                manufacturer.setManufacturerId(resultSet.getInt("ID"));
                manufacturer.setManufacturerName(resultSet.getString("MANUF_NAME"));
                optionalManufacturer = Optional.of(manufacturer);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalManufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllManufacturersQuery = "select  * from MANUFACTURERS;";
        try(Connection connection = dbManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllManufacturersQuery);
            while(resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerId(resultSet.getInt("ID"));
                manufacturer.setManufacturerName(resultSet.getString("MANUF_NAME"));
                manufacturerList.add(manufacturer);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return manufacturerList;
    }

    @Override
    public long create(Manufacturer manufacturer) {
        long manufacturerId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createManufacturerQuery = "insert into MANUFACTURERS values (?,?)";
        try(Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(createManufacturerQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, manufacturer.getManufacturerName());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                manufacturerId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return manufacturerId;
    }

    @Override
    public void update(Manufacturer manufacturer, String[] params) {
        CONNECTION_LOCK.lock();
        String updateManufacturerQuery = "update MANUFACTURERS set MANUF_NAME = ? where ID = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateManufacturerQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setInt(2, manufacturer.getManufacturerId());
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
        String deleteManufacturerQuery = "delete from MANUFACTURERS where ID = ?";
        try(Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteManufacturerQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }
}
