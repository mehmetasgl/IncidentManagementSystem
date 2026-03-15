package Tests;

import org.junit.*;
import v3.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;

public class AttachDocumentTest {

    private static final String TEST_FILE_NAME = "test_doc.txt";
    private static final String INVALID_FILE_NAME = "nonexistent_file.txt";
    private Incident incident;

    @Before
    public void setUp() throws IOException {
        File file = new File(TEST_FILE_NAME);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Test document content");
            }
        }

        incident = new Incident(UUID.randomUUID().toString().substring(0, 8), "testUser",
                "Test Title", null, null, "Test Description", "Open", "Low");

        FileManager.createDocumentsDirectory();
    }

    @After
    public void tearDown() {
        File original = new File(TEST_FILE_NAME);
        if (original.exists()) original.delete();

        for (String attachment : incident.getAttachments()) {
            new File(attachment).delete();
        }
    }

    @Test
    public void testAttachDocument_Positive() {
        String result = FileManager.attachDocument(incident, TEST_FILE_NAME);
        assertTrue(result.contains("Document attached successfully"));
        assertEquals(1, incident.getAttachments().size());
        File attachedFile = new File(incident.getAttachments().get(0));
        assertTrue(attachedFile.exists());
    }

    @Test
    public void testAttachDocument_Negative_InvalidPath() {
        String result = FileManager.attachDocument(incident, INVALID_FILE_NAME);
        assertEquals("File does not exist. Please enter a valid file path.", result);
        assertTrue(incident.getAttachments().isEmpty());
    }

}
