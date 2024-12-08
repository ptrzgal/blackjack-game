package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import controller.Game;
import lombok.Getter;
import model.Card;
import model.Dealer;
import model.Deck;
import model.Player;

/**
 * The View class represents the graphical user interface (GUI) for the Blackjack game.
 * It handles the display of the game window, player actions, and updates to the game state.
 *
 * @author ptrzgal
 * @version 1.0.0.
 */
@Getter
public class View {
    // Variables
    private final JFrame window, howToPlayWindow, shortcutsWindow;
    private final JPanel mainPanel, northPanel, southPanel, centerPanel, southCenterPanel, southWestPanel, playerCardsPanel, dealerCardsPanel, cardsPanel;
    private final JButton startGameButton, hitButton, standButton, nextRoundButton, exitButton, howToPlayButton, shortcutsButton;
    private final JLabel scoreLabel, dealerHandValue, playerHandValue;
    private ArrayList<JLabel> dealerCardsLabel, playerCardsLabel;   // REQ-001: Replacing all existing tables in the program with collections
                                                                    // REQ-002: Adding a type-safe object collection to the model if it wasn't already

    /**
     * Constructs the View object and initializes the graphical interface.
     *
     * @param game      the game instance controlling the flow of the game
     * @param player    the player participating in the game
     * @param deck      the deck of cards being used in the game
     * @param discarded the deck of discarded cards
     */
    public View(Game game, Player player, Deck deck, Deck discarded) {
        // Create window
        window = new JFrame("Blackjack");
        setWindow();

        // Create How To Play window
        howToPlayWindow = new JFrame("How to Play");

        // Create Shortcuts window
        shortcutsWindow = new JFrame("Shortcuts");

        // Create main panel
        mainPanel = new JPanel();
        setMainPanels(mainPanel);
        window.add(mainPanel);

        // Create north panel
        northPanel = new JPanel();
        setMainPanels(northPanel);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Create south panel
        southPanel = new JPanel();
        setMainPanels(southPanel);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Create buttons
        startGameButton = new JButton("Start");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        nextRoundButton = new JButton("Next Round");
        exitButton = new JButton("Exit");
        howToPlayButton = new JButton("How to Play");
        shortcutsButton = new JButton("Shortcuts");
        setButtons();
        setTooltips();

        // Create center panel
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setBackground(Color.decode("#18320e"));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(startGameButton);

        // Create south center panel
        southCenterPanel = new JPanel();
        setSouthCenterPanel();
        southPanel.add(southCenterPanel, BorderLayout.CENTER);

        // Create south-west panel
        southWestPanel = new JPanel();
        setSouthWestPanel();
        southPanel.add(southWestPanel, BorderLayout.WEST);

        // Assigning an action after clicking the Start Game button
        startGameButton.addActionListener(e -> {
            initializeCards();
            game.dealCards();
        });

        // Assigning an action after clicking the Hit button
        hitButton.addActionListener(e -> {
            player.hit(deck, discarded);
            updatePlayerScreen(player, game);
            game.checkPlayerBusts();
            updateScore(game);
        });

        // Assigning an action after clicking the Stand button
        standButton.addActionListener(e -> {
           game.dealerTurn();
        });

        // Assigning an action after clicking the Next Round button
        nextRoundButton.addActionListener(e -> {
            game.dealCards();
        });

        // Assigning an action after clicking the How To Play button
        howToPlayButton.addActionListener(e -> {
            // Game rules text
            String rules = "The rules of the game will be:\n\n" +
                    "1. It will be played with a single, standard Deck of 52 cards, though you could easily change it to accommodate larger deck sizes.\n" +
                    "2. Players start with two cards and have an option to hit (draw another card) or stand (end their turn).\n" +
                    "3. Number cards are valued at their number, Face cards (Jack Queen and King) are valued at 10, and aces are valued at 1 or 11 depending on the situation.\n" +
                    "   - If the value of the hand with an ace puts it over 21, the ace is valued at 1.\n" +
                    "   - If it doesn’t, it’s 11. So, for example, a King and an ace would be 21, but one King, a 9, and an Ace would be valued at 20 (10+9+1=20).\n" +
                    "4. If the Player starts with 21, they automatically get BlackJack and win.\n" +
                    "5. If the dealer starts with 21, the player loses automatically, before they even get a chance to hit or stand.\n" +
                    "6. If they both get 21 in the end, it’s a push (a draw or tie).\n" +
                    "7. The player may stand (stop drawing) at any time. If they go over 21 they bust and lose.\n" +
                    "8. When the player stands, it’s the end of their turn, and the dealer begins drawing their third, fourth, fifth card, and so on.\n" +
                    "9. The dealer will keep drawing cards until they reach a hand valued at 17 or higher.\n\n" +
                    "Example: The dealer draws a 3 and a 7 to start, for a value of 10, then they draw a King. The total value of their hand is now 20, so they stand.\n" +
                    "If they started with a value of 10, and then drew a 3, they would keep drawing more cards until they get to 17 or higher.\n\n" +
                    "If neither player nor dealer busts or gets BlackJack, the player with the highest score wins the round.\n" +
                    "If we run out of cards, we shuffle the deck and start over.";

            // Show rules in a message dialog
            JOptionPane.showMessageDialog(
                    howToPlayWindow,
                    rules,
                    "Game Rules",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Assigning an action after clicking the Shortcuts button
        shortcutsButton.addActionListener(e -> {
            // Data for the table using a typed collection
            List<Map<String, String>> data = new ArrayList<>();
            data.add(Map.of("Action", "Start Game", "Shortcut", "Ctrl + N"));
            data.add(Map.of("Action", "Hit", "Shortcut", "H"));
            data.add(Map.of("Action", "Stand", "Shortcut", "S"));
            data.add(Map.of("Action", "Next Round", "Shortcut", "N"));
            data.add(Map.of("Action", "Exit", "Shortcut", "Esc"));
            data.add(Map.of("Action", "How to Play", "Shortcut", "F1"));
            data.add(Map.of("Action", "View Shortcuts", "Shortcut", "Ctrl + H"));

            // Extracting data for JTable
            String[][] tableData = data.stream() // REQ-007: Using stream processing of data stored in collections using lambda expressions
                    .map(row -> new String[]{row.get("Action"), row.get("Shortcut")})
                    .toArray(String[][]::new);

            // Column Headers
            String[] columnNames = {"Action", "Shortcut"};

            // Creating a table
            JTable shortcutsTable = new JTable(tableData, columnNames);
            JScrollPane scrollPane = new JScrollPane(shortcutsTable);
            shortcutsTable.setFillsViewportHeight(true);

            // Window settings
            shortcutsWindow.getContentPane().removeAll(); // Removes previous components
            shortcutsWindow.add(scrollPane);
            shortcutsWindow.setSize(400, 200);
            shortcutsWindow.setVisible(true);
        });



        // Assigning an action after clicking the Exit button
        exitButton.addActionListener(e -> System.exit(0));

        // Create Score label
        scoreLabel = new JLabel();
        setScoreLabel();
        northPanel.add(scoreLabel);

        // Create panel for cards
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(Color.decode("#18320e"));

        // Create panel for player cards
        playerCardsPanel = new JPanel();
        playerCardsPanel.setBackground(Color.decode("#18320e"));

        // Create panel for dealers cards
        dealerCardsPanel = new JPanel();
        dealerCardsPanel.setBackground(Color.decode("#18320e"));

        // Create dealer hand value label
        dealerHandValue = new JLabel("Dealer's Hand Value:");
        dealerHandValue.setForeground(Color.WHITE);

        // Create player hand value label
        playerHandValue = new JLabel("Player's Hand Value:");
        playerHandValue.setForeground(Color.WHITE);

        // Make window visible
        window.setVisible(true);
    }

    /**
     * Sets the main window properties.
     */
    public void setWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 600);
    }

    /**
     * Sets the layout and background color for the given panel.
     *
     * @param panel the panel to configure
     */
    public void setMainPanels(JPanel panel) {
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#18320e"));
    }

    /**
     * Configures the layout for the south center panel and adds action buttons.
     */
    public void setSouthCenterPanel() {
        southCenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southCenterPanel.setBackground(Color.decode("#18320e"));
        southCenterPanel.add(hitButton);
        southCenterPanel.add(standButton);
        southCenterPanel.add(nextRoundButton);
    }

    /**
     * Configures the layout for the south-west panel and adds control buttons.
     */
    public void setSouthWestPanel() {
        southWestPanel.setLayout(new BoxLayout(southWestPanel, BoxLayout.Y_AXIS));
        southWestPanel.setBackground(Color.decode("#18320e"));
        southWestPanel.add(howToPlayButton);
        southWestPanel.add(shortcutsButton);
        southWestPanel.add(exitButton);
    }

    /**
     * Sets the preferred sizes for all buttons in the interface.
     */
    public void setButtons() {
        startGameButton.setPreferredSize(new Dimension(110, 50));
        hitButton.setPreferredSize(new Dimension(110, 50));
        standButton.setPreferredSize(new Dimension(110, 50));
        nextRoundButton.setPreferredSize(new Dimension(110, 50));
        exitButton.setPreferredSize(new Dimension(110, 50));
        howToPlayButton.setPreferredSize(new Dimension(110, 50));
        shortcutsButton.setPreferredSize(new Dimension(110, 50));
    }

    /**
     * Sets tooltips for all buttons in the interface.
     */
    public void setTooltips() {
        startGameButton.setToolTipText("Start the game.");
        hitButton.setToolTipText("Take another card.");
        standButton.setToolTipText("Stop taking cards.");
        nextRoundButton.setToolTipText("Start a new round.");
        exitButton.setToolTipText("Exit the game.");
        howToPlayButton.setToolTipText("Instructions on how to play.");
        shortcutsButton.setToolTipText("View keyboard shortcuts.");
    }

    /**
     * Sets the score label text.
     */
    public void setScoreLabel() {
        scoreLabel.setText("Wins: 0 Losses: 0 Pushes: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setPreferredSize(new Dimension(110, 50));
    }

    /**
     * Shows the initial buttons at the start of the game.
     */
    public void setStartButtons() {
        startGameButton.setVisible(true);
        hitButton.setVisible(false);
        standButton.setVisible(false);
        nextRoundButton.setVisible(false);
        exitButton.setVisible(true);
        howToPlayButton.setVisible(true);
        shortcutsButton.setVisible(true);
    }

    /**
     * Displays the buttons related to the player's turn (Hit, Stand).
     */
    public void setPlayerTurnButtons() {
        startGameButton.setVisible(false);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        nextRoundButton.setVisible(false);
        exitButton.setVisible(true);
        howToPlayButton.setVisible(false);
        shortcutsButton.setVisible(false);

        hitButton.requestFocusInWindow();
        standButton.requestFocusInWindow();
        nextRoundButton.requestFocusInWindow();
        exitButton.requestFocusInWindow();
    }

    /**
     * Initializes the card labels for both the player and dealer,
     * setting them to face down and adding them to their respective panels.
     */
    public void initializeCards() {
        // Initialize dealers and players cards
        dealerCardsLabel = new ArrayList<>();
        playerCardsLabel = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            JLabel dealerCard = new JLabel(new ImageIcon(new ImageIcon(Card.IMAGE_DIR + "CardDown.png").getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_SMOOTH)));
            JLabel playerCard = new JLabel(new ImageIcon(new ImageIcon(Card.IMAGE_DIR + "CardDown.png").getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_SMOOTH)));

            dealerCardsLabel.add(dealerCard);
            playerCardsLabel.add(playerCard);

            dealerCardsPanel.add(dealerCard);
            playerCardsPanel.add(playerCard);
        }

        // Add cards to cardsPanel
        cardsPanel.add(dealerCardsPanel);
        cardsPanel.add(dealerHandValue);
        cardsPanel.add(playerCardsPanel);
        cardsPanel.add(playerHandValue);
        centerPanel.add(cardsPanel);
    }

    /**
     * Show player cards
     *
     * @param player that play game
     */
    public void printPlayerHand(Player player) {
        // Iterate through each card, update pic, hide remaining
        for (JLabel label : playerCardsLabel) { // REQ-005: Using a for-each loop
            label.setVisible(false);
        }

        for(int i = 0; i < player.getHand().getHandSize(); i++){
            String rank = player.getHand().getCard(i).rank().toString();
            String suit = player.getHand().getCard(i).suit().toString();
            String filename = rank + suit + ".png";
            playerCardsLabel.get(i).setIcon(new ImageIcon(new ImageIcon(Card.IMAGE_DIR+filename).getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_SMOOTH)));
            playerCardsLabel.get(i).setVisible(true);
        }
    }

    /**
     * Displays the dealer's first hand, with the second card face down
     * and the rest of the cards visible.
     *
     * @param dealer the dealer in the game
     */
    public void printDealerFirstHand(Dealer dealer) {
        // Iterate through each card, update pic, hide remaining
        for (JLabel label : dealerCardsLabel) {
            label.setVisible(false);
        }

        for(int i = 0; i < dealer.getHand().getHandSize(); i++){
            String rank = dealer.getHand().getCard(i).rank().toString();
            String suit = dealer.getHand().getCard(i).suit().toString();
            String filename = rank + suit + ".png";
            dealerCardsLabel.get(i).setIcon(new ImageIcon(new ImageIcon(Card.IMAGE_DIR+filename).getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_SMOOTH)));
            dealerCardsLabel.get(i).setVisible(true);
        }
        // Hide second card
        dealerCardsLabel.get(1).setIcon(new ImageIcon(new ImageIcon(Card.IMAGE_DIR+"CardDown.png").getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_DEFAULT)));
    }

    /**
     * Displays all the dealer's cards, updating their images
     * based on the current state of the dealer's hand.
     *
     * @param dealer the dealer in the game
     */
    public void printDealerHand(Dealer dealer) {
        //iterate through each card, update pic, hide remaining
        for (JLabel label : dealerCardsLabel) {
            label.setVisible(false);
        }

        for(int i = 0; i < dealer.getHand().getHandSize(); i++){
            String rank = dealer.getHand().getCard(i).rank().toString();
            String suit = dealer.getHand().getCard(i).suit().toString();
            String filename = rank + suit + ".png";
            dealerCardsLabel.get(i).setIcon(new ImageIcon(new ImageIcon(Card.IMAGE_DIR+filename).getImage().getScaledInstance(Card.CARD_WIDTH, Card.CARD_HEIGHT, Image.SCALE_SMOOTH)));
            dealerCardsLabel.get(i).setVisible(true);
        }
    }

    /**
     * Updates the player screen with the current game state.
     *
     * @param player the player whose screen is being updated
     * @param game   the game instance controlling the flow
     */
    public void updatePlayerScreen(Player player, Game game) {
        playerHandValue.setText("Player's hand value: " + player.getHand().calculateValue());
        printPlayerHand(player);
        scoreLabel.setText("Wins: " + game.getWins() + " Losses: " + game.getLosses() + " Pushes: " + game.getPushes());
    }

    /**
     * Updates the dealer screen with the current game state.
     *
     * @param dealer whose screen is being updated
     * @param game instance controlling the flow
     */
    public void updateDealerScreen(Dealer dealer, Game game) {
        dealerHandValue.setText("Dealer's hand value: " + dealer.getHand().calculateValue());
        printDealerHand(dealer);
        scoreLabel.setText("Wins: " + game.getWins() + " Losses: " + game.getLosses() + " Pushes: " + game.getPushes());
    }

    /**
     * Updates the score display with the current number of wins, losses, and pushes.
     *
     * @param game the game instance providing the updated scores
     */
    public void updateScore(Game game) {
        scoreLabel.setText("Wins: " + game.getWins() + " Losses: " + game.getLosses() + " Pushes: " + game.getPushes());
    }

    /**
     * Updates the dealer's hand value display to show "?" indicating
     * that one of the dealer's cards is face down.
     */
    public void updateFirstDealerHand() {
        dealerHandValue.setText("Dealer's hand value: ?");
    }

    /**
     * Displays the buttons related to the end game turn.
     */
    public void setEndGameButtons() {
        startGameButton.setVisible(false);
        hitButton.setVisible(false);
        standButton.setVisible(false);
        nextRoundButton.setVisible(true);
        exitButton.setVisible(true);
        howToPlayButton.setVisible(false);
        shortcutsButton.setVisible(false);
    }

    /**
     * Show window with result of round
     *
     * @param result the message about who win
     */
    public void showResultWindow(String result) {
        JOptionPane.showMessageDialog(null, result);
    }

}
