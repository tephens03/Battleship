# Battleship Game

A simple Battleship game built using Java and Swing. This application allows a player to play against a bot in a graphical user interface (GUI).

**Project Completed:** September 2023  
**Note:** This is a re-upload of the original project.

## Features
- **Play Against the Bot:** Challenge an AI bot with randomly placed ships.
- **Fully Functional GUI:** The user interface is built with Java Swing, providing an interactive grid for placing ships and making moves.
- **Randomized Bot Ships:** The bot's ships are randomized first to ensure fairness (you won't be able to see or select them).
- **Ship Alignment:** Right-click to change the alignment (horizontal or vertical) of your ships.
- **Health Gauge:** The health gauge will indicate 100% when both player and bot are ready to play.
- **Customization:** Access the Help menu to change colors for hits, missed shots, and unselected positions.
- **Theme Songs:** When the player loses, a dedicated theme song plays (bad luck). If the player wins, a cheerful theme plays (congratulations).
  
## Requirements
- Java 8 or later

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/battleship-game.git
    ```

2. Navigate to the project directory:
    ```bash
    cd battleship-game
    ```

3. Compile and run the Java application:
    ```bash
    javac Main.java
    java Main
    ```

## How to Play
1. **Start the Game:** Run the application to open the GUI.
2. **Randomized Bot Ships:** The bot’s ships are randomized first for fairness, and then you can place your ships.
3. **Place Your Ships:** Right-click on the grid to change the alignment (horizontal or vertical) of your ships. Click to place them on the grid.
4. **Health Gauge:** When both your health gauge and the bot’s health gauge reach 100%, you can start playing.
5. **Make Moves:** Click on the enemy's grid to launch an attack. The bot will attack randomly after each of your moves.
6. **Game Over:** If the player loses, a theme song for bad luck plays. If the player wins, a cheerful theme song plays.

## Help Menu
- **Change Colors:** Go to the Help menu in the MenuBar to change the colors for:
    - **Hit:** The color for cells where your attack hits a ship.
    - **Missed:** The color for cells where your attack misses.
    - **Unselected:** The color for cells that haven't been selected yet.

## Game Logic
- The game follows the classic Battleship rules:
    - Each player has a fleet of ships that are placed on a grid.
    - The goal is to guess the locations of the opponent’s ships and sink them by attacking the correct grid coordinates.
    - The game ends when one player has successfully sunk all of the opponent's ships.

## Clearing Old Class Files
To avoid cache issues with old class files, use the included Python script to delete existing artifacts
