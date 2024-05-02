package org.transaction.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ATMTransactionApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMain() {
        ATMTransactionApplication.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Dispensed denominations for 2000"));
        assertTrue(output.contains("Dispensed denominations for 80"));
        assertTrue(output.contains("Dispensed denominations for 250"));
        assertTrue(output.contains("Amount must be mutiples of : 10"));
        assertTrue(output.contains("Insufficient Funds In the ATM"));
    }
}