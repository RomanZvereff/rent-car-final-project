package dao;

import appUtils.db.DBManager;
import entity.User;
import entity.enums.UserRole;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDao implements Dao<User> {

    private static final Logger logger = LogManager.getLogger(UserDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    public Optional<User> validateUser(String userLogin, String userPassword) {
        Optional<User> optionalUser = Optional.empty();
        String securedPassword;
        ResultSet resultSet;
        String getUserQuery = "select  * from USERS where USER_LOGIN = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery)) {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                securedPassword = resultSet.getString("USER_PASS");
                boolean matched = User.validatePassword(userPassword, securedPassword);
                if(matched) {
                    user.setUserId(resultSet.getLong("ID"));
                    user.setUserLogin(resultSet.getString("USER_LOGIN"));
                    user.setUserRole(UserRole.findByKey(resultSet.getInt("ROLE_ID")).toString());
                    optionalUser = Optional.of(user);
                }
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalUser;
    }

    public boolean getUserIfExists(String userLogin) {
        boolean isExists = false;
        ResultSet resultSet;
        String getUserQuery = "select  * from USERS where USER_LOGIN = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery)) {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            isExists = resultSet.next();
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return isExists;
    }

    @Override
    public Optional<User> get(long id) {
        Optional<User> optionalUser = Optional.empty();
        ResultSet resultSet;
        String getUserQuery = "select  t1.*, t2.ROLE_NAME from USERS as t1 join USER_ROLE as t2 on t1.ROLE_ID = t2.ID where t1.ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                user.setUserId(resultSet.getLong("ID"));
                user.setUserLogin(resultSet.getString("USER_LOGIN"));
                user.setUserPassword(resultSet.getString("USER_PASS"));
                user.setUserRole(resultSet.getString("ROLE_NAME"));
                optionalUser = Optional.of(user);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalUser;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String getAllUsersQuery = "select  t1.*, t2.ROLE_NAME from USERS as t1 join USER_ROLE as t2 on t1.ROLE_ID = t2.ID;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsersQuery);
            while(resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("ID"));
                user.setUserLogin(resultSet.getString("USER_LOGIN"));
                user.setUserPassword(resultSet.getString("USER_PASS"));
                user.setUserRole(resultSet.getString("ROLE_NAME"));
                userList.add(user);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return userList;
    }

    @Override
    public long create(User user) {
        long userId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createUserQuery = "insert into USERS values (?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setString(2, user.getUserLogin());
            String generatedSecuredPasswordHash = User.generateStrongPasswordHash(user.getUserPassword());
            preparedStatement.setString(3, generatedSecuredPasswordHash);
            preparedStatement.setInt(4, UserRole.valueOf(user.getUserRole()).getUserRoleId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return userId;
    }

    @Override
    public void update(User user, String[] params) {
        CONNECTION_LOCK.lock();
        String updateUserQuery = "update USERS set USER_PASS = ? where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setLong(2, user.getUserId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void delete(long id) {
        CONNECTION_LOCK.lock();
        String deleteUserQuery = "delete from USERS where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

}
