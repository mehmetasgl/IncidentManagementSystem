package Tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;
import v3.*;

public class ListIncidentsByDateTest {

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
    public void testListIncidentsByDateWithMatch() {
        LocalDate date = LocalDate.of(2025, 5, 13);
        Incident incident = new Incident("3", "user1", "Power Outage", date, date, "Power down.", "Open", "Medium");
        userIncidents.get("user1").add(incident);

        String input = "2025-05-13\n0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputUtility.resetScanner();

        manager.listIncidentsByDate();

        String output = outContent.toString();
        assertTrue(output.contains("Power Outage"));
        assertTrue(output.contains("Incidents for 2025-05-13"));
    }

    @Test
    public void testListIncidentsByDateWithNoMatch() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Incident incident = new Incident("4", "user1", "Server Down", date, date, "Server issues.", "Closed", "High");
        userIncidents.get("user1").add(incident);

        String input = "2025-01-01\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputUtility.resetScanner();

        manager.listIncidentsByDate();

        String output = outContent.toString();
        assertTrue(output.contains("No incidents found for the date: 2025-01-01"));
    }
}
