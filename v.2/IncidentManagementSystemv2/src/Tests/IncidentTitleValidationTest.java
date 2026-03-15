package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import incident_management.model.Incident;

public class IncidentTitleValidationTest {

    @Test
    public void testValidTitle_Positive() {
        Incident incident = new Incident("INC999", "userX", "Valid Title", LocalDate.now(), LocalDate.now().plusDays(5),
                "desc", "Started", "High");

        assertNotNull("Incident should be created with valid title", incident);
        assertEquals("Title should match", "Valid Title", incident.getTitle());
    }

    @Test(expected = NullPointerException.class)
    public void testNullTitle_Negative() {
        new Incident("INC999", "userX", null, LocalDate.now(), null, "desc", "Started", "High");
    }
}
