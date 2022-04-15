package com.teamkoala;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a player of the game.
 *
 * @version 1.0
 */
public class Player {
    /**
     * Declaring variables to store information
     */
    public PlayingCards[][] hand;

    /**
     * Constructor to initialize a player. The player has a hand of two rows of three cards.
     *
     * @param deck The deck to draw the hand from.
     **/
    public Player(Deck deck) {
        this.hand = new PlayingCards[2][3];
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 3; c++) {
                hand[r][c] = deck.drawCard(); //This is just to make it work
            }

        }
        int row;
        int col;
        for (int i = 0; i < 2; i++) {
            do {
                Random rand = new Random();
                row = rand.nextInt(2);
                col = rand.nextInt(3);
            } while (!hand[row][col].faceDown);
            hand[row][col].setFaceUp();
        }
    }

    /**
     * Returns the hand as a string.
     *
     * @return The hand represented as a string.
     */
    public String handAsString() {
        StringBuilder out = new StringBuilder();
        for (int r = 0; r < 2; r++) {
            out.append("Row ").append(r + 1).append(": ");

            for (int c = 0; c < 3; c++) {
                out.append(hand[r][c]);

                if (r != 1 || c != 2)
                    out.append("; ");
            }
        }

        return out.toString();
    }
}

