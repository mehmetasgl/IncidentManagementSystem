package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.time.LocalDate;
import java.util.List;
import incident_management.model.Incident;

public class IncidentAttachmentTest {

    private Incident incident;

    @Before
    public void setUp() {
        incident = new Incident("INC007", "user1", "Attachment Test",
                    LocalDate.now(), LocalDate.now().plusDays(7),
                    "Testing attachment functionality", "Started", "Low");
    }

    @Test
    public void testPositiveAttachmentAddition() {
        List<String> attachments = incident.getAttachments();
        assertTrue("Fresh incident should have no attachments", attachments.isEmpty());

        incident.addAttachment("path/to/document1.pdf");
        incident.addAttachment("path/to/document2.docx");

        attachments = incident.getAttachments();
        assertEquals("Should have 2 attachments", 2, attachments.size());
        assertTrue("Should contain first attachment", attachments.contains("path/to/document1.pdf"));
        assertTrue("Should contain second attachment", attachments.contains("path/to/document2.docx"));
    }

    @Test
    public void testPositiveAttachmentRemoval() {
        incident.addAttachment("path/to/document1.pdf");
        incident.addAttachment("path/to/document2.docx");
        incident.addAttachment("path/to/document3.txt");

        incident.removeAttachment("path/to/document2.docx");

        List<String> attachments = incident.getAttachments();
        assertEquals("Should have 2 attachments after removal", 2, attachments.size());
        assertTrue("Should still contain first attachment", attachments.contains("path/to/document1.pdf"));
        assertFalse("Should not contain removed attachment", attachments.contains("path/to/document2.docx"));
        assertTrue("Should still contain third attachment", attachments.contains("path/to/document3.txt"));
    }

    @Test
    public void testNegativeAttachmentRemoval() {
        incident.addAttachment("path/to/document1.pdf");

        incident.removeAttachment("path/to/nonexistent.txt");

        List<String> attachments = incident.getAttachments();
        assertEquals("Should still have 1 attachment", 1, attachments.size());
        assertTrue("Should still contain original attachment", attachments.contains("path/to/document1.pdf"));
    }

    @Test
    public void testDuplicateAttachments() {
        incident.addAttachment("path/to/document1.pdf");
        incident.addAttachment("path/to/document1.pdf");

        List<String> attachments = incident.getAttachments();
        assertEquals("Should have 2 attachments (ArrayList allows duplicates)", 2, attachments.size());
    }
}