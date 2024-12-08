package model;

import lombok.Getter;
import lombok.ToString;

/**
 * Represents the rank of a card in the Blackjack game.
 * Each rank has a name (e.g., "Ace", "Two") and a corresponding value used for calculating the hand's total value.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
@Getter
public enum Rank {
    ACE("Ace", 11), // REQ-006: Using one of the following: enumeration type
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10);

    private final String name;
    private final int value;

    /**
     * Constructs a Rank with a specified name and value.
     *
     * @param name the name of the rank (e.g., "Ace", "Two")
     * @param value the value associated with the rank (e.g., 11 for Ace, 10 for face cards)
     */
    Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of the rank as a string.
     *
     * @return the name of the rank (e.g., "Ace", "Two")
     */
    public String toString() {return name;}
}
