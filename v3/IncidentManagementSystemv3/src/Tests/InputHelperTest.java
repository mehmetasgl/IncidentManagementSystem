package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import v3.InputUtility;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class InputHelperTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemIO() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        InputUtility.resetScanner();
    }

    @Test
    public void testGetLineInput_Positive() {
        provideInput("   test input   \n");

        String result = InputUtility.getLineInput("Enter something: ");
        assertEquals("test input", result);
    }

    @Test
    public void testGetLineInput_Negative() {
        provideInput("     \n");

        String result = InputUtility.getLineInput("Prompt: ");
        assertEquals("", result);
    }
}
