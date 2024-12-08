package model;

/**
 * Represents the suit of a card in the Blackjack game.
 * Each suit has a name (e.g., "Clubs", "Diamonds", "Hearts", "Spades").
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
public enum Suit {
    CLUB("Clubs"),
    DIAMOND("Diamonds"),
    HEART("Hearts"),
    SPADE("Spades");

    private final String name;

    /**
     * Constructs a Suit with a specified name.
     *
     * @param name the name of the suit (e.g., "Clubs", "Diamonds")
     */
    Suit(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the suit as a string.
     *
     * @return the name of the suit (e.g., "Clubs", "Diamonds")
     */
    public String toString() {
        return name;
    }
}
