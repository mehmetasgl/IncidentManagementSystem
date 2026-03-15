package Tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import v3.*;

public class DeleteIncidentTest {

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
    public void testDeleteIncidentConfirmed() {
        Incident incident = new Incident("1", "user1", "Test Incident", LocalDate.now(), null, "Desc", "Open", "Low");
        userIncidents.get("user1").add(incident);

        String input = "1\nY\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.deleteIncident();

        String output = outContent.toString();
        assertTrue(output.contains("v3.Incident deleted successfully!"));
        assertEquals(0, userIncidents.get("user1").size());
    }

    @Test
    public void testDeleteIncidentCancelled() {
        Incident incident = new Incident("2", "user1", "Cancel Incident", LocalDate.now(), null, "Desc", "Open", "Low");
        userIncidents.get("user1").add(incident);

        String input = "1\nN\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.deleteIncident();

        String output = outContent.toString();
        assertTrue(output.contains("Deletion cancelled."));
        assertEquals(1, userIncidents.get("user1").size());
    }
}
