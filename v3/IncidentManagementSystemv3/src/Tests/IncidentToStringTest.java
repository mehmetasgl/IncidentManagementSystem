package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import v3.Incident;

import java.time.LocalDate;

public class IncidentToStringTest {

    @Test
    public void testToString_Positive() {
        Incident incident = new Incident("ID123", "eray", "Test Title",
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10),
                "Detailed description", "In Progress", "Medium");

        incident.addAttachment("incident_documents/ID123_file.txt");

        String output = incident.toString();
        assertTrue(output.contains("ID123"));
        assertTrue(output.contains("Detailed description"));
        assertTrue(output.contains("incident_documents/ID123_file.txt"));
    }

    @Test
    public void testToString_Negative_MissingOptionalFields() {
        Incident incident = new Incident("ID456", "user2", "Missing Fields",
                LocalDate.now(), null,
                "Test", "Started", "Low");

        String output = incident.toString();
        assertTrue(output.contains("Not specified")); // endDate null
        assertFalse(output.contains("Attached Documents")); // no attachments
    }
}