package com.teamkoala;

/**
 * Represents a View.
 *
 * @version Lab 7
 */
public interface View {
    /**
     * Show the title screen.
     */
    void showStartScreen();

    /**
     * Query the user for number of players and return result.
     *
     * @return Number of players.
     */
    int getNumberOfPlayers();

    /**
     * Query the user for the number of holes to play.
     *
     * @return Number of holes.
     */
    int queryHoles();

    /**
     * Display the present player's turn.
     *
     * @param playerTurn Player whose turn it is.
     * @param playerHand The player's hand.
     * @param lastDiscard Last discarded card.
     */
    //Story 4
    void displayTurnStart(int playerTurn, String playerHand, PlayingCards lastDiscard);

    /**
     * Asks the player where to draw a card from.
     *
     * @param stockHasCards If the stock pile has cards (can be drawn.)
     * @param discardHasCards If the discard pile has cards (can be drawn.)
     * @return 1 for stock, 2 for discard, 0 for exit.
     */
    // Story 5
    int drawCard(boolean stockHasCards, boolean discardHasCards);

    /**
     * Asks the player which card they want to replace.
     *
     * @param drawn Card that was drawn.
     * @return 0-based index of the hand.
     */
    int askReplace(PlayingCards drawn);

    /**
     * Asks the player if they want to keep the card.
     *
     * @param drawn Card that was drawn.
     * @return If the player keeps the card.
     */
    boolean askKeep(PlayingCards drawn);

    /**
     * Asks the player if they want to flip a card over.
     *
     * @param wasFlipped If the player chose a flipped card, and we need another.
     * @return Card the player would like to flip over or -1.
     */
    int askFlip(boolean wasFlipped);
  
    /**
     * Asks the player if they want to view the scoreboard.
     *
     * @return If the player wants to view the scoreboard.
     */
    boolean viewScoreboard();

    /**
     * Shows the current scoreboard.
     *
     * @param players Current players
     */
    void showScoreboard(Player[] players, int holes, int hole);

    /**
     * Displays the winner of the game.
     *
     * @param scores The final scores.
     */
    void displayWinner(int[] scores);
}
