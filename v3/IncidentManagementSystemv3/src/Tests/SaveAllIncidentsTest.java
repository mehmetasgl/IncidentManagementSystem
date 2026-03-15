package Tests;

import org.junit.*;
import v3.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class SaveAllIncidentsTest {

    private static final String TEST_USER = "testUser";
    private static final String FILE_NAME = TEST_USER + "_incident.txt";

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @Test
    public void testSaveAllIncidents_Positive() throws IOException {
        Map<String, List<Incident>> userIncidents = new HashMap<>();
        List<Incident> incidents = new ArrayList<>();

        Incident incident = new Incident(
                "1234", TEST_USER, "Sample Incident",
                LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 2),
                "Description here", "Open", "High"
        );

        incidents.add(incident);
        userIncidents.put(TEST_USER, incidents);

        FileManager.saveAllIncidents(userIncidents);

        Path path = Paths.get(FILE_NAME);
        assertTrue("File should be created", Files.exists(path));

        String content = new String(Files.readAllBytes(path));
        assertTrue("File should contain incident title", content.contains("Sample Incident"));
    }

    @Test
    public void testSaveAllIncidents_NegativeWithInvalidFile() {
        Map<String, List<Incident>> userIncidents = new HashMap<>();
        List<Incident> incidents = new ArrayList<>();

        Incident incident = new Incident(
                "9999", "/invalidUser", "Bad Path Test",
                LocalDate.now(), null, "Invalid write test", "Open", "Low"
        );

        incidents.add(incident);
        userIncidents.put("/invalidUser", incidents);

        FileManager.saveAllIncidents(userIncidents);

        Path invalidPath = Paths.get("/invalidUser_incident.txt");
        assertFalse("Invalid file should not be created", Files.exists(invalidPath));
    }
}
