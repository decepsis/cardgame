package com.teamkoala;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Controls the game view and uses the appropriate models.
 *
 * @version Lab 6
 */
public class GameController implements Controller {
    private final static Logger logger = Logger.getLogger("Koala-Golf");

    private final View view;
    private final Player[] players;
    private final Deck deck;

    private int activePlayer = 0;
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

        deck = new Deck(players);

        for (int i = 0; i < players; i++) {
            this.players[i] = new Player(deck);
        }
    }

    /**
     * Advances to the next turn, for internal use.
     */
    private void nextTurn() {
        activePlayer++;
        activePlayer %= players.length;
    }

    /**
     * Performs game play loop.
     * @return If the program should continue running.
     */
    @Override
    public boolean process() {
        while (running) { // Each iteration is a turn
            PlayingCards lastDiscard = deck.discardSize() != 0 ? deck.peekDiscard() : null;
            view.displayTurnStart(activePlayer + 1, players[activePlayer].handAsString(), lastDiscard); // TODO: Stub

            PlayingCards drawn = null;
            boolean discard = false;
            boolean keep = true;

            try {
                switch (view.drawCard(deck.stockSize() > 0, deck.discardSize() > 0)) {  // TODO: Ensure stock/discard has cards.
                    case 0 -> {
                        running = false;
                        continue; // Exit
                    }
                    case 1 -> drawn = deck.drawCard();
                    case 2 -> {
                        drawn = deck.drawDiscard();
                        discard = true;
                    }
                }
            } catch (NoSuchElementException e) {
                logger.warning("View returned a value not allowed due to pile sizes.");
                return true;
            }

            if (drawn == null) {
                logger.warning("Impossible value returned from the view.");
                return true;
            }

            drawn.setFaceUp();

            if (!discard)
                keep = view.askKeep(drawn);

            if (keep) {
                final int index = view.askReplace(drawn);
                if (index > 5 || index < 0) {
                    logger.warning("Impossible value returned from the view.");
                    return true;
                }

                final int row = index / 3;
                final int col = index % 3;

                PlayingCards last = players[activePlayer].hand[row][col];
                players[activePlayer].hand[row][col] = drawn;

                last.setFaceUp();
                deck.discard(last);
            } else {
                deck.discard(drawn);

                // TODO: If discard, allow to flip over one card.
            }

            nextTurn();
        }

        return false;
    }
}
