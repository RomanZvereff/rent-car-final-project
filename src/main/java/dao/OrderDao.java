package dao;

import appUtils.db.DBManager;
import entity.Branch;
import entity.Car;
import entity.Order;
import entity.Profile;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderDao implements Dao<Order> {

    private static final Logger logger = LogManager.getLogger(OrderDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();
    private static final String ORDER_CONFIRM = "Confirmed";
    private static final String ORDER_REJECT = "Rejected";
    private static final String ORDER_CANCEL = "Canceled";

    @Override
    public Optional<Order> get(long id) {
        Calendar rentStart = Calendar.getInstance();
        Calendar rentEnd = Calendar.getInstance();
        Optional<Order> optionalOrder = Optional.empty();
        ResultSet resultSet;
        String getOrderQuery = "select  * from ORDERS where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getOrderQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Order order = new Order();
            while(resultSet.next()) {
                order.setOrderId(resultSet.getLong("ID"));
                order.setOrderNumber(resultSet.getString("ORDER_NUM"));

                long profileId = resultSet.getLong("PROFILE_ID");
                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(profileId);
                optionalProfile.ifPresent(order::setCustomer);

                rentStart.setTime(resultSet.getDate("RENT_START"));
                order.setRentStart(rentStart);
                rentEnd.setTime(resultSet.getDate("RENT_END"));
                order.setRentEnd(rentEnd);

                long carId = resultSet.getLong("CAR_ID");
                CarDao carDao = new CarDao();
                Optional<Car> optionalCar = carDao.get(carId);
                optionalCar.ifPresent(order::setCar);

                long branchId = resultSet.getInt("BRANCH_ID");
                BranchDao branchDao = new BranchDao();
                Optional<Branch> optionalBranch = branchDao.get(branchId);
                optionalBranch.ifPresent(order::setBranch);

                order.setNeedDriver(resultSet.getString("NEED_DRIVER"));
                order.setTotalCost(resultSet.getFloat("TOTAL_COST"));
                order.setStatus(resultSet.getString("ORDER_STATUS"));

                optionalOrder = Optional.of(order);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalOrder;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        String getAllOrdersQuery = "select  * from ORDERS;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllOrdersQuery);
            while(resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong("ID"));
                order.setOrderNumber(resultSet.getString("ORDER_NUM"));

                long profileId = resultSet.getLong("PROFILE_ID");
                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(profileId);
                optionalProfile.ifPresent(order::setCustomer);

                Calendar rentStart = Calendar.getInstance();
                Calendar rentEnd = Calendar.getInstance();
                rentStart.setTime(resultSet.getDate("RENT_START"));
                order.setRentStart(rentStart);
                rentEnd.setTime(resultSet.getDate("RENT_END"));
                order.setRentEnd(rentEnd);

                long carId = resultSet.getLong("CAR_ID");
                CarDao carDao = new CarDao();
                Optional<Car> optionalCar = carDao.get(carId);
                optionalCar.ifPresent(order::setCar);

                long branchId = resultSet.getInt("BRANCH_ID");
                BranchDao branchDao = new BranchDao();
                Optional<Branch> optionalBranch = branchDao.get(branchId);
                optionalBranch.ifPresent(order::setBranch);

                order.setNeedDriver(resultSet.getString("NEED_DRIVER"));
                order.setTotalCost(resultSet.getFloat("TOTAL_COST"));
                order.setStatus(resultSet.getString("ORDER_STATUS"));
                orderList.add(order);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return orderList;
    }

    public List<Order> getAllByUserId(long profileId) {
        Calendar rentStart = Calendar.getInstance();
        Calendar rentEnd = Calendar.getInstance();
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSet;
        String getAllOrdersQuery = "select  * from ORDERS where PROFILE_ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllOrdersQuery)) {
            preparedStatement.setLong(1, profileId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong("ID"));
                order.setOrderNumber(resultSet.getString("ORDER_NUM"));

                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(profileId);
                optionalProfile.ifPresent(order::setCustomer);

                rentStart.setTime(resultSet.getDate("RENT_START"));
                order.setRentStart(rentStart);
                rentEnd.setTime(resultSet.getDate("RENT_END"));
                order.setRentEnd(rentEnd);

                CarDao carDao = new CarDao();
                Optional<Car> optionalCar = carDao.get(resultSet.getInt("CAR_ID"));
                optionalCar.ifPresent(order::setCar);

                BranchDao branchDao = new BranchDao();
                Optional<Branch> optionalBranch = branchDao.get(resultSet.getInt("BRANCH_ID"));
                optionalBranch.ifPresent(order::setBranch);

                order.setNeedDriver(resultSet.getString("NEED_DRIVER"));
                order.setTotalCost(resultSet.getFloat("TOTAL_COST"));
                order.setStatus(resultSet.getString("ORDER_STATUS"));
                orderList.add(order);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return orderList;
    }

    @Override
    public long create(Order order) {
        long orderId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createOrderQuery = "insert into ORDERS values (?,?,?,?,?,?,?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setString(2, order.getOrderNumber());
            preparedStatement.setLong(3, order.getCustomer().getProfileId());
            preparedStatement.setDate(4, new java.sql.Date(order.getRentStart().getTimeInMillis()));
            preparedStatement.setDate(5, new java.sql.Date(order.getRentEnd().getTimeInMillis()));
            preparedStatement.setInt(6, order.getCar().getCarId());
            preparedStatement.setInt(7, order.getBranch().getBranchId());
            preparedStatement.setString(8, order.getNeedDriver());
            preparedStatement.setFloat(9, order.getTotalCost());
            preparedStatement.setString(10, order.getStatus());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                orderId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return orderId;
    }

    @Override
    public void update(Order order, String[] params) {

    }

    public void updateByManager(Order order, String param) {
        CONNECTION_LOCK.lock();
        String updateOrderByManagerQuery = "update ORDERS set ORDER_STATUS=? where ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateOrderByManagerQuery)) {
            if(param.equals("confirm")) {
                preparedStatement.setString(1, ORDER_CONFIRM);
            }else if(param.equals("reject")) {
                preparedStatement.setString(1, ORDER_REJECT);
            }else if("cancel".equals(param)) {
                preparedStatement.setString(1, ORDER_CANCEL);
            }
            preparedStatement.setLong(2, order.getOrderId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void delete(long id) {

    }
}
