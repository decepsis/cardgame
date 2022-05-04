package com.teamkoala;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the PlayingCards class.
 *
 * @version Lab 6
 */
public class PlayingCardsTest {
    PlayingCards h6 = new PlayingCards("Heart", 6, false);
    PlayingCards ck = new PlayingCards("Club", 13, false);
    PlayingCards fd = new PlayingCards("Heart", 6, true);

    /**
     * Tests isRed() and isBlack() functions.
     */
    @Test
    void colors() {
        assertTrue(h6.isRed(), "isRed() false for hearts.");
        assertFalse(ck.isRed(), "isRed() true for clubs.");
        assertFalse(h6.isBlack(), "isBlack() true for hearts.");
        assertTrue(ck.isBlack(), "isBlack() false for clubs.");
    }

    /**
     * Tests faceDown, setFaceUp(), and setFaceDown() members.
     */
    @Test
    void faces() {
        assertTrue(fd.faceDown, "Face down card is not face down.");
        assertFalse(h6.faceDown, "Face up card is not face up.");
        assertTrue(h6.isFaceDown(), "Face up card returned face down.");

        fd.setFaceUp();
        h6.setFaceDown();

        assertFalse(fd.faceDown, "Face down card did not flip.");
        assertTrue(h6.faceDown, "Face up card did not flip.");
        assertFalse(h6.isFaceDown(), "Face up card returned face down.");
    }

    /**
     * Tests getCardNumber(), isFaceCard(), and getCardScore() functions.
     */
    @Test
    void values() {
        assertEquals(6, h6.getCardNumber(), "6 of Hearts is not numbered 6.");
        assertEquals(13, ck.getCardNumber(), "King of Clubs is not numbered 13.");

        assertFalse(h6.isFaceCard(), "6 of Hearts is a face card.");
        assertTrue(ck.isFaceCard(), "King of Clubs is not a face card.");

        int[] values = {
                0,
                1,
                -2,
                3, 4, 5, 6, 7, 8, 9, 10,
                10, 10,
                0,
                15, 15
        };
        for (int i = 0; i < 16; i++) {
            PlayingCards card = new PlayingCards("Heart", i, false);

            assertEquals(values[i], card.getCardScore(), "Card number " + i + " is not scored expectantly.");
        }
    }

    /**
     * Tests toString() function.
     */
    @Test
    void testToString() {
        assertEquals("Face down", fd.toString(), "Face down card not converted to string correctly.");

        assertEquals("6 of testsuit", new PlayingCards("testsuit", 6, false).toString(),
                "6 of testsuit not converted correctly to string.");

        PlayingCards ace = new PlayingCards("testsuit", 1, false);
        PlayingCards jack = new PlayingCards("testsuit", 11, false);
        PlayingCards queen = new PlayingCards("testsuit", 12, false);
        PlayingCards king = new PlayingCards("testsuit", 13, false);

        assertEquals("Ace of testsuit", ace.toString(), "Ace not converted correctly to string.");
        assertEquals("Jack of testsuit", jack.toString(), "Jack not converted correctly to string.");
        assertEquals("Queen of testsuit", queen.toString(), "Queen not converted correctly to string.");
        assertEquals("King of testsuit", king.toString(), "King not converted correctly to string.");
    }
}
