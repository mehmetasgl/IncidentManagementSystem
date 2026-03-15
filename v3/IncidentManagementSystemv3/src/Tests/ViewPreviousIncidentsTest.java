package Tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v3.Incident;
import v3.IncidentManager;

public class ViewPreviousIncidentsTest {

    private IncidentManager incidentManager;
    private Map<String, List<Incident>> userIncidents;
    private final String currentUser = "testUser";
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));

        userIncidents = new HashMap<>();
        List<Incident> incidents = new ArrayList<>();
        userIncidents.put(currentUser, incidents);
        userIncidents.put("otherUser", new ArrayList<>());

        Incident testIncident = new Incident(
                "12345678",
                currentUser,
                "Test Incident",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Test description",
                "Open",
                "High"
        );
        incidents.add(testIncident);

        Incident otherIncident = new Incident(
                "87654321",
                "otherUser",
                "Other Incident",
                LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(3),
                "Other description",
                "In Progress",
                "Medium"
        );
        userIncidents.get("otherUser").add(otherIncident);

        incidentManager = new IncidentManager(userIncidents, currentUser);
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testViewPreviousIncidents_WithIncidents() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);

        incidentManager.viewPreviousIncidents();

        String output = outputStream.toString();

        assertTrue(output.contains("===== View Previous v3.Incident Posts ====="));
        assertTrue(output.contains("Test Incident"));
        assertTrue(output.contains("Other Incident"));
        assertTrue(output.contains("testUser"));
        assertTrue(output.contains("otherUser"));
    }

    @Test
    public void testViewPreviousIncidents_NoIncidents() {
        Map<String, List<Incident>> emptyUserIncidents = new HashMap<>();
        emptyUserIncidents.put(currentUser, new ArrayList<>());

        IncidentManager emptyIncidentManager = new IncidentManager(emptyUserIncidents, currentUser);

        outputStream.reset();

        emptyIncidentManager.viewPreviousIncidents();

        String output = outputStream.toString();

        assertTrue(output.contains("No incidents have been recorded in the system."));
    }
}