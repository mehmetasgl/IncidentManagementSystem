package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import incident_management.model.Incident;


public class IncidentDescriptionTest {
    
    private Incident incident;
    private final LocalDate startDate = LocalDate.now();
    private final LocalDate endDate = startDate.plusDays(7);
    
    @Before
    public void setUp() {
        incident = new Incident("INC001", "testuser", "Test Incident", 
                startDate, endDate, "Initial description", "Started", "Medium");
    }
    
    // Positive
    @Test
    public void testUpdateDescriptionValid() {
        String newDescription = "Updated description with more details about the incident";
        incident.setDescription(newDescription);
        assertEquals(newDescription, incident.getDescription());
    }
    
    // Positive
    @Test
    public void testUpdateDescriptionWithLongText() {
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            longDescription.append("This is a paragraph ").append(i)
                    .append(" with detailed information about the incident. ");
        }
        incident.setDescription(longDescription.toString());
        assertEquals(longDescription.toString(), incident.getDescription());
        assertTrue(incident.getDescription().length() > 500);
    }
    
    // Positive
    @Test
    public void testDescriptionWithSpecialCharacters() {
        String specialCharsDesc = "Description with special chars: !@#$%^&*()_+<>?:\"{}|~`-=[]\\;',./";
        incident.setDescription(specialCharsDesc);
        assertEquals(specialCharsDesc, incident.getDescription());
    }
    
    // Negative
    @Test(expected = NullPointerException.class)
    public void testSetNullDescription() {
        incident.setDescription(null);
    }
    
    // Negative
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDescription() {
        String emptyDesc = "";
        incident.setDescription(emptyDesc);
    }
    
    // Negative
    @Test(expected = IllegalArgumentException.class)
    public void testVeryShortDescription() {
        String shortDesc = "abc";
        incident.setDescription(shortDesc);
    }
}