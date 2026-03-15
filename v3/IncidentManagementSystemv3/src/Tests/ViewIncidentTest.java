package Tests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import v3.*;
import static org.junit.Assert.*;
public class ViewIncidentTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private IncidentManager manager;
    private Map<String, List<Incident>> userIncidents;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        userIncidents = new HashMap<>();
        userIncidents.put("user1", new ArrayList<>());
        manager = new IncidentManager(userIncidents, "user1");
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outContent.reset();
    }

    @Test
    public void testViewIncidentsPositive() {
        Incident incident = new Incident("1", "user1", "Power Outage", LocalDate.now(), null, "No electricity", "Open", "High");
        userIncidents.get("user1").add(incident);

        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.viewIncidents();

        String output = outContent.toString();
        assertTrue(output.contains("===== Your Incidents ====="));
        assertTrue(output.contains("Power Outage"));
        assertTrue(output.contains("No electricity"));
    }

    @Test
    public void testViewIncidentsEmptyList() {
        String input = "";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.viewIncidents();

        String output = outContent.toString();
        assertTrue(output.contains("You have no incidents."));
    }
}
