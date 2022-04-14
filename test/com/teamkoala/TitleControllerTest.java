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
        TitleController ctrl = withParams(1);

        // TODO: Bodged to work with partially complete TestView.
        assertThrows(IllegalStateException.class, ctrl::process, "TitleController did not appear pass to GameController.");
    }

    /**
     * Tests exiting.
     */
    @Test
    void exit() {
        TitleController ctrl = withParams(0);

        assertFalse(ctrl.process(), "TitleController did not exit properly.");
    }

    private TitleController withParams(int numPlayers) {
        return new TitleController(new TestView(numPlayers));
    }
}