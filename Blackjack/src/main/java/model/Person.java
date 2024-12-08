package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a person (player or dealer) in the Blackjack game.
 * A person has a hand of cards and a name.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
@Getter
@Setter // REQ-004: Throughout the project, non-record classes use appropriate annotations from the Lombok specification wherever possible
public abstract class Person {
    // Variables
    private Hand hand;
    private String name;

    /**
     * Constructs a new person with an empty hand and an empty name.
     */
    public Person() {
        hand = new Hand();
        this.name = "";
    }
}
