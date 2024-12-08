import javax.swing.*;
import controller.Game;

/**
 * Main class for the Blackjack application.
 * This class initializes the game by taking a player's name as input,
 * either through a command-line argument or a GUI dialog prompt.
 *
 * @author ptrzgal
 * @version 1.0.0
 */
public class Blackjack {

    /**
     * The entry point of the application. Initializes the game with the player's name.
     * If no command-line argument is provided, prompts the user for input via a GUI dialog.
     *
     * @param args an array of command-line arguments. The first argument is expected to be the player's name.
     */
    public static void main(String[] args) {
        String fileName = "";

        // Check if a player name was provided as a command-line argument
        if (args.length != 1) {
            // Prompt the user for the player name via a GUI dialog if no or incorrect arguments are provided
            fileName = JOptionPane.showInputDialog(
                    null,
                    "Missing required parameters!\nEnter player name.",
                    "Enter parameter",
                    JOptionPane.PLAIN_MESSAGE
            );

            // If the user cancels the input dialog or provides an empty value, terminate the application
            if (fileName == null || fileName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The application has been closed.");
                System.exit(0);
            }
        } else {
            // Use the player name provided as the first command-line argument
            fileName = args[0];
        }

        // Initialize the game with the specified player name
        Game game = new Game(fileName);
    }
}