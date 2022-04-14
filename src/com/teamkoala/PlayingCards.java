package com.teamkoala;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Playing Card Object
 * Version: 1.0
 */
public class PlayingCards {
    private String suit; // Variable to hold the type of suit
    private int number; // Variable to hold the numeral value of the card
    private boolean faceDown; // Variable to see if the card is face down or not
    private ArrayList<PlayingCards> deck;

    /**
     * Constructor for PlayingCard
     * @param suit
     * @param number
     * @param faceDown
     */
    public PlayingCards(String suit, int number, boolean faceDown) {
        this.suit = suit;
        this.number = number;
        this.faceDown = faceDown;
    }

    public void deckOfCards() {
        this.deck = new ArrayList<PlayingCards>(52);
        for(int i = 1; i < 14; i++){
            deck.add(new PlayingCards("Heart", i, true));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new PlayingCards("Diamond", i, true));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new PlayingCards("Spade", i, true));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new PlayingCards("Club", i, true));
        }
    }

    /**
     * Method to check if the suit is red
     * @return
     */
    public boolean isRed() {
        if (Objects.equals(suit, "Diamond") || Objects.equals(suit, "Heart")) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method to check if the suit is black
     * @return
     */
    public boolean isBlack() {
        if (Objects.equals(suit, "Diamond") || Objects.equals(suit, "Heart")) {
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Getter for the card number
     * @return
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
     * @return
     */
    public void setFaceDown(){
        this.faceDown = true;
    }

    /**
     * Method to return if a card is face card or not
     * @return
     */
    public boolean isFaceCard(){
        if(number == 11 || number == 12 || number == 13){
            return true;
        }

        return false;
    }

    /**
     * toString method that returns the string of a card
     * @return
     */
    public String toString() {
        if (this.faceDown == true) {
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
     * @return
     */
    public int getCardScore(){
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
