package com.teamkoala;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the GameController class.
 *
 * @version Lab 6
 */
class GameControllerTest {
    /**
     * Tests an invalid returns to GameController.
     */
    @Test
    void invalid() {
        GameController ctrl = fromArrays(new int[]{ 2, 3, 1, 1, 1, 1 }, new boolean[]{ true, true, false, false }, new int[]{ -1, 6 }, new int[]{ -2, 6 });

        assertTrue(ctrl.process(), "GameController did not correctly error when asked to draw from empty pile.");
        assertTrue(ctrl.process(), "GameController did not correctly error when given invalid draw decision.");
        assertTrue(ctrl.process(), "GameController did not correctly error when given too small a hand index.");
        assertTrue(ctrl.process(), "GameController did not correctly error when given too big a hand index.");
        assertTrue(ctrl.process(), "GameController did not correctly error when given too small a flip index.");
        assertTrue(ctrl.process(), "GameController did not correctly error when given too big a flip index.");
    }

    /**
     * Tests the draw, discard, and keep features.
     *
     * Also, incidentally tests exit feature.
     */
    @Test
    void drawCards() throws NoSuchFieldException, IllegalAccessException {
        GameController ctrl = fromArrays(new int[]{ 1, 0, 2, 0 }, new boolean[]{ false }, new int[]{ 0 }, new int[]{ -1 });

        Deck deck = getField(ctrl, "deck");
        Player[] players = getField(ctrl, "players");
        PlayingCards firstCard = players[0].hand[0][0];
        firstCard.setFaceUp();

        assertFalse(ctrl.process(), "GameController does not exit when prompted to.");

        // 52 - 1 (drawn) - 18 (dealt) = 33
        assertEquals(33, deck.stockSize(), "GameController drew too many cards.");
        assertEquals(1, deck.discardSize(), "GameController did not correctly discard card.");

        setField(ctrl, "activePlayer", 0);
        setField(ctrl, "running", true);

        PlayingCards toDraw = deck.peekDiscard();

        assertFalse(ctrl.process(), "GameController does not exit when prompted to.");

        assertEquals(33, deck.stockSize(), "GameController drew more cards.");
        assertEquals(1, deck.discardSize(), "GameController did not correctly discard or draw card.");

        assertNotEquals(firstCard, players[0].hand[0][0], "GameController did not correctly replace card.");
        assertEquals(toDraw, players[0].hand[0][0], "GameController did not draw card.");
    }

    /**
     * Tests the flip after discard functionality.
     */
    @Test
    void flip() throws NoSuchFieldException, IllegalAccessException {
        GameController ctrl = fromArrays(new int[]{ 1, 1, 0 }, new boolean[]{ false }, new int[]{}, new int[]{ -1, 1, 0 });

        Player[] players = getField(ctrl, "players");
        PlayingCards firstCard = players[1].hand[0][0];
        firstCard.setFaceDown();

        PlayingCards secondCard = players[1].hand[0][1];
        secondCard.setFaceUp();

        // Note: -1 should either error for one of several reasons if not handled correctly, we can detect the throw.
        // Additionally, if GameController does not correctly retry after the second card is already flipped, it will not flip the first.
        assertFalse(ctrl.process(), "GameController did not correctly exit when testing flip.");
        assertFalse(firstCard.faceDown, "GameController did not correctly flip second player's card.");
    }

    /**
     * Generates a controller that returns from arrays.
     *
     * @param drawReturn Array to return from in drawCard().
     * @param keepReturn Array to return from in askKeep().
     * @return GameController with TestView that returns from arrays.
     */
    private GameController fromArrays(int[] drawReturn, boolean[] keepReturn, int[] replaceReturn, int[] flipReturn) {
        return new GameController(new TestView() {
            private int drawIndex = 0;
            private int keepIndex = 0;
            private int returnIndex = 0;
            private int flipIndex = 0;

            @Override
            public int drawCard(boolean stockHasCards, boolean discardHasCards) {
                int ret = drawReturn[drawIndex++];
                drawIndex %= drawReturn.length;

                return ret;
            }

            @Override
            public boolean askKeep(PlayingCards drawn) {
                boolean ret = keepReturn[keepIndex++];
                keepIndex %= keepReturn.length;

                return ret;
            }

            @Override
            public int askReplace(PlayingCards drawn) {
                int ret = replaceReturn[returnIndex++];
                returnIndex %= replaceReturn.length;

                return ret;
            }

            @Override
            public int askFlip(boolean wasFlipped) {
                int ret = flipReturn[flipIndex++];
                flipIndex %= flipReturn.length;

                return ret;
            }
        }, 3);
    }

    /**
     * Reflectively gets the member of a GameController.
     *
     * @param ctrl GameController to get the member from.
     * @param name Name of the member to get.
     * @param <T> Type of the member.
     * @return Value of the member.
     */
    @SuppressWarnings("unchecked")
    private <T> T getField(GameController ctrl, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = ctrl.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T)field.get(ctrl);
    }

    /**
     * Reflectively set the member of a GameController.
     * <br>
     * Note: Despite having specialized functions for primitives, the unspecialized version appears to work.
     * Might be a side effect of being a JVM optimized function. It probably is a JVM optimized function.
     *
     * @param ctrl GameController to set the member in.
     * @param name Name of the member to set.
     * @param value Value to set the member to.
     */
    private void setField(GameController ctrl, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = ctrl.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(ctrl, value);
    }
}
