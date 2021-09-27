package dao;

import appUtils.db.DBManager;
import entity.Branch;
import entity.Profile;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BranchDao implements Dao<Branch> {

    private static final Logger logger = LogManager.getLogger(BranchDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<Branch> get(long id) {
        Optional<Branch> optionalBranch = Optional.empty();
        ResultSet resultSet;
        String getBranchQuery = "select  * from BRANCHES where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getBranchQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Branch branch = new Branch();
            Profile manager = new Profile();
            while(resultSet.next()) {
                branch.setBranchId(resultSet.getInt("ID"));
                branch.setBranchName(resultSet.getString("BRANCH_NAME"));
                branch.setCityName(resultSet.getString("CITY_NAME"));
                branch.setAddress(resultSet.getString("ADDRESS"));
                long managerId = resultSet.getLong("MANAGER_ID");

                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(managerId);
                optionalProfile.ifPresent(branch::setProfile);
                optionalBranch = Optional.of(branch);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return optionalBranch;
    }

    @Override
    public List<Branch> getAll() {
        List<Branch> branchList = new ArrayList<>();
        String getAllBranchesQuery = "select  t1.*, t2.* from BRANCHES as t1 join PROFILES as t2 on t1.MANAGER_ID = t2.ID;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllBranchesQuery);
            while(resultSet.next()) {
                Branch branch = new Branch();
                ProfileDao profileDao = new ProfileDao();
                long profileId = resultSet.getLong("MANAGER_ID");
                Optional<Profile> optionalProfile = profileDao.get(profileId);
                optionalProfile.ifPresent(branch::setProfile);
                branch.setBranchId(resultSet.getInt("ID"));
                branch.setBranchName(resultSet.getString("BRANCH_NAME"));
                branch.setCityName(resultSet.getString("CITY_NAME"));
                branch.setAddress(resultSet.getString("ADDRESS"));
                branchList.add(branch);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return branchList;
    }

    @Override
    public long create(Branch branch) {
        long branchId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createUserQuery = "insert into BRANCHES values (?,?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setString(2, branch.getBranchName());
            preparedStatement.setString(3, branch.getCityName());
            preparedStatement.setString(4, branch.getAddress());
            preparedStatement.setLong(5, branch.getProfile().getProfileId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                branchId = generatedKeys.getInt(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return branchId;
    }

    @Override
    public void update(Branch branch, String[] params) {
        CONNECTION_LOCK.lock();
        String updateBranchQuery = "update BRANCHES set BRANCH_NAME=?, CITY_NAME=?, ADDRESS=?, MANAGER_ID=?  where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateBranchQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setString(3, params[2]);
            preparedStatement.setLong(4, Long.parseLong(params[3]));
            preparedStatement.setLong(5, branch.getBranchId());
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
        String deleteBranchQuery = "delete from BRANCHES where ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBranchQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

}
