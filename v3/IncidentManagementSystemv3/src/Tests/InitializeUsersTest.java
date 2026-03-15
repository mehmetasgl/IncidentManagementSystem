package Tests;

import org.junit.Before;
import org.junit.Test;
import v3.FileManager;
import v3.User;

import java.util.*;

import static org.junit.Assert.*;

public class InitializeUsersTest {

    private List<User> users;
    private Map<String, List<v3.Incident>> userIncidents;

    private class TestInitializer {
        public void initializeUsers(List<User> users, Map<String, List<v3.Incident>> userIncidents) {
            if (users == null || userIncidents == null) {
                throw new NullPointerException("Users or UserIncidents cannot be null");
            }

            users.add(new User("oyku", "1234"));
            users.add(new User("eray", "1234"));
            users.add(new User("berkay", "1234"));
            users.add(new User("mehmet", "1234"));

            for (User user : users) {
                userIncidents.put(user.getUserId(), new ArrayList<>());
            }

            FileManager.loadIncidents(users, userIncidents);
        }
    }

    @Before
    public void setUp() {
        users = new ArrayList<>();
        userIncidents = new HashMap<>();
    }

    @Test
    public void testInitializeUsers_ShouldPopulateUsersAndMap() {
        TestInitializer initializer = new TestInitializer();
        initializer.initializeUsers(users, userIncidents);

        assertEquals(4, users.size());
        assertTrue(userIncidents.containsKey("oyku"));
        assertTrue(userIncidents.get("mehmet").isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testInitializeUsers_ShouldThrowException_WhenUsersIsNull() {
        TestInitializer initializer = new TestInitializer();
        initializer.initializeUsers(null, userIncidents);
    }
}
