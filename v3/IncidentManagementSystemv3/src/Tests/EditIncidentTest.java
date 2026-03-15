package Tests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import v3.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class EditIncidentTest {

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
    public void testEditIncidentPositive() {
        Incident incident = new Incident("1", "user1", "Old Title", LocalDate.now(), null, "Old Description", "Open", "Low");
        userIncidents.get("user1").add(incident);

        String input = "1\n1\nNew Title\n8\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.editIncident();

        String output = outContent.toString();
        assertTrue(output.contains("v3.Incident updated successfully!"));
        assertEquals("New Title", incident.getTitle());
    }

    @Test
    public void testEditIncidentNegative() {
        String input = "";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.editIncident();

        String output = outContent.toString();
        assertTrue(output.contains("You have no incidents to edit."));
    }
}
