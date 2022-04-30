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
        if (printer != null)
            printer.close();

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

        printer.close();
    }

    /**
     * Tests start screen output.
     */
    @Test
    void showStartScreen() {
        view.showStartScreen();

        assertEquals(String.format("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~%n"), fakeOut.toString(),
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
        int num = assertTimeoutPreemptively(Duration.ofMillis(100), view::getNumberOfPlayers, "getNumberOfPlayers ignored special value and timed out.");
        assertEquals(0, num, "getNumberOfPlayers did not return special value. May have returned out of bounds.");
        fixPipe();

        // Test boundary closure
        printer.println(2);

        num = assertTimeoutPreemptively(Duration.ofMillis(100), view::getNumberOfPlayers, "getNumberOfPlayers ignored boundary value.");
        assertEquals(2, num, "getNumberOfPlayers did not return boundary value.");
        fixPipe();

        printer.println(8);

        num = assertTimeoutPreemptively(Duration.ofMillis(100), view::getNumberOfPlayers, "getNumberOfPlayers ignored boundary value.");
        assertEquals(8, num, "getNumberOfPlayers did not return boundary value.");
        fixPipe();

        // Now test output
        assertEquals(expectedStr, fakeOut.toString(), "Expected output mismatch.");
    }

    /**
     * Tests the output of queryHoles().
     */
    @Test
    void queryHoles() {
        final String prompt = "Enter the number of holes to play: ";
        final String error = "Please enter a positive or zero number of holes." + System.lineSeparator();
        final String[] expected = { prompt, error, prompt };
        final String expectedStr = String.join("", expected);

        printer.println(-1);
        printer.println(5);

        int num = assertTimeoutPreemptively(Duration.ofSeconds(5), view::queryHoles, "queryHoles ignored valid value and timed out.");
        assertEquals(5, num, "queryHoles did not return right value.");

        assertEquals(expectedStr, fakeOut.toString(), "Expected output mismatch.");
    }

    /**
     * Tests the output of displayTurnStart().
     */
    @Test
    void displayTurnStart() {
        final String format = "Player %d's turn. Player's hand: %s%n";
        final String discardFormat = "Last discarded card: %s%n";
        final String emptyDiscard = String.format("Discard is empty%n");

        view.displayTurnStart(999, "fakehand1", null);

        PlayingCards card = new PlayingCards("Heart", 6, false);

        view.displayTurnStart(998, "fakehand2", card);

        final String[] expected = {
                String.format(format, 999, "fakehand1"),
                emptyDiscard,
                String.format(format, 998, "fakehand2"),
                String.format(discardFormat, card)
        };

        assertEquals(String.join("", expected), fakeOut.toString(), "displayTurnStart did not correctly output.");
    }

    /**
     * Tests the drawCard() I/O.
     */
    @Test
    void drawCard() throws IOException, NoSuchFieldException, IllegalAccessException {
        final String queryFull = "Press 1 to draw a card from the stock pile, 2 to draw the last discarded card, or 0 to exit.";
        final String queryStock = "Press 1 to draw a card from the stock pile or 0 to exit.";
        final String queryDiscard = "Press 2 to draw the last discarded card or 0 to exit.";
        final String queryEmpty = "There are no more cards left, we have to exit, sorry.";
        final String errorFull = "Please only input 1, 2, or 0";
        final String errorStock = "Please only input 1 or 0";
        final String errorDiscard = "Please only input 2 or 0";
        final String[] expected = { queryFull, errorFull, queryFull, queryFull, errorFull, queryStock, errorStock, queryDiscard, errorDiscard, queryEmpty, "" };

        printer.println("-1");
        printer.println("0");
        int value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(true, true), "drawCard did not return first value.");
        assertEquals(0, value, "drawCard did not correctly return first value.");

        fixPipe();

        printer.println("1");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(true, true), "drawCard did not return second value.");
        assertEquals(1, value, "drawCard did not correctly return second value.");

        fixPipe();

        printer.println("3");
        printer.println("2");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(true, true), "drawCard did not return third value.");
        assertEquals(2, value, "drawCard did not correctly return third value.");

        fixPipe();

        printer.println("2");
        printer.println("0");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(true, false), "drawCard did not return without discard.");
        assertEquals(0, value, "drawCard did not correctly return without discard.");

        fixPipe();

        printer.println("1");
        printer.println("0");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(false, true), "drawCard did not return without stock.");
        assertEquals(0, value, "drawCard did not correctly return without stock.");

        fixPipe();

        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.drawCard(false, false), "drawCard did not return without cards.");
        assertEquals(0, value, "drawCard did not correctly return without cards.");

        assertEquals(String.join(String.format("%n"), expected), fakeOut.toString(), "drawCard did not output correctly.");
    }

    /**
     * Tests the askKeep() I/O.
     */
    @Test
    void askKeep() throws IOException, NoSuchFieldException, IllegalAccessException {
        final String drawnFormat = "The card drawn from the deck is: %s";
        final String query = "Press 1 to keep the card or 2 to discard it";
        final String error = "Please only input 1 or 2.";

        final PlayingCards card1 = new PlayingCards("Heart", 6, false);
        final PlayingCards card2 = new PlayingCards("Club", 8, false);

        final String[] expected = {
                String.format(drawnFormat, card1),
                query,
                error,
                String.format(drawnFormat, card2),
                query,
                error,
                ""
        };

        printer.println("0");
        printer.println("1");
        boolean value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askKeep(card1), "drawCard did not return first value.");
        assertTrue(value, "drawCard did not correctly return first value.");

        fixPipe();

        printer.println("3");
        printer.println("2");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askKeep(card2), "drawCard did not return second value.");
        assertFalse(value, "drawCard did not correctly return second value.");

        assertEquals(String.join(String.format("%n"), expected), fakeOut.toString(), "drawCard did not output correctly.");
    }

    /**
     * Tests the askReplace() I/O.
     */
    @Test
    void askReplace() throws IOException, NoSuchFieldException, IllegalAccessException {
        final String query = "Select the row and column of the card you want replaced.";
        final String rowQuery = "Row: ";
        final String colQuery = "Column: ";
        final String rowError = "Please select a row between 1 and 2.";
        final String colError = "Please select a column between 1 and 3.";
        final String[] expected = {
                query,
                rowQuery,
                rowError,
                rowError,
                colQuery,
                colError,
                colError,
                query,
                rowQuery,
                colQuery,
                query,
                rowQuery,
                colQuery,
                ""
        };

        final PlayingCards card = new PlayingCards("Heart", 6, false);

        printer.println("0");
        printer.println("3");
        printer.println("1");
        printer.println("0");
        printer.println("4");
        printer.println("1");
        int value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askReplace(card), "askReplace did not return first value.");
        assertEquals(0, value, "askReplace did not correctly return first value.");

        fixPipe();

        printer.println("2");
        printer.println("3");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askReplace(card), "askReplace did not return second value.");
        assertEquals(5, value, "askReplace did not correctly return second value.");

        fixPipe();

        printer.println("1");
        printer.println("2");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askReplace(card), "askReplace did not return third value.");
        assertEquals(1, value, "askReplace did not correctly return third value.");

        assertEquals(String.join(String.format("%n"), expected), fakeOut.toString(), "askReplace did not output correctly.");
    }

    /**
     * Tests the askFlip() function I/O.
     */
    @Test
    void askFlip() throws IOException, NoSuchFieldException, IllegalAccessException {
        final String query = "You may flip a card over. Enter the row or 0 to skip:";
        final String colQuery = "Column: ";
        final String rowError = "Please select a row between 1 and 2.";
        final String colError = "Please select a column between 1 and 3.";
        final String wasFlipped = "That card is already flipped.";

        final String[] expected = {
                query,
                rowError,
                rowError,
                colQuery,
                colError,
                colError,
                query,
                colQuery,
                query,
                colQuery,
                wasFlipped,
                query,
                ""
        };

        final PlayingCards card = new PlayingCards("Heart", 6, false);

        printer.println("-1");
        printer.println("3");
        printer.println("1");
        printer.println("0");
        printer.println("4");
        printer.println("1");
        int value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askFlip(false), "askFlip did not return first value.");
        assertEquals(0, value, "askFlip did not correctly return first value.");

        fixPipe();

        printer.println("2");
        printer.println("3");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askFlip(false), "askFlip did not return second value.");
        assertEquals(5, value, "askFlip did not correctly return second value.");

        fixPipe();

        printer.println("1");
        printer.println("2");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askFlip(false), "askFlip did not return third value.");
        assertEquals(1, value, "askFlip did not correctly return third value.");

        fixPipe();

        printer.println("0");
        value = assertTimeoutPreemptively(Duration.ofMillis(100), () -> view.askFlip(true), "askFlip did not return the skip value.");
        assertEquals(-1, value, "askFlip did not correctly return the skip value.");

        assertEquals(String.join(String.format("%n"), expected), fakeOut.toString(), "askFlip did not output correctly.");
    }
}