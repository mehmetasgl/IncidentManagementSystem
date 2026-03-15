package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import v3.*;

public class IncidentDatesTest {
    
    @Test
    public void testPositiveDateHandling() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 15);
        
        Incident incident = new Incident("INC003", "user1", "Date Test", 
                startDate, endDate, "Testing date handling", "Started", "Low");
        
        assertEquals("Start date should match the input", startDate, incident.getStartDate());
        assertEquals("End date should match the input", endDate, incident.getEndDate());
        
        LocalDate newStartDate = LocalDate.of(2025, 6, 1);
        LocalDate newEndDate = LocalDate.of(2025, 6, 30);
        
        incident.setStartDate(newStartDate);
        incident.setEndDate(newEndDate);
        
        assertEquals("Start date should be updated", newStartDate, incident.getStartDate());
        assertEquals("End date should be updated", newEndDate, incident.getEndDate());
    }
    
    @Test
    public void testNullEndDate() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        
        Incident incident = new Incident("INC004", "user1", "Null End Date Test", 
                startDate, null, "Testing null end date", "Started", "Low");
        
        assertEquals("Start date should match the input", startDate, incident.getStartDate());
        assertNull("End date should be null", incident.getEndDate());
        
        incident.setEndDate(LocalDate.of(2025, 5, 15));
        assertNotNull("End date should be non-null after setting", incident.getEndDate());
        
        incident.setEndDate(null);
        assertNull("End date should be null after setting to null", incident.getEndDate());
    }
    
    @Test
    public void testSetStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 15);
        
        Incident incident = new Incident("INC005", "user1", "Invalid Date Test", 
                startDate, endDate, "Testing invalid date scenario", "Started", "Low");
        
        LocalDate invalidStartDate = LocalDate.of(2025, 5, 20);
        incident.setStartDate(invalidStartDate);
        
        assertEquals("Start date should be updated even if after end date", 
                    invalidStartDate, incident.getStartDate());
        assertEquals("End date should remain unchanged", endDate, incident.getEndDate());
    }
    
    @Test
    public void testSetEndDateBeforeStartDate() {
        LocalDate startDate = LocalDate.of(2025, 5, 15);
        LocalDate endDate = LocalDate.of(2025, 5, 30);
        
        Incident incident = new Incident("INC006", "user1", "Invalid Date Test", 
                startDate, endDate, "Testing invalid date scenario", "Started", "Low");
        
        LocalDate invalidEndDate = LocalDate.of(2025, 5, 10);
        incident.setEndDate(invalidEndDate);
        
        assertEquals("Start date should remain unchanged", startDate, incident.getStartDate());
        assertEquals("End date should be updated even if before start date", 
                    invalidEndDate, incident.getEndDate());
    }
}