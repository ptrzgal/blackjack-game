package model;

import java.util.ArrayList;

/**
 * Represents a hand of cards in the Blackjack game.
 * A hand contains a collection of cards and methods for calculating its value,
 * adding cards, and managing the hand's state.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
public class Hand {
    // Variables
    private ArrayList<Card> hand;

    /**
     * Constructs an empty hand of cards.
     */
    public Hand() {
        hand = new ArrayList<>();
    }

    /**
     * Takes a card from the deck and adds it to the hand.
     *
     * @param deck the deck from which to take the card
     * @throws Deck.EmptyDeckException if the deck is empty and cannot provide a card
     */
    public void takeCardFromDeck(Deck deck) throws Deck.EmptyDeckException {
        hand.add(deck.takeCard());
    }

    /**
     * Calculates and returns the total value of the cards in the hand.
     * If the hand contains an Ace (value of 11), it will adjust the value
     * if the total exceeds 21, converting some Aces to 1 until the value is 21 or less.
     *
     * @return the calculated value of the hand, considering any adjustments for Aces
     */
    public int calculateValue() {
        // Variables
        int value = 0;
        int aceCount = 0;

        // For each card in this hand
        for(Card card: hand) {
            // Add the card value to the hand
            value += card.getValue();
            // Count how many Aces have been added
            if (card.getValue() == 11){
                aceCount ++;
            }
        }

        // Adjust for Aces if the total value exceeds 21
        if (value > 21 && aceCount > 0){
            while(aceCount > 0 && value > 21){
                aceCount--;
                value -= 10;
            }
        }
        return value;
    }

    /**
     * Discards all the cards in the hand to the provided discard deck.
     * After discarding, the hand is cleared.
     *
     * @param discardDeck the deck to which the cards from the hand will be discarded
     */
    public void discardHandToDeck(Deck discardDeck){
        // Copy cards from hand to discardDeck
        discardDeck.addCards(hand);
        // Clear the hand
        hand.clear();
    }

    /**
     * Gets the card at the specified index in the hand.
     *
     * @param index the index of the card to retrieve
     * @return the card at the specified index
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return the number of cards in the hand
     */
    public int getHandSize() {
        return hand.size();
    }
}
