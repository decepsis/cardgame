package com.teamkoala;

/**
 * Playing Card Object
 * Version: 1.0
 */
public class PlayingCards {
    private final String suit; // Variable to hold the type of suit
    private final int number; // Variable to hold the numeral value of the card
    public boolean faceDown; // Variable to see if the card is face down or not

    /**
     * Constructor for PlayingCard.
     * @param suit Suit of the card.
     * @param number Face value of the card.
     * @param faceDown If the card is face down.
     */
    public PlayingCards(String suit, int number, boolean faceDown) {
        this.suit = suit;
        this.number = number;
        this.faceDown = faceDown;
    }

    /**
     * Method to check if the suit is red
     * @return If the card suit is red.
     */
    public boolean isRed() {
        return suit.equals("Diamond") || suit.equals("Heart");
    }

    /**
     * Method to check if the suit is black
     * @return If the card suit is black.
     */
    public boolean isBlack() {
        return suit.equals("Club") || suit.equals("Spade");
    }

    /**
     * Getter for the card number
     * @return Face value of card.
     */
    public int getCardNumber(){
        return this.number;
    }

    /**
     * Setter to set the card face up
     */
    public void setFaceUp() {
        this.faceDown = false;
    }

    /**
     * Setter to set the card face down
     */
    public void setFaceDown(){
        this.faceDown = true;
    }

    /**
     * Returns true if a card is face down
     *
     * @return
     */
    public boolean isFaceDown() {
        return !faceDown;
    }

    /**
     * Method to return if a card is face card or not
     * @return If the card is a face card.
     */
    public boolean isFaceCard() {
        return number == 11 || number == 12 || number == 13;
    }

    /**
     * toString method that returns the string of a card
     * @return String value of the card.
     */
    public String toString() {
        if (this.faceDown) {
            return "Face down";
        } else {
            if (this.number == 1) {
                return "Ace of " + this.suit;
            }
            if (this.number == 11) {
                return "Jack of " + this.suit;
            }
            if (this.number == 12) {
                return "Queen of " + this.suit;
            }
            if (this.number == 13) {
                return "King of " + this.suit;
            }

            return this.number + " of " + this.suit;
        }
    }

    /**
     * Method that returns the score of the card
     * @return Game value of the card.
     */
    public int getCardScore() {
        if(this.number == 1) {
            return 1;
        }
        if(this.number == 2){
            return -2;
        }
        if(this.number >= 3 && this.number <= 10){
            return this.number;
        }
        if(this.number == 11 || this.number == 12){
            return 10;
        }
        if(this.number == 13){
            return 0;
        }
        if(this.number == 14 || this.number == 15){
            return 15;
        }

        return 0;
    }
}
