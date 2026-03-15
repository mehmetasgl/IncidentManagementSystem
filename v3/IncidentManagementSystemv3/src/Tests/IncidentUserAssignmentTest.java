package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import v3.*;

public class IncidentUserAssignmentTest {

    // positive
    @Test
    public void testPositiveUserAssignment() {
        Incident incident = new Incident("INC008", "user1", "User Assignment Test",
                LocalDate.now(), LocalDate.now().plusDays(7), 
                "Testing user assignment", "Started", "Low");
        
        assertEquals("User should be correctly assigned", "user1", incident.getUser());
    }

    // positive
    @Test
    public void testMultipleIncidentsForSameUser() {
        String userId = "user2";
        
        Incident incident1 = new Incident("INC009", userId, "First Incident", 
                LocalDate.now(), LocalDate.now().plusDays(7), 
                "First incident description", "Started", "Low");
        
        Incident incident2 = new Incident("INC010", userId, "Second Incident", 
                LocalDate.now(), LocalDate.now().plusDays(14), 
                "Second incident description", "In Progress", "Medium");
        
        assertEquals("First incident should have correct user", userId, incident1.getUser());
        assertEquals("Second incident should have correct user", userId, incident2.getUser());
    }

    // negative
    @Test
    public void testEmptyUserHandling() {
        Incident incident = new Incident("INC011", "", "Empty User Test",
                LocalDate.now(), LocalDate.now().plusDays(7), 
                "Testing with empty user", "Started", "Low");
        
        assertEquals("Empty user string should be preserved", "", incident.getUser());
    }

    // negative
    @Test
    public void testNullUserHandling() {
        try {
            Incident incident = new Incident("INC012", null, "Null User Test", 
                    LocalDate.now(), LocalDate.now().plusDays(7), 
                    "Testing with null user", "Started", "Low");
            
            assertNull("Null user should be preserved", incident.getUser());
        } catch (NullPointerException e) {
            assertTrue("NullPointerException is an acceptable response to null user", true);
        }
    }
}