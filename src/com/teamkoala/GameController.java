package com.teamkoala;

import java.util.logging.Logger;

/**
 * Controls the game view and uses the appropriate models.
 *
 * @version Lab 6
 */
public class GameController implements Controller {
    final static Logger logger = Logger.getLogger("Koala-Golf");

    final View view;
    final Player[] players;
    final Deck deck;

    int activePlayer = 0;
    private boolean running = true;

    /**
     * Creates a new GameController from a given GameView and number of players.
     *
     * @param view GameView to use to display the game.
     * @param players Number of players to create for the game.
     */
    public GameController(View view, int players) {
        this.view = view;
        this.players = new Player[players];

        this.deck = new Deck(players);

        for (int i = 0; i < players; i++) {
            this.players[i] = new Player(i + 1);
        }
    }

    public void exit() {
        running = false;
    }

    /**
     * Advances to the next turn, for internal use.
     */
    private void nextTurn() {
        activePlayer++;
    }

    @Override
    public boolean process() {
        view.displayTurnStart(0, ""); // TODO: Stub
        return running;
    }
}
