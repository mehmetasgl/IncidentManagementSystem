package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;
import v3.*;
import static org.junit.Assert.*;

public class CreateIncidentTest {

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
    public void testCreateIncidentPositive() {
        String input = String.join("\n",
                "Test Incident",      // title
                "2024-05-01",          // start date
                "2024-05-10",          // end date
                "Test description",    // description
                "1",                   // status option
                "2",                   // priority option
                "N"                    // attach documents
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner(); // Eğer tek scanner ile çalışıyorsan bu gerekli olabilir
        manager.createIncident();


        String output = outContent.toString();
        assertTrue(output.contains("v3.Incident Created and Saved!"));
        assertEquals(1, userIncidents.get("user1").size());
        Incident created = userIncidents.get("user1").get(0);
        assertEquals("Test Incident", created.getTitle());
        assertEquals("Started", created.getStatus());
    }

    @Test
    public void testCreateIncidentWithEmptyTitle() {
        String input = String.join("\n",
                "",                    // title empty
                "2024-05-01",          // start date
                "2024-05-02",          // end date
                "Short description",   // description
                "1",                   // status input
                "2",                   // priority input
                "N"                    // askYesNo
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.createIncident();

        String output = outContent.toString();
        assertTrue(output.contains("v3.Incident Created and Saved!"));
        List<Incident> incidents = userIncidents.get("user1");
        assertEquals(1, incidents.size());
        assertEquals("", incidents.get(0).getTitle());
    }

}
