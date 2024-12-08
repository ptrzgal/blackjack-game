package controller;

import lombok.Getter;
import view.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The Game class is the controller in the MVC (Model-View-Controller) architecture for the Blackjack game.
 * It manages the game logic, including the flow of the game, player and dealer turns, card dealing, and scoring.
 * It also handles user interface actions and key bindings.
 * The controller communicates with the model (player, dealer, deck) and updates the view accordingly.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
@Getter
public class Game {

    // Variables
    private View view;
    private Deck deck, discarded;
    private Player player;
    private Dealer dealer;
    private int wins, losses, pushes;

    /**
     * Constructs a new Game object with the specified player name.
     * Initializes the player, dealer, deck, discarded deck, and the game view.
     * Registers keyboard shortcuts and starts the game.
     *
     * @param userName the name of the player.
     */
    public Game(String userName) {
        wins = 0; losses = 0; pushes = 0;
        player = new Player(userName);
        dealer = new Dealer();
        deck = new Deck(true);
        discarded = new Deck();
        view = new View(this, player, deck, discarded);

        registerKeyBindings(view);

        deck.shuffle();
        startGame();
    }

    /**
     * Starts the game by setting up the start buttons on the view.
     */
    public void startGame() {
        view.setStartButtons();
    }

    /**
     * Deals two cards each to the player and the dealer.
     * If cards have already been dealt in previous rounds, they are discarded to the discard deck.
     * Handles the scenario when the deck runs low and needs to be reloaded.
     * If the deck is empty, it shows an error message and exits the application.
     */
    public void dealCards() {
        // If this is not the first game, we discard the cards to the discard deck.
        if (wins > 0 || losses > 0 || pushes > 0) {
            dealer.getHand().discardHandToDeck(discarded);
            player.getHand().discardHandToDeck(discarded);
        }

        // Checking if the deck has at least 4 cards
        if (deck.cardsLeft() < 4) {
            deck.reloadDeckFromDiscard(discarded);
        }

        view.updateFirstDealerHand();

        // Handling a potential exception
        try {
            // The dealer draws two cards
            dealer.getHand().takeCardFromDeck(deck);
            dealer.getHand().takeCardFromDeck(deck);

            // The player draws two cards
            player.getHand().takeCardFromDeck(deck);
            player.getHand().takeCardFromDeck(deck);

        } catch (Deck.EmptyDeckException e) {
            view.showResultWindow("Cannot deal cards. The deck is empty!");
            System.exit(0);
        }

        // View Player and Dealer Cards
        view.printPlayerHand(player);
        view.printDealerFirstHand(dealer);

        // Go to player's turn
        playerTurn();
    }

    /**
     * Initiates the player's turn by displaying the appropriate buttons and hand value.
     */
    public void playerTurn() {
        // Show only exit, hit, stand buttons
        view.setPlayerTurnButtons();

        // Show value of player first hand
        view.getPlayerHandValue().setText("Player's hand value: " + player.getHand().calculateValue());
    }

    /**
     * Handles the dealer's turn, where the dealer hits until the hand value is at least 17.
     * After the dealer finishes, the game checks for the winner and updates the score.
     */
    public void dealerTurn() {
        // Update score, print hand, and show new hand value
        view.updateDealerScreen(dealer, this);

        // Dealer hits until get value 17
        while (dealer.getHand().calculateValue() < 17) {
            dealer.hit(deck, discarded);
            view.updateDealerScreen(dealer, this);
        }
        checkWins();
        view.updateScore(this);
        endGame();
    }

    /**
     * Ends the current game round and sets the appropriate end-game buttons.
     */
    public void endGame() {
        // Make next round and exit button only visible
        view.setEndGameButtons();
    }

    /**
     * Registers keyboard shortcuts for various actions during the game.
     * These shortcuts include starting the game, hitting, standing, next round, etc.
     *
     * @param view the game view object to bind keys to.
     */
    public void registerKeyBindings(View view) {
        // Ctrl + N for Start Game
        view.getStartGameButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "startGame");
        view.getStartGameButton().getActionMap().put("startGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getStartGameButton().doClick();
            }
        });

        // 'H' for Hit
        view.getHitButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "hit");
        view.getHitButton().getActionMap().put("hit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getHitButton().doClick();
            }
        });

        // 'S' for Stand
        view.getStandButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "stand");
        view.getStandButton().getActionMap().put("stand", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getStandButton().doClick();
            }
        });

        // 'N' for Next Round
        view.getNextRoundButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "nextRound");
        view.getNextRoundButton().getActionMap().put("nextRound", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getNextRoundButton().doClick();
            }
        });

        // F1 for How to Play
        view.getHowToPlayButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "howToPlay");
        view.getHowToPlayButton().getActionMap().put("howToPlay", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getHowToPlayButton().doClick();
            }
        });

        // Ctrl + H for Shortcuts
        view.getShortcutsButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), "shortcuts");
        view.getShortcutsButton().getActionMap().put("shortcuts", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getShortcutsButton().doClick();
            }
        });

        // Esc for Exit
        view.getExitButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        view.getExitButton().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Checks if the player has bust (hand value exceeds 21).
     * If the player busts, the game ends, and the score is updated.
     */
    public void checkPlayerBusts() {
        if (player.getHand().calculateValue() > 21) {
            // Show window message "You Bust"
            view.showResultWindow("You bust!");
            // Update score
            losses++;

            endGame();
        }
    }

    /**
     * Checks the winner of the game based on the value of the dealer's and player's hands.
     * Updates the score and displays the result in a window.
     */
    public void checkWins() {
        // Show value of dealer's hand
        view.getDealerHandValue().setText("Dealer's hand value: " + dealer.getHand().calculateValue());

        // Check who wins and display the result in window
        if (player.getHand().calculateValue() == 21 && dealer.getHand().calculateValue() != 21) {
            view.showResultWindow("Blackjack!!!");
            wins++;
        } else if (dealer.getHand().calculateValue() == 21 && player.getHand().calculateValue() != 21) {
            view.showResultWindow("Dealer has blackjack.");
            losses++;
        } else if (dealer.getHand().calculateValue() > 21) {
            view.showResultWindow("Dealer Busts! You win!");
            wins++;
        } else if (dealer.getHand().calculateValue() > player.getHand().calculateValue()) {
            view.showResultWindow("Dealer wins - Higher hand");
            losses++;
        } else if (player.getHand().calculateValue() > dealer.getHand().calculateValue()) {
            view.showResultWindow("You win - Higher hand");
            wins++;
        } else {
            view.showResultWindow("Equal Value Hands - Push");
            pushes++;
        }
    }
}
