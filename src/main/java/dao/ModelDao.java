package dao;

import appUtils.db.DBManager;
import entity.Model;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ModelDao implements Dao<Model> {

    private static final Logger logger = LogManager.getLogger(ModelDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<Model> get(long id) {
        Optional<Model> optionalModel = Optional.empty();
        String getModelQuery = "select  t2.MANUF_NAME, t1.* from MODELS as t1 join MANUFACTURERS as T2 on t1.MANUF_ID = t2.ID where t1.ID = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getModelQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Model model = new Model();
            while(resultSet.next()) {
                model.setModelId(resultSet.getInt("ID"));
                model.setModelName(resultSet.getString("MODEL_NAME"));
                model.setNumberOfPassengers(resultSet.getInt("PASSENG_NUM"));
                optionalModel = Optional.of(model);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalModel;
    }

    @Override
    public List<Model> getAll() {
        List<Model> modelList = new ArrayList<>();
        String getAllModelsQuery = "select  * from MODELS";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllModelsQuery);
            while(resultSet.next()) {
                Model model = new Model();
                model.setModelId(resultSet.getInt("ID"));
                model.setModelName(resultSet.getString("MODEL_NAME"));
                model.setNumberOfPassengers(resultSet.getInt("PASSENG_NUM"));
                modelList.add(model);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return modelList;
    }

    @Override
    public long create(Model model) {
        long modelId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createModelQuery = "insert into MODELS values (?,?,?,?)";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createModelQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, model.getModelName());
            preparedStatement.setInt(3, model.getNumberOfPassengers());
            preparedStatement.setInt(4, model.getManufacturer().getManufacturerId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                modelId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return modelId;
    }

    @Override
    public void update(Model model, String[] params) {
        CONNECTION_LOCK.lock();
        String updateModelQuery = "update MODELS set MODEL_NAME = ?, PASSENG_NUM = ?, MANUF_ID = ? where ID = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateModelQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setString(3, params[2]);
            preparedStatement.setInt(4, model.getModelId());
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
        String deleteModelQuery = "delete from MODELS where ID = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteModelQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

}
