package model;

/**
 * Represents a player in the Blackjack game, extending from the Person class.
 * The player has a name and a hand of cards, and can perform actions like drawing cards (hitting).
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
public class Player extends Person {

    /**
     * Constructs a new Player with the given name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        super.setName(name);
    }

    /**
     * The player takes a card from the deck.
     * If there are no cards left in the deck, it reloads the deck from the discard pile.
     *
     * @param deck the deck from which the player will draw a card
     * @param discard the discard pile used to reload the deck if necessary
     * @throws RuntimeException if the deck is empty and cannot be reloaded from the discard pile
     */
    public void hit(Deck deck, Deck discard) {
        // If there's no cards left in the deck
        if (!deck.hasCards()) {
            deck.reloadDeckFromDiscard(discard);
        }

        try {
            this.getHand().takeCardFromDeck(deck);
        } catch (Deck.EmptyDeckException e) {
            throw new RuntimeException(e);
        }
    }
}
