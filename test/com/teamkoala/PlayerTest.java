package com.teamkoala;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Tests the Player class.
 *
 * @version Lab 6
 */
public class PlayerTest {
    /**
     * Tests the Player constructor for drawing and face flipping.
     */
    @Test
    void generation() throws NoSuchFieldException, IllegalAccessException {
        Deck deck = new Deck(3);
        ArrayList<PlayingCards> stock = getStock(deck);
        PlayingCards[] cards = new PlayingCards[0];
        cards = stock.toArray(cards);

        Player player = new Player(deck);
        assertEquals(2, player.hand.length, "Invalid number of rows.");

        // I'm 99% certain asymmetrically sized arrays are impossible.
        assertEquals(3, player.hand[0].length, "Invalid number of columns.");

        int faceup = 0;
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 3; c++) {
                PlayingCards card = player.hand[r][c];
                if (!card.faceDown)
                    faceup++;

                assertEquals(cards[r * 3 + c], card, "Player did not deal in order.");
            }

        assertEquals(2, faceup, "Player did not deal 2 face up cards.");
        assertEquals(46, deck.stockSize(), "Player did not draw exactly 6 cards");
        assertEquals(0, deck.discardSize(), "Player discarded cards.");
    }

    /**
     * Reflectively gets the stock pile of a Deck.
     *
     * @param deck Deck to get the stock pile from.
     * @return Value of the member.
     */
    @SuppressWarnings("unchecked")
    private ArrayList<PlayingCards> getStock(Deck deck) throws NoSuchFieldException, IllegalAccessException {
        Field field = deck.getClass().getDeclaredField("stock");
        field.setAccessible(true);
        return (ArrayList<PlayingCards>)field.get(deck);
    }
}
