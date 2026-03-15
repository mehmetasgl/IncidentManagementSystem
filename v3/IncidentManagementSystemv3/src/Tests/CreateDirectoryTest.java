package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import v3.*;

import java.io.File;

public class CreateDirectoryTest {
    
    private FileManager fileManager;
    
    @Before
    public void setUp() {
        fileManager = new FileManager();
        fileManager.createDocumentsDirectory();
    }

    @Test
    public void testCreateDocumentsDirectory_Positive() {
        assertTrue("Documents directory should exist", new File("incident_documents/").exists());
    }

    @Test
    public void testCreateDocumentsDirectory_Negative() {
        File dir = new File("incident_documents_wrong");
        assertFalse("Wrong directory should not exist", dir.exists());
    }
}
