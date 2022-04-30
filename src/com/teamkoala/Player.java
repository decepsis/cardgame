package com.teamkoala;
import java.util.Random;

/**
 * Represents a player of the game.
 *
 * @version Lab 7
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

    /**
     * Discards the player's entire hand.
     * <br><br>
     * Note: This invalidates the player's object, continued use is dangerous.
     *
     * @param deck The deck to discard the cards into.
     */
    public void discardHand(Deck deck) {
        for (PlayingCards[] row: hand)
            for (PlayingCards card: row)
                deck.discard(card);
    }

    /**
     * Scores the player's entire hand.
     *
     * @return The hand's score.
     */
    public int scoreHand() {
        int score = 0;

        for (PlayingCards[] row: hand)
            for (PlayingCards card: row)
                score += card.getCardScore();

        return score;
    }

    /**
     * Returns score for player's current hand only for cards that are faced up
     * @return
     */
    public int scoreFaceCard() {
        int score = 0;

        for (PlayingCards[] row: hand)
            for (PlayingCards card: row)
                if (card.isFaceDown()) {
                    score += card.getCardScore();
                }
        return score;
    }

    /**
     * Just returns scoreFaceCard(). This just makes displayScores work.
     * @return
     */
    public int returnScore(){
        return scoreFaceCard();
    }
}

