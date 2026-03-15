package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.time.LocalDate;
import incident_management.model.Incident;

public class IncidentStatusTest {
    
    private Incident incident;
    
    @Before
    public void setUp() {
        incident = new Incident("INC001", "user1", "Status Test", 
                    LocalDate.now(), LocalDate.now().plusDays(7), 
                    "Testing status changes", "Started", "Medium");
    }

    //  positive
    @Test
    public void testPositiveStatusChanges() {

        assertEquals("Initial status should be 'Started'", "Started", incident.getStatus());
        

        incident.setStatus("In Progress");
        assertEquals("Status should be updated to 'In Progress'", "In Progress", incident.getStatus());
        
        incident.setStatus("Done");
        assertEquals("Status should be updated to 'Done'", "Done", incident.getStatus());
        
        incident.setStatus("Delayed");
        assertEquals("Status should be updated to 'Delayed'", "Delayed", incident.getStatus());
    }

    //  negative
    @Test
    public void testNegativeStatusChanges() {
        incident.setStatus("Invalid Status");
        assertEquals("System should accept any status string", "Invalid Status", incident.getStatus());
        
        incident.setStatus("");
        assertEquals("System should accept empty status", "", incident.getStatus());
        
        incident.setStatus("Started");
        assertEquals("Status should be reset to 'Started'", "Started", incident.getStatus());
    }
}