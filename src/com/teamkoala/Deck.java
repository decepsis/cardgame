package com.teamkoala;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;

/**
 * Represents a playing deck.
 *
 * This stores the stock and discard piles, and can contain more than 52 cards.
 * I.E. this does not represent a 52-card deck.
 *
 * @version Lab 6
 */
public class Deck {
    static final SecureRandom seeder; // Slightly unnecessary.
    static Logger logger = Logger.getLogger("Koala-Golf");

    static {
        SecureRandom temp;

        try {
            temp = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            logger.warning("Uhhhh... no crypto-random source? Will continue with poorly seeded PRNG.");
            temp = null;
        }

        seeder = temp;
    }

    private final Random generator;

    private final ArrayList<PlayingCards> stock;
    private final ArrayDeque<PlayingCards> discard;

    /**
     * Construct a deck for a given set of players.
     *
     * For 1-4 players, a single set of 52 cards is used, doubled for 5-7.
     * @param players Number of players in the current game.
     * @throws IllegalArgumentException If players is not within [1, 8]
     */
    public Deck(int players) {
        if (players > 8)
            throw new IllegalArgumentException("more than 8 players not supported");
        if (players < 1)
            throw new IllegalArgumentException("more than 0 players required");

        if (seeder != null)
            generator = new Random(seeder.nextLong());
        else
            generator = new Random();

        final int numCards = players > 4 ? 104 : 52;
        stock = new ArrayList<>(numCards);
        discard = new ArrayDeque<>(numCards >> 1);

        addDeck();

        if (numCards > 52)
            addDeck();

        Collections.shuffle(stock, generator);
    }

    /**
     * Draws a card from the stock pile.
     *
     * @return Next card from stock.
     * @throws NoSuchElementException If stock is empty.
     */
    public PlayingCards drawCard() {
        try {
            return stock.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e);
        }
    }

    /**
     * Draws a card from the discard pile.
     *
     * @return Last discarded card.
     * @throws NoSuchElementException If discard is empty.
     */
    public PlayingCards drawDiscard() {
        return discard.pop();
    }

    /**
     * Adds a card to the discard pile.
     *
     * @param card Card to discard.
     */
    public void discard(PlayingCards card) {
        discard.push(card);
    }

    /**
     * Resets the deck.
     *
     * Adds discard cards into stock and reshuffles the deck.
     * Warning: If any cards are not discarded, they will not be shuffled.
     */
    public void reset() {
        stock.addAll(discard);
        discard.clear();

        Collections.shuffle(stock, generator);
    }

    /**
     * Returns the number of cards remaining in the stock pile.
     * @return Stock pile size.
     */
    public int stockSize() {
        return stock.size();
    }

    /**
     * Returns the number of cards remaining in the discard pile.
     * @return Discard pile size.
     */
    public int discardSize() {
        return discard.size();
    }

    /**
     * Adds an entire 52-card deck to the stock.
     */
    //TODO: PlayingCards not implemented, reevaluate after implementation.
    private void addDeck() {
        for (int suit = 1; suit < 5; suit++)
            for (int card = 1; card < 14; card++)
                stock.add(new PlayingCards(suit, card));
    }
}
