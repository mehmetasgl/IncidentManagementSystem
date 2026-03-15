package Tests;

import org.junit.*;
import v3.*;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ManageAttachmentsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private Incident incident;
    private IncidentManager manager;

    private File tempFile;

    @Before
    public void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));
        new File("documents/").mkdirs();

        tempFile = File.createTempFile("testDoc", ".txt");
        tempFile.deleteOnExit();

        incident = new Incident(
                "INC001",
                "TestUser",
                "Test Incident",
                LocalDate.now(),
                null,
                "Test description",
                "Open",
                "High"
        );

        manager = new IncidentManager(new HashMap<>(), "testUser");
        InputUtility.resetScanner();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        InputUtility.resetScanner();
    }

    @Test
    public void testManageAttachmentsPositive() {
        String input = String.format("1\n%s\n3\n", tempFile.getAbsolutePath());
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.manageAttachments(incident);

        boolean matched = incident.getAttachments().stream()
                .anyMatch(path -> path.contains(tempFile.getName()));

        System.out.println("Expected: " + tempFile.getName());
        incident.getAttachments().forEach(System.out::println);

        assertTrue("Attachment was not added correctly", matched);
        assertTrue(outContent.toString().contains("Manage Attachments"));
    }

    @Test
    public void testManageAttachmentsNegative_InvalidAndCancelRemove() {
        String input = "5\n2\n0\n3\n";  // invalid option, try to remove when none, then exit
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputUtility.resetScanner();

        manager.manageAttachments(incident);

        String output = outContent.toString();
        assertTrue(output.contains("Manage Attachments"));
        assertTrue(output.contains("No attachments to remove"));
    }
}
