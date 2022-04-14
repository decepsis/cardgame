package com.teamkoala;

import java.util.Scanner;

/**
 * CLIView which extends the View class to contain user inputs and outputs
 */
public class CLIView implements View {
    private Scanner input = new Scanner(System.in);

    @Override
    public void showStartScreen() {
        System.out.println("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~");
    }

    @Override
    public int getNumberOfPlayers(){
        int num = 0;
        System.out.print("Enter the number of players(min. 2 & max. 8: ");
        int num1 = input.nextInt();
        if (num1 >= 2 && num1 <= 8 ) {
            num = num1;
        }
        else{
            System.out.println("Please enter valid number of players");
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
        System.out.println("Last discarded card: " + deck.drawDiscard());
    }
}
