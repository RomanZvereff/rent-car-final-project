package dao;

import appUtils.db.DBManager;
import entity.Profile;
import entity.User;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProfileDao implements Dao<Profile> {

    private static final Logger logger = LogManager.getLogger(ProfileDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    public List<Profile> getAllManagers() {
        List<Profile> managerList = new ArrayList<>();
        String getAllManagersQuery = "select  t2.USER_LOGIN, t1.* from PROFILES as t1 join USERS as t2 on t1.USER_ID = t2.ID and t2.ROLE_ID = 2;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllManagersQuery);
            while(resultSet.next()) {
                Profile profile = new Profile();
                UserDao userDao = new UserDao();
                Optional<User> userOptional = userDao.get(resultSet.getLong("USER_ID"));
                if(userOptional.isPresent()) {
                    User user = userOptional.get();
                    profile.setUser(user);
                }
                profile.setProfileId(resultSet.getLong("ID"));
                profile.setFirstName(resultSet.getString("FRST_NAME"));
                profile.setLastName(resultSet.getString("LAST_NAME"));
                profile.setPhoneNumber(resultSet.getString("PHONE_NUM"));
                profile.setEmail(resultSet.getString("EMAIL"));
                managerList.add(profile);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return managerList;
    }

    @Override
    public Optional<Profile> get(long id) {
        User user;
        Optional<Profile> optionalProfile = Optional.empty();
        ResultSet resultSet;
        String getProfileQuery = "select  t2.USER_LOGIN, t1.* from PROFILES as t1 join USERS as t2 on t1.USER_ID = t2.ID where t1.ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getProfileQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Profile profile = new Profile();
            UserDao userDao = new UserDao();
            Optional<User> userOptional = userDao.get(id);
            if(userOptional.isPresent()) {
                user = userOptional.get();
                profile.setUser(user);
            }
            while(resultSet.next()) {
                profile.setProfileId(resultSet.getLong("ID"));
                profile.setFirstName(resultSet.getString("FRST_NAME"));
                profile.setLastName(resultSet.getString("LAST_NAME"));
                profile.setPhoneNumber(resultSet.getString("PHONE_NUM"));
                profile.setEmail(resultSet.getString("EMAIL"));
                optionalProfile = Optional.of(profile);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalProfile;
    }

    public Optional<Profile> getProfileUserId(long id) {
        User user;
        Optional<Profile> optionalProfile = Optional.empty();
        ResultSet resultSet;
        String getProfileQuery = "select  * from PROFILES where USER_ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getProfileQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Profile profile = new Profile();
            UserDao userDao = new UserDao();
            Optional<User> userOptional = userDao.get(id);
            if(userOptional.isPresent()) {
                user = userOptional.get();
                profile.setUser(user);
            }
            while(resultSet.next()) {
                profile.setProfileId(resultSet.getLong("ID"));
                profile.setFirstName(resultSet.getString("FRST_NAME"));
                profile.setLastName(resultSet.getString("LAST_NAME"));
                profile.setPhoneNumber(resultSet.getString("PHONE_NUM"));
                profile.setEmail(resultSet.getString("EMAIL"));
                optionalProfile = Optional.of(profile);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalProfile;
    }

    @Override
    public List<Profile> getAll() {
        List<Profile> profileList = new ArrayList<>();
        String getAllProfilesQuery = "select  t2.USER_LOGIN, t1.* from PROFILES as t1 join USERS as t2 on t1.USER_ID = t2.ID;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllProfilesQuery);
            while(resultSet.next()) {
                Profile profile = new Profile();
                UserDao userDao = new UserDao();
                Optional<User> userOptional = userDao.get(resultSet.getLong("USER_ID"));
                if(userOptional.isPresent()) {
                    User user = userOptional.get();
                    profile.setUser(user);
                }
                profile.setFirstName(resultSet.getString("FRST_NAME"));
                profile.setLastName(resultSet.getString("LAST_NAME"));
                profile.setPhoneNumber(resultSet.getString("PHONE_NUM"));
                profile.setEmail(resultSet.getString("EMAIL"));
                profileList.add(profile);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return profileList;
    }

    @Override
    public long create(Profile profile) {
        long profileId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createProfileQuery = "insert into PROFILES values (?,?,?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createProfileQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setString(2, profile.getFirstName());
            preparedStatement.setString(3, profile.getLastName());
            preparedStatement.setString(4, profile.getPhoneNumber());
            preparedStatement.setString(5, profile.getEmail());
            preparedStatement.setLong(6, profile.getUser().getUserId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                profileId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return profileId;
    }

    @Override
    public void update(Profile profile, String[] params) {
        CONNECTION_LOCK.lock();
        String updateProfileQuery = "update PROFILES set FRST_NAME=?, LAST_NAME=?, EMAIL=?, PHONE_NUM=? where ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateProfileQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setString(3, params[2]);
            preparedStatement.setString(4, params[3]);
            preparedStatement.setLong(5, profile.getProfileId());
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
        String deleteProfileQuery = "delete from PROFILES where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteProfileQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

}
