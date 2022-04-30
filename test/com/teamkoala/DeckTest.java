package com.teamkoala;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Deck class.
 *
 * @version Lab 6
 */
public class DeckTest {
    final Deck deck = new Deck(3);
    final Logger logger = Logger.getLogger(DeckTest.class.getName());

    /**
     * Tests the Deck constructor in various boundaries.
     *
     * Boundaries: Upper bound error, lower bound error, large/small deck size.
     */
    @Test
    void decks() {
        try {
            new Deck(10);
            throw new AssertionError("Deck did not throw outside upper bound.");
        } catch (IllegalArgumentException ignored) {}

        try {
            new Deck(0);
            throw new AssertionError("Deck did not throw outside lower bound.");
        } catch (IllegalArgumentException ignored) {}

        assertEquals(52, new Deck(4).stockSize(), "New deck stock (small) is not 52 cards.");
        assertEquals(104, new Deck(5).stockSize(), "New deck stock (large) is not 104 cards.");

        // TODO Note: We cannot easily coverage test the getInstanceStrong() error. I'll look into improving it later.
    }

    /**
     * Tests the drawCard() function ensuring the deck does not return null nor equivalent cards twice (in a row.)
     */
    @Test
    void drawCard() {
        final PlayingCards card = deck.drawCard();
        assertNotNull(card, "drawCard returned null.");
        assertNotEquals(card, deck.drawCard(), "drawCard returned the same card.");
        assertTrue(card.faceDown, "drawCard returned a faceUp card.");
    }

    /**
     * Tests the drawCard() function at the error boundary (empty.)
     */
    @Test
    void drawCardEmpty() {
        final int deckSize = deck.stockSize();

        for (int i = 0; i < deckSize; i++)
            deck.drawCard();

        assertThrows(NoSuchElementException.class, deck::drawCard, "drawCard did not throw when empty.");
    }

    /**
     * Tests the drawDiscard() function by discarding a card, redrawing it, and ensuring it drew the card it discarded.
     */
    @Test
    void drawDiscard() {
        final PlayingCards discarded = deck.drawCard();
        deck.discard(deck.drawCard());
        deck.discard(discarded);

        assertEquals(discarded, deck.drawDiscard(), "drawDiscard did not draw the last discarded.");
        assertNotEquals(discarded, deck.drawDiscard(), "drawDiscard drew the same card twice in a row.");
    }

    /**
     * Tests the drawDiscard() function at the error boundary (empty.)
     */
    @Test
    void drawDiscardEmpty() {
        assertThrows(NoSuchElementException.class, deck::drawDiscard, "drawDiscard did not throw when empty.");
    }

    /**
     * Tests the peekDiscard() function, which is very similar to drawDiscard().
     */
    @Test
    void peekDiscard() {
        final PlayingCards discarded = deck.drawCard();
        deck.discard(deck.drawCard());
        deck.discard(discarded);

        assertEquals(discarded, deck.peekDiscard(), "peekDiscard did not return the last discarded card.");
        assertEquals(discarded, deck.peekDiscard(), "peekDiscard returned a different card, suggesting it removed the last one.");
    }

    /**
     * Tests the discard() function, doing, not much. Mostly for isolation.
     */
    @Test
    void discard() {
        // Technically redundant because drawDiscard() and reset() has to call the exact same series of functions.
        // Additionally, discardSize() checks for proper discard handling.
        // Really, all this does is isolate specific errors between those functions and the discard function itself.
        final PlayingCards card = deck.drawCard();
        deck.discard(card);
    }

    /**
     * Tests the reset() function.
     *
     * @see #reset(boolean)
     */
    @Test
    void reset() {
        reset(false);
    }

    /**
     * Tests the reset() function with reentry to prevent random flukes.
     *
     * @param isRetry Represents if this is a reentry.
     */
    private void reset(boolean isRetry) {
        final PlayingCards card = deck.drawCard();

        card.setFaceUp();

        deck.discard(card);

        final int before = deck.stockSize();
        final int discarded = deck.discardSize();

        deck.reset();

        assertTrue(card.faceDown, "Reset did not flip all cards down");

        final int after = deck.stockSize();

        assertEquals(before + discarded, after, "Reset did not fix stockSize, indicating it did not re-add discard.");

        final PlayingCards next = deck.drawCard();

        if (next.equals(card)) // We could use assertNotSame, but the cards needn't be persistent across resets.
            if (isRetry) // Check for fluke
                assertNotEquals(card, next, "Same card was returned after reset indicating no shuffle.");
            else
                reset(true);
    }

    /**
     * Tests the stockSize() function to ensure it correctly decrements.
     */
    @Test
    void stockSize() {
        // We know decks() tests stockSize initially.
        final int initialSize = deck.stockSize();

        deck.drawCard();
        assertEquals(initialSize - 1, deck.stockSize(), "Stock size did not decrement properly.");
    }

    /**
     * Tests the discardSize() function to ensure it correctly increments and decrements.
     */
    @Test
    void discardSize() {
        assertEquals(0, deck.discardSize());

        deck.discard(deck.drawCard());
        assertEquals(1, deck.discardSize(), "Discard size did not increment properly.");

        deck.drawDiscard();
        assertEquals(0, deck.discardSize(), "Discard size did not decrement properly.");
    }
}
