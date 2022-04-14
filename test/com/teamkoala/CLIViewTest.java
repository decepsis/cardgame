package com.teamkoala;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CLIView class including I/O.
 *
 * @version Lab 6
 */
class CLIViewTest {
    final CLIView view = new CLIView();
    final ByteArrayOutputStream fakeOut = new ByteArrayOutputStream();
    PipedOutputStream fakeIn = null;
    PrintStream printer = null;

    final PrintStream oldOut = System.out;

    /**
     * Hooks stdio so that we can interface with the view.
     */
    @BeforeEach
    void hookStdio() throws IOException, IllegalAccessException, NoSuchFieldException {
        System.setOut(new PrintStream(fakeOut));

        fixPipe();
    }

    /**
     * Why is this a seperate method? Java has weird pipe behavior with threads.
     */
    private void fixPipe() throws NoSuchFieldException, IOException, IllegalAccessException {
        fakeIn = new PipedOutputStream();
        printer = new PrintStream(fakeIn);

        // CLIView will already have captured stdin, so we'll just hook it directly.
        Field scanner = view.getClass().getDeclaredField("input");
        scanner.setAccessible(true);
        scanner.set(view, new Scanner(new PipedInputStream(fakeIn)));
    }

    /**
     * Cleanup to ensure that we do not interfere with JUnit 5 and other things.
     */
    @AfterEach
    void unhookStdio() {
        System.setOut(oldOut);

        // Just double check and ensure all caught messages are logged, in case JUnit has a fit.
        System.out.print(fakeOut);
    }

    /**
     * Tests start screen output.
     */
    @Test
    void showStartScreen() {
        view.showStartScreen();

        assertEquals("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~", fakeOut.toString().trim(),
                "Unexpected title message, investigate and/or update the test.");
    }

    /**
     * Tests the getNumberOfPlayers() function.
     * <br><br><br>
     * THIS THING IS A PAIN. This function is the reason fixPipe() exists.
     * assertTimeoutPreemptively(), which is required to die fast, forks threads, which will break our hooked pipe.
     * Without it, only the first assert passes, the other calls to the view never receive data.
     */
    @Test
    void getNumberOfPlayers() throws IOException, NoSuchFieldException, IllegalAccessException {
        // These strings are VERY sensitive
        final String prompt = "Enter the number of players(min. 2 & max. 8) or 0 to exit: ";
        final String error = "Please enter valid number of players." + System.lineSeparator();
        final String[] expected = { prompt, error, prompt, error, prompt, prompt, prompt };
        final String expectedStr = String.join("", expected);

        // The easiest way to do this with this current framework is just spam inputs and measure outputs.
        // Test lower error bound
        printer.println(1);

        // Test upper error bound
        printer.println(9);

        // Test special number
        printer.println(0);

        // Now, we should have returned.
        int num = assertTimeoutPreemptively(Duration.ofMillis(50), view::getNumberOfPlayers, "getNumberOfPlayers ignored special value and timed out.");
        assertEquals(0, num, "getNumberOfPlayers did not return special value. May have returned out of bounds.");
        fixPipe();

        // Test boundary closure
        printer.println(2);

        num = assertTimeoutPreemptively(Duration.ofMillis(50), view::getNumberOfPlayers, "getNumberOfPlayers ignored boundary value.");
        assertEquals(2, num, "getNumberOfPlayers did not return boundary value.");
        fixPipe();

        printer.println(8);

        num = assertTimeoutPreemptively(Duration.ofMillis(50), view::getNumberOfPlayers, "getNumberOfPlayers ignored boundary value.");
        assertEquals(8, num, "getNumberOfPlayers did not return boundary value.");
        fixPipe();

        // Now test output
        assertEquals(expectedStr, fakeOut.toString(), "Expected output mismatch.");
    }
}