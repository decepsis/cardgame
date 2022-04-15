package com.teamkoala;

import org.junit.jupiter.api.Test;

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

        assertThrows(TraceException.class, ctrl::process, "TitleController did not appear pass to GameController.");
    }

    /**
     * Tests exiting.
     */
    @Test
    void exit() {
        TitleController ctrl = withParams(0, false);
        assertFalse(ctrl.process(), "TitleController did not exit properly.");

        ctrl = withParams(1, false);
        assertFalse(ctrl.process(), "TitleController did not exit from GameController properly.");
    }

    /**
     * Constructs a TitleController from a TestView easily for less typing.
     *
     * @param numPlayers Number of players to return to controller.
     * @param throwOnGame Whether to throw or exit when entering GameController.
     * @return New TitleController with specified view.
     */
    private TitleController withParams(int numPlayers, boolean throwOnGame) {
        return new TitleController(new TestView() {
            @Override
            public int getNumberOfPlayers() {
                return numPlayers;
            }

            @Override
            public int drawCard() {
                if (throwOnGame)
                    throw new TraceException();

                return 0;
            }
        });
    }
}
