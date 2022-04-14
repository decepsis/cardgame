package com.teamkoala;

import org.junit.jupiter.api.Test;

import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the TitleController behavior.
 *
 * @version Lab 6
 */
class TitleControllerTest {
    /**
     * Tests starting a game.
     */
    @Test
    void startGame() {
        TitleController ctrl = withParams(1, true);

        assertThrows(TestView.TraceException.class, ctrl::process, "TitleController did not appear pass to GameController.");
    }

    /**
     * Tests exiting.
     */
    @Test
    void exit() {
        TitleController ctrl = withParams(0, false);

        assertFalse(ctrl.process(), "TitleController did not exit properly.");
    }

    private TitleController withParams(int numPlayers, boolean throwOnGame) {
        return new TitleController(new TestView(numPlayers, throwOnGame));
    }
}
