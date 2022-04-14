package com.teamkoala;

import java.util.Scanner;

/**
 * CLIView which extends the View class to contain user inputs and outputs
 */
public class CLIView extends View {
    public PlayingCards pc;
    private Deck deck;
    private Player player;
    private Scanner input = new Scanner(System.in);

    @Override
    void showStartScreen() {
        System.out.println("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~");
    }

    @Override
    int getNumberOfPlayers(){
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
    public void displayTurnStart(int playerTurn, String playerHand) {
        System.out.println(" Player " + playerTurn + "\'s turn. Player's hand: " + playerHand);
        System.out.println("Last discarded card: " + deck.drawDiscard());
    }

    // Story 5
    @Override
    public void drawCard() {
        int temp;
        System.out.println("Press 1 to draw a card from the draw pile or Press 2 to draw the last discarded card");
        temp = input.nextInt();
        if (temp == 1) {
            // show card
            PlayingCards newCard = deck.drawCard();
            System.out.println("The card drawn from the deck is: " + newCard);
            System.out.println("Press 1 to keep the card or Press 2 to discard it");
            int temp2 = input.nextInt();
            if (temp2 == 1) {
                System.out.println("Select the row and column of the card you want replaced");
                System.out.println("Row: ");
                int row = input.nextInt();
                System.out.println("Column: ");
                int col = input.nextInt();

                player.hand[row][col] = deck.drawCard();
            }
            else {
                deck.discard(newCard);
            }
        }
        if (temp == 2) {
            // replace card
            System.out.println("Select the row and column of the card you want replaced");
            System.out.println("Row: ");
            int row = input.nextInt();
            System.out.println("Column: ");
            int col = input.nextInt();

            player.hand[row][col] = deck.drawDiscard();
        }
    }
}
