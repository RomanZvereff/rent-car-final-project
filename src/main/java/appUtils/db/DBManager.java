package appUtils.db;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager dbManager;
    private static String url, user, pass, driver;

    private DBManager() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("app.properties");
            properties.load(inputStream);
            url = properties.getProperty("db.connection");
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
            driver = properties.getProperty("driver");
        }catch(IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }

    public static synchronized DBManager getInstance() {
        if(dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        }catch(SQLException | ClassNotFoundException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

}
