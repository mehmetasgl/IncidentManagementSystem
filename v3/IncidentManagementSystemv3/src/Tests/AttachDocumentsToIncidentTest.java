package Tests;

import org.junit.*;
import v3.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class AttachDocumentsToIncidentTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private IncidentManager incidentManager;
    private Incident testIncident;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        incidentManager = new IncidentManager(new HashMap<>(), "testuser");
        testIncident = new Incident("1", "testuser", "Test Incident", LocalDate.now(), null, "Description", "Open", "High");
        InputUtility.resetScanner();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        InputUtility.resetScanner();
    }

    @Test
    public void testAttachValidDocumentPath() {
        String fakeInput = "valid/path/file.txt\n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(inputStream);

        InputUtility.resetScanner();
        incidentManager.attachDocumentsToIncident(testIncident);

        String output = outContent.toString();
        assertTrue(output.contains("Enter the file path to attach"));
    }

    @Test
    public void testAttachInvalidDocumentPath() {
        String fakeInput = "invalid/path/file.txt\n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(inputStream);

        InputUtility.resetScanner();

        incidentManager.attachDocumentsToIncident(testIncident);

        String output = outContent.toString();
        assertTrue(output.contains("Enter the file path to attach"));
    }

    @Test
    public void testAttachNoInputThenExit() {
        String fakeInput = "\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(inputStream);

        InputUtility.resetScanner();

        incidentManager.attachDocumentsToIncident(testIncident);

        String output = outContent.toString();
        assertTrue(output.contains("Attach Documents"));
    }
}