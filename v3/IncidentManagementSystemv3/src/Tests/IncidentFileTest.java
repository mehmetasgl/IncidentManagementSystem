package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import v3.*;

import java.io.File;
import java.time.LocalDate;

public class IncidentFileTest {

    private FileManager fileManager;
    private Incident testIncident;

    @Before
    public void setUp() {
        fileManager = new FileManager();
        testIncident = new Incident("INC001", "user1", "Test Incident", LocalDate.now(), LocalDate.now().plusDays(7),
                "Test Description", "Started", "High");
    }

    @Test
    public void testSaveIncidentToFile_Positive() {
        fileManager.saveIncidentToFile(testIncident);
        assertTrue("Incident file should be created", new File("user1_incident.txt").exists());
    }

    @Test
    public void testSaveIncidentToFile_Negative() {
        fileManager.saveIncidentToFile(testIncident);
        assertFalse("Incorrect file should not be created", new File("incorrect_file.txt").exists());
    }
}
