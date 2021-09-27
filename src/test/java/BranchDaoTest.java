import appUtils.db.DBManager;
import dao.BranchDao;
import entity.Branch;
import entity.Profile;
import org.junit.*;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.*;

public class BranchDaoTest {

    private static final String JDBC_DRIVER_TEST = "org.h2.Driver";
    private static final String URL_CONNECTION_TEST = "jdbc:h2:~/RENT_CAR_TEST_DB";
    private static final String USER_TEST = "sa";
    private static final String PASSWORD_TEST = "";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_CONNECTION = "jdbc\\:mysql\\://localhost\\:3306/rent_car_db?autoReconnect=true&useUnicode=yes";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static DBManager dbManager;

    @BeforeClass
    public static void beforeTest() throws ClassNotFoundException {
        try (OutputStream output = new FileOutputStream("src/main/resources/app.properties")) {
            Properties prop = new Properties();
            prop.setProperty("db.connection", URL_CONNECTION_TEST);
            prop.setProperty("user", USER_TEST);
            prop.setProperty("password", PASSWORD_TEST);
            prop.setProperty("driver", JDBC_DRIVER_TEST);

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }

        dbManager = DBManager.getInstance();

        try (Connection con = dbManager.getConnection();
             Statement statement = con.createStatement()) {

            String sqlDropTable = "DROP TABLE IF EXISTS branches;";
            statement.executeUpdate(sqlDropTable);

            String sqlCreateTable = "create table if not exists BRANCHES (\n" +
                    "    ID          int auto_increment  not null,\n" +
                    "    BRANCH_NAME varchar(50)         not null,\n" +
                    "    CITY_NAME   varchar(30)         not null,\n" +
                    "    ADDRESS     varchar(100)        not null,\n" +
                    "    MANAGER_ID  bigint              not null,\n" +
                    "    primary key(ID));";
            statement.executeUpdate(sqlCreateTable);

            String sqlInsertIntoTable = "insert into BRANCHES values (1, 'Branch name', 'City Name', 'Address', 1)";
            statement.executeUpdate(sqlInsertIntoTable);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnOneBranch() {
        BranchDao branchDao = new BranchDao();
        Optional<Branch> optionalBranch = branchDao.get(1);
        Branch branch = optionalBranch.get();
        assertNotNull(branch);
    }

    @Test
    public void shouldReturnAllBranches() {
        BranchDao branchDao = new BranchDao();
        List<Branch> branchList = branchDao.getAll();
        assertNotNull(branchList);
    }

    @Test
    public void shouldCreateBranch() {
        Branch branch = new Branch();
        branch.setBranchName("Branch 2");
        branch.setCityName("City 2");
        branch.setAddress("Address 2");
        Profile profile = new Profile();
        profile.setProfileId(1);
        branch.setProfile(profile);

        BranchDao branchDao = new BranchDao();
        Long id = branchDao.create(branch);

        assertNotNull(id);
    }

    @Test
    public void shouldUpdateBranch() {
        Branch branch = new Branch();
        branch.setBranchId(1);
        BranchDao branchDao = new BranchDao();

        branchDao.update(branch, new String[]{"Branch new", "City new", "Address new", "8"});

        String name = null;

        Optional<Branch> optionalBranch = branchDao.get(1);
        if(optionalBranch.isPresent()) {
            branch = optionalBranch.get();
            name = branch.getBranchName();
        }

        assertEquals(name, "Branch new");
    }

    @Test
    public void shouldDeleteBranch() {
        BranchDao branchDao = new BranchDao();
        branchDao.delete(1);

        boolean isDeleted = branchDao.get(1).isPresent();

        assertFalse(isDeleted);
    }

    @AfterClass
    public static void afterTest() {
        try (OutputStream output = new FileOutputStream("src/main/resources/app.properties")) {
            Properties prop = new Properties();
            prop.setProperty("db.connection", URL_CONNECTION);
            prop.setProperty("user", USER);
            prop.setProperty("password", PASSWORD);
            prop.setProperty("driver", JDBC_DRIVER);

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
