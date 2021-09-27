import entity.User;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void shouldGenerateStrongPasswordHash() {
        String password = "12345";
        String passwordHash = User.generateStrongPasswordHash(password);
        assertNotNull(passwordHash);
    }

    @Test
    public void shouldCorrectGenerateStrongPasswordHash() {
        String password = "12345";
        String passwordHash = User.generateStrongPasswordHash(password);
        assertTrue(passwordHash.startsWith("1000:"));
    }

    @Test
    public void shouldValidatePassword() {
        String password = "12345";
        String passwordHash = User.generateStrongPasswordHash(password);
        boolean isValid = User.validatePassword(password, passwordHash);
        assertTrue(isValid);
    }

}
