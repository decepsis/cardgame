package com.teamkoala;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the application driver Main class.
 */
public class MainTest {
    final ByteArrayOutputStream fakeOut = new ByteArrayOutputStream();
    PipedOutputStream fakeIn = new PipedOutputStream();
    PrintStream printer = new PrintStream(fakeIn);

    final PrintStream oldOut = System.out;
    final InputStream oldIn = System.in;

    /**
     * Hooks stdio so that we can interface with the view.
     */
    @BeforeEach
    void hookStdio() throws IOException {
        System.setOut(new PrintStream(fakeOut));
        System.setIn(new PipedInputStream(fakeIn));
    }

    /**
     * Cleanup to ensure that we do not interfere with JUnit 5 and other things.
     */
    @AfterEach
    void unhookStdio() {
        System.setOut(oldOut);
        System.setIn(oldIn);

        // Just double check and ensure all caught messages are logged, in case JUnit has a fit.
        System.out.print(fakeOut);

        printer.close();
    }

    /**
     * Tests the main function.
     */
    @Test
    void testEntry() {
        printer.println("0");

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> Main.main(null), "main did not return.");

        assertEquals("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~",
                fakeOut.toString().lines().findFirst().orElse("").trim(),
                "Did not find the title screen in the output. Did it hand off?");
    }
}
