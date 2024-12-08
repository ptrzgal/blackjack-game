package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a deck of cards used in the Blackjack game.
 * The deck can be shuffled, have cards drawn from it, and be reloaded from a discard pile.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
@Getter
public class Deck {
    private ArrayList<Card> deck;

    /**
     * Constructs an empty deck of cards.
     */
    public Deck() {
        deck = new ArrayList<>();
    }

    /**
     * Constructs a deck of cards with all possible suits and ranks.
     *
     * @param ifMakeDeck determines whether to populate the deck with cards.
     *                    If true, the deck is populated with all combinations of suits and ranks.
     */
    public Deck(boolean ifMakeDeck) {
        deck = new ArrayList<>();

        if (ifMakeDeck) {
            // Go through all the suits
            for(Suit suit : Suit.values()){
                // Go through all the ranks
                for(Rank rank : Rank.values()){
                    // Add a new card containing each iteration's suit and rank
                    deck.add(new Card(suit, rank));
                }
            }
        }
    }

    /**
     * Adds a list of cards to the deck.
     *
     * @param cards the list of cards to add to the deck
     */
    public void addCards(ArrayList<Card> cards){
        deck.addAll(cards);
    }

    /**
     * Shuffles the cards in the deck using a randomizer.
     */
    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    /**
     * Takes the top card from the deck and removes it.
     *
     * @return the card taken from the deck
     * @throws EmptyDeckException if the deck is empty and no card can be drawn
     */
    public Card takeCard() throws EmptyDeckException {
        if (deck.isEmpty()) {
            throw new EmptyDeckException("The deck is empty. No more cards can be drawn.");
        }
        // Take a copy of the first card from the deck
        Card cardToTake = deck.getFirst();
        deck.removeFirst();  // Remove the first card
        return cardToTake;
    }

    /**
     * Checks if there are any cards left in the deck.
     *
     * @return true if there are cards left in the deck, false otherwise
     */
    public boolean hasCards(){
        return !deck.isEmpty();
    }

    /**
     * Clears the deck, removing all cards.
     */
    public void emptyDeck(){
        deck.clear();
    }

    /**
     * Reloads the deck from the discard pile and shuffles the cards.
     *
     * @param discard the deck representing the discard pile
     */
    public void reloadDeckFromDiscard(Deck discard){
        this.addCards(discard.getDeck());
        this.shuffle();
        discard.emptyDeck();
    }

    /**
     * Returns the number of cards left in the deck.
     *
     * @return the number of remaining cards in the deck
     */
    public int cardsLeft(){
        return deck.size();
    }

    /**
     * Exception thrown when an attempt is made to draw a card from an empty deck.
     */
    public class EmptyDeckException extends Exception {
        public EmptyDeckException(String message) {
            super(message);
        }
    }

}
