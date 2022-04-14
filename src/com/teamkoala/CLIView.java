package com.teamkoala;

import java.util.Scanner;

/**
 * CLIView which extends the View class to contain user inputs and outputs
 *
 * @version Lab 6
 */
public class CLIView implements View {
    private final Scanner input = new Scanner(System.in);

    /**
     * Shows the title screen.
     */
    @Override
    public void showStartScreen() {
        System.out.println("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~");
    }

    /**
     * Prompts for the number of players.
     *
     * @return Number of players or 0 for exit.
     */
    @Override
    public int getNumberOfPlayers(){
        int num = -1;
        while (num == -1) {
            System.out.print("Enter the number of players(min. 2 & max. 8) or 0 to exit: ");

            int num1 = input.nextInt();
            if ((num1 >= 2 && num1 <= 8) || num1 == 0) {
                num = num1;
            } else {
                System.out.println("Please enter valid number of players.");
            }
        }

        return num;
    }

    /**
     * Displays current player's turn, their current hand and the last discarded card
     * @param playerTurn
     * @param playerHand
     * @return
     */
    //Story 4
    @Override
    public int displayTurnStart(int playerTurn, String playerHand) {
        System.out.println(" Player " + playerTurn + "\'s turn. Player's hand: " + playerHand);
        //System.out.println("Last discarded card: " + deck.drawDiscard());
        return 0;
    }
}
