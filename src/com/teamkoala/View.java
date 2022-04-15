package com.teamkoala;

/**
 * Represents a View.
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
     * @return 1 for stock, 2 for discard, 0 for exit.
     */
    // Story 5
    int drawCard();

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
}