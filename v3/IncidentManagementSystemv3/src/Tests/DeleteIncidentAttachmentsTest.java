package Tests;

import org.junit.*;
import v3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;

public class DeleteIncidentAttachmentsTest {

    private Incident incident;
    private List<String> tempFilePaths;

    @Before
    public void setUp() throws IOException {
        incident = new Incident("test-id", "test-user", "title", null, null, "desc", "status", "priority");
        tempFilePaths = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (String path : tempFilePaths) {
            try {
                Files.deleteIfExists(new File(path).toPath());
            } catch (IOException ignored) {}
        }
    }

    @Test
    public void testDeleteIncidentAttachments_Positive() throws IOException {
        File tempFile1 = File.createTempFile("test_doc1_", ".txt");
        File tempFile2 = File.createTempFile("test_doc2_", ".txt");

        String path1 = tempFile1.getAbsolutePath();
        String path2 = tempFile2.getAbsolutePath();

        incident.addAttachment(path1);
        incident.addAttachment(path2);

        tempFilePaths.add(path1);
        tempFilePaths.add(path2);

        assertTrue(tempFile1.exists());
        assertTrue(tempFile2.exists());

        FileManager.deleteIncidentAttachments(incident);

        assertFalse(new File(path1).exists());
        assertFalse(new File(path2).exists());
    }

    @Test
    public void testDeleteIncidentAttachments_Negative_FileDoesNotExist() {
        String nonExistentPath = "non_existent_file_123456.txt";
        incident.addAttachment(nonExistentPath);

        FileManager.deleteIncidentAttachments(incident);

        assertTrue("Attachment list should still contain the attachment", incident.getAttachments().contains(nonExistentPath));
    }
}
