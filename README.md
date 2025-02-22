# Blackjack Game

## Project Description

Blackjack Game is a Java-based application implementing the classic Blackjack card game. The project follows the MVC (Model-View-Controller) architecture, where the game logic is handled in Java.

## Technologies

- **Java (JDK 11+)** – Core game logic and object-oriented design.
- **Swing (Java GUI)** – Graphical user interface for player interaction.

## Architecture

The application consists of three main components:

1. **Model (Game Logic Classes)** – Handles the core game logic, including:
   - `Card` – Represents an individual playing card.
   - `Deck` – Manages the deck of cards.
   - `Hand` – Stores the player's or dealer's hand.
   - `Person` – Base class for both `Player` and `Dealer`.
   - `Player` – Manages player-specific logic.
   - `Dealer` – Implements dealer behavior.
   - `Rank` and `Suit` – Enumerations for card ranks and suits.

2. **View (Graphical User Interface)** – Implements the game interface using Java Swing. It includes:
   - A **main window** displaying the game board.
   - **Panels** organizing different sections, such as player and dealer cards, game controls, and game information.
   - **Buttons** for user interactions (Start, Hit, Stand, Next Round, Exit, How to Play, Shortcuts).
   - **Dynamic card display** updating the screen based on the player's and dealer's moves.
   - **Popup windows** showing game rules, shortcuts, and game results.

3. **Controller (Game Flow Management)** – `Game` class handles:
   - Game state transitions.
   - User input processing.
   - Managing interactions between the Model and View.
   - Implementing keyboard shortcuts for smoother gameplay.

## Game Rules

- The player starts with two cards, and the dealer also gets two cards (one face-up, one face-down).
- The player can choose to "Hit" (draw another card) or "Stand" (keep the current hand).
- If the player's hand value exceeds 21, they bust and lose the game.
- The dealer must hit until reaching at least 17 points.
- The goal is to have a higher hand value than the dealer without exceeding 21.

## Installation and Execution

### System Requirements

- Java JDK 11+
- Any Java-compatible IDE (IntelliJ IDEA, Eclipse, NetBeans)

### Compilation and Execution

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/BlackjackGame.git
   cd BlackjackGame
2. Open the project in an IDE such as IntelliJ IDEA or Eclipse.
3. Build and run the application using the IDE's run configuration.

## Step-by-Step Application Workflow

1. **Launch the Application** – Start the Blackjack Game.  
   <p align="center">
     <img src="https://github.com/user-attachments/assets/" width="600">
   </p>

2. **Get to know the rules** - Click the "How to Play" button

3. **Check out Shortcuts** - Click the "Shortcuts" button

4. **Start First Round** - Click the "Start" button

5. **Make a decision** - Click the "Hit" or "Stand" button

6. **Wait for the round result** - After pressing the "Stand" button you will be notified about the result of the round

7. **Maybe one more round?** - Decide if you want another round. If so, press the "Next Round" button

## Author
**[ptrzgal](https://github.com/ptrzgal)** 
