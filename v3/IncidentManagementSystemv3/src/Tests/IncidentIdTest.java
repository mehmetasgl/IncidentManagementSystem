package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import v3.Incident;
import java.time.LocalDate;
public class IncidentIdTest {

    @Test
    public void testIncidentId_ShouldBeSetCorrectly() {
        String expectedId = "incident123";
        Incident incident = new Incident(expectedId, "user1", "Test Title", LocalDate.now(), null, 
                                         "Test description", "Open", "High");
        
        assertEquals("The Incident ID should be set correctly", expectedId, incident.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testIncidentId_ShouldThrowExceptionWhenIdIsNull() {
        new Incident(null, "user1", "Test Title", LocalDate.now(), null, "Test description", "Open", "High");
    }
}
