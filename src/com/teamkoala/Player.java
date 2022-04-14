package com.teamkoala;
import java.util.Random;
import java.util.Scanner;

public class Player {
    /**
     * Declaring variables to store information
     */
    public PlayingCards[][] hand;
    private int score;
    private boolean myTurn;
    private int playerNumber;
    private Random rand = new Random();
    private Deck deck = new Deck(1);
    //TODO: I need to fix this because each player needs to pull from the same deck

    /**
     * Constructor to initialize a player. The player has a hand of two rows of three cards.
     *
     * @param playerNumber Which position the player is in the game.
     **/


    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hand = new PlayingCards[2][3];
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 3; c++) {
                hand[r][c] = deck.drawCard(); //This is just to make it work
            }

        }
        int row;
        int col;
        /*for (int i = 0; i < 2; i++) {
            do {
                row = rand.nextInt(3);
                col = rand.nextInt(4);
            } while (!hand[row][col].faceDown);
            hand[row][col].setFaceUp();
        }*/

    }

}

