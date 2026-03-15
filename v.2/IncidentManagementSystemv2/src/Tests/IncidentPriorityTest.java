package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.time.LocalDate;
import incident_management.model.Incident;

public class IncidentPriorityTest {
    
    private Incident incident;
    
    @Before
    public void setUp() {
        incident = new Incident("INC002", "user1", "Priority Test", 
                    LocalDate.now(), LocalDate.now().plusDays(7), 
                    "Testing priority changes", "Started", "Low");
    }

    // positive
    @Test
    public void testPositivePriorityChanges() {

        assertEquals("Initial priority should be 'Low'", "Low", incident.getPriority());
        
        incident.setPriority("Medium");
        assertEquals("Priority should be updated to 'Medium'", "Medium", incident.getPriority());
        
        incident.setPriority("Important");
        assertEquals("Priority should be updated to 'Important'", "Important", incident.getPriority());
        
        incident.setPriority("Urgent");
        assertEquals("Priority should be updated to 'Urgent'", "Urgent", incident.getPriority());
    }

    // negative
    @Test
    public void testNegativePriorityChanges() {
        incident.setPriority("Critical");
        assertEquals("System should accept any priority string", "Critical", incident.getPriority());
        
        incident.setPriority("");
        assertEquals("System should accept empty priority", "", incident.getPriority());
        
        incident.setPriority("Low");
        assertEquals("Priority should be reset to 'Low'", "Low", incident.getPriority());
    }
}