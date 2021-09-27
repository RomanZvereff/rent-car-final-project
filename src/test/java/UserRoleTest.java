import entity.enums.UserRole;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRoleTest {

    @Test
    public void shouldReturnCorrectUserRoleByKey() {
        String expected = "MANAGER";
        String actual = UserRole.findByKey(2).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnUserRole() {
        int expected = 1;
        int actual = UserRole.ADMIN.getUserRoleId();
        assertEquals(expected, actual);
    }

}
