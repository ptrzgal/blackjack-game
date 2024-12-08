package model;

/**
 * Represents the dealer in the Blackjack game, extending from the Person class.
 * The dealer has a fixed name ("Dealer") and performs actions like drawing cards (hitting).
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
public class Dealer extends Person {

    /**
     * Constructs a new Dealer with the name "Dealer".
     */
    public Dealer() {
        super.setName("Dealer");
    }

    /**
     * The dealer takes a card from the deck.
     * If there are no cards left in the deck, it reloads the deck from the discard pile.
     *
     * @param deck the deck from which the dealer will draw a card
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
