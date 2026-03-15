package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import incident_management.model.User;

public class UserTest {

    //  positive
    @Test
    public void testGetUserId() {
        User user = new User("testuser", "password123");
        assertEquals("Should return correct user ID", "testuser", user.getUserId());
    }

    //  positive
    @Test
    public void testSuccessfulAuthentication() {
        User user = new User("testuser", "password123");
        assertTrue("Should authenticate with correct credentials",
                user.authenticate("testuser", "password123"));
    }

    //  negative
    @Test
    public void testFailedAuthenticationWithWrongPassword() {
        User user = new User("testuser", "password123");
        assertFalse("Should reject authentication with wrong password",
                user.authenticate("testuser", "wrongpassword"));
    }

    //  negative
    @Test
    public void testFailedAuthenticationWithWrongUsername() {
        User user = new User("testuser", "password123");
        assertFalse("Should reject authentication with wrong username",
                user.authenticate("wronguser", "password123"));
    }
}
