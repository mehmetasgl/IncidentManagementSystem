package Tests;

import org.junit.*;
import v3.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

public class RemoveDocumentTest {

    private Incident incident;
    private String testFilePath;

    @Before
    public void setUp() throws IOException {
        incident = new Incident(UUID.randomUUID().toString().substring(0, 8), "testUser",
                "Test Title", null, null, "Test Description", "Open", "Low");

        testFilePath = "removable_test_doc.txt";
        File file = new File(testFilePath);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Temporary test file content.");
            }
        }
        incident.addAttachment(testFilePath);
    }

    @After
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) file.delete();
    }

    @Test
    public void testRemoveDocument_Positive() {
        assertTrue(new File(testFilePath).exists());

        boolean result = FileManager.removeDocument(incident, testFilePath);

        assertTrue(result);
        assertFalse(new File(testFilePath).exists());
        assertFalse(incident.getAttachments().contains(testFilePath));
    }

    @Test
    public void testRemoveDocument_Negative_FileDoesNotExist() {
        String nonExistentPath = "this_file_does_not_exist.txt";

        boolean result = FileManager.removeDocument(incident, nonExistentPath);

        assertFalse(result);
        assertFalse(incident.getAttachments().contains(nonExistentPath));
    }
}
