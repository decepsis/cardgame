package com.teamkoala;

public class PlayingCards {
    /**
     * Declaring variables to store information
     */
    private int suit, number;

    /**
     * Constructor to initialize a card. Suits range from 1-4 and the number ranges from 1-13
     * @param suit
     * @param number
     */
    public PlayingCards(int suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    public boolean isRed(){
        return true;
    }

    public boolean isBlack(){
        return false;
    }

    public int getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }
}
