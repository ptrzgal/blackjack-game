package model;

/**
 * Represents a card in the Blackjack game.
 * A card has a suit and a rank.
 *
 * @param suit the suit of the card
 * @param rank the rank of the card
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
public record Card(Suit suit, Rank rank) { // REQ-003: On the model side, extracting data and defining it as a record or records

    /**
     * The width of the card (in pixels).
     */
    public static final int CARD_WIDTH = 100;

    /**
     * The height of the card (in pixels).
     */
    public static final int CARD_HEIGHT = 145;

    /**
     * The directory where the card images are located.
     */
    public static final String IMAGE_DIR = "img/cards/";

    /**
     * Returns the value of the card based on its rank.
     *
     * @return the value of the card, corresponding to its rank (e.g., 2-10, J=10, Q=10, K=10, A=1 or 11)
     */
    public int getValue() {
        return rank.getValue();
    }
}
