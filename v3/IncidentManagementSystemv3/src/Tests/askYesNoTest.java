package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import v3.*;
import static org.junit.Assert.*;

public class askYesNoTest {
    
    private final InputStream originalIn = System.in;
    
    @Before
    public void setUp() {
        InputUtility.resetScanner();
    }
    
    @After
    public void tearDown() {
        System.setIn(originalIn);
        InputUtility.resetScanner();
    }
    
    @Test
    public void testAskYesNo_CorrectlyHandlesYInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("Y\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertTrue("Method should return true for 'Y' input", result);
    }
    
    @Test
    public void testAskYesNo_CorrectlyHandlesYESInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("YES\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertTrue("Method should return true for 'YES' input", result);
    }
    
    @Test
    public void testAskYesNo_CorrectlyHandlesNInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("N\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertFalse("Method should return false for 'N' input", result);
    }
    
    @Test
    public void testAskYesNo_CorrectlyHandlesNOInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("NO\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertFalse("Method should return false for 'NO' input", result);
    }
    
    @Test
    public void testAskYesNo_LowercaseInputFailure() {
        ByteArrayInputStream in = new ByteArrayInputStream("yes\nY\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertTrue("Method should reject lowercase and accept the subsequent uppercase input", result);
    }
    
    @Test
    public void testAskYesNo_InvalidInputHandling() {
        ByteArrayInputStream in = new ByteArrayInputStream("MAYBE\nN\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        boolean result = InputUtility.askYesNo("Do you want to continue? (Y/N): ");
        
        assertFalse("Method should handle invalid input and then process valid input", result);
    }
}