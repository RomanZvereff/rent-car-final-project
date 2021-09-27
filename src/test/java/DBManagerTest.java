import appUtils.db.DBManager;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

public class DBManagerTest {

    @Test
    public void shouldReturnDBManager() {
        DBManager dbManager = DBManager.getInstance();
        assertNotNull(dbManager);
    }

    @Test
    public void shouldReturnConnection() {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConnection();
        assertNotNull(connection);
    }

}
