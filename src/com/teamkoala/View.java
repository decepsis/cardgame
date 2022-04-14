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
     * @param player Player whose turn it is.
     * @param hand The player's hand.
     * @return Unknown value, incomplete.
     */
    int displayTurnStart(int player, String hand);
}
