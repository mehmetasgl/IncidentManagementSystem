package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import v3.*;
import static org.junit.Assert.*;

public class GetValidIntegerInputTest {
    
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
    public void testGetValidIntegerInput_PositiveCase() {
        ByteArrayInputStream in = new ByteArrayInputStream("5\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        int result = InputUtility.getValidIntegerInput(1, 10);
        
        assertEquals("Method should return the valid integer input", 5, result);
    }
    
    @Test
    public void testGetValidIntegerInput_NegativeCase() {
        ByteArrayInputStream in = new ByteArrayInputStream("15\n7\n".getBytes());
        System.setIn(in);
        InputUtility.resetScanner();
        
        int result = InputUtility.getValidIntegerInput(1, 10);
        
        assertEquals("Method should reject the out-of-range input and accept the subsequent valid input", 7, result);
    }
}