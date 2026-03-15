package Tests;

import org.junit.*;
import v3.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class ParseIncidentTest {

    private Map<String, List<Incident>> userIncidents;
    private String userId;

    @Before
    public void setUp() {
        userId = "testUser";
        userIncidents = new HashMap<>();
        userIncidents.put(userId, new ArrayList<>());
    }

    @Test
    public void testParseIncident_Positive() {
        String validIncident =
                "==========================\n" +
                "v3.Incident ID: 12345\n" +
                "Title: Sample Incident\n" +
                "Start Date: 2024-01-10\n" +
                "End Date: 2024-01-12\n" +
                "Description: Something went wrong.\n" +
                "Status: Open\n" +
                "Priority: High\n" +
                "- doc1.txt\n" +
                "- doc2.pdf\n" +
                "==========================\n";

        FileManager.parseIncident(validIncident, userId, userIncidents);

        assertEquals(1, userIncidents.get(userId).size());
    }

    @Test
    public void testParseIncident_Negative_InvalidDateFormat() {
        String malformedIncident =
                "==========================\n" +
                "v3.Incident ID: 999\n" +
                "Title: Invalid Date\n" +
                "Start Date: INVALID_DATE\n" +
                "Description: This should fail gracefully.\n" +
                "Status: Closed\n" +
                "Priority: Medium\n" +
                "==========================\n";

        FileManager.parseIncident(malformedIncident, userId, userIncidents);

        assertTrue(userIncidents.get(userId).isEmpty());
    }

    @Test
    public void testParseIncident_Negative_MissingFields() {
        String incompleteIncident =
                "==========================\n" +
                "Title: Missing Fields Incident\n" +
                "==========================\n";

        FileManager.parseIncident(incompleteIncident, userId, userIncidents);

        assertEquals(1, userIncidents.get(userId).size());

        Incident incident = userIncidents.get(userId).get(0);
        assertEquals("Missing Fields Incident", incident.getTitle());
        assertTrue(incident.getAttachments().isEmpty());
    }
}
