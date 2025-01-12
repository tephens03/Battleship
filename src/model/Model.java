package model;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import controller.Controller;
import miscellaneous.Misc;

/**
 * The Model class represents the game logic and state of the Battleship game.
 * It contains methods for generating ships, checking the suitability of
 * coordinates for ship placement, randomly placing ships, receiving shots,
 * updating colors, tracking ship counts, and updating health status.
 * 
 * The model interacts with the controller to handle user input and update the
 * view.
 * 
 * The model is responsible for maintaining the game state and providing
 * methods to manipulate the state and perform game-related operations.
 * 
 * The model uses the PlayerBoard and ComputerBoard classes to represent the
 * game boards and ships.
 * 
 * The model also defines colors and uses the miscellaneous class to store
 * various constants.
 * 
 * @author Gia Bao Tran - Kiet Tran
 */
public class Model {

    private PlayerBoard playerBoard;
    private ComputerBoard computerBoard;
    private int dimension, playerShipCount, computerShipCount, maxShipCount;
    private Color[] colorSet = { Misc.UNSELECTED_COLOR, Misc.HIT_COLOR, Misc.MISSED_COLOR };
    private Controller controller;
    private Ship currentShip;
    private boolean isAppropriateForShip;

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        public void mouseEntered(MouseEvent e) {
            Coordinate coordinate = (Coordinate) e.getSource();
            int row = coordinate.getRow();
            int column = coordinate.getColumn();

            int shipStart, shipEnd;
            if (currentShip.isHorizontal()) {
                shipStart = column;
                shipEnd = column + currentShip.getLength();
            } else {
                shipStart = row;
                shipEnd = row + currentShip.getLength();
            }

            for (int i = shipStart; i < shipEnd; i++) {

                String coordinateName = Misc.ALPHABET[currentShip.isHorizontal() ? i : column] + ""
                        + (currentShip.isHorizontal() ? row : i);
                Coordinate tempCoordinate = playerBoard.getCoordinate(coordinateName);

                if (tempCoordinate == null || tempCoordinate.isOccupied()) {
                    for (int j = shipStart; j < i; j++) {
                        String coordinateName_1 = Misc.ALPHABET[currentShip.isHorizontal() ? j : column] + ""
                                + (currentShip.isHorizontal() ? row : j);
                        playerBoard.getCoordinate(coordinateName_1).setBackground(Color.RED);
                    }
                    isAppropriateForShip = false;
                    return;
                }
                tempCoordinate.setHover();
            }
            isAppropriateForShip = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Coordinate coordinate = (Coordinate) e.getSource();
            int row = coordinate.getRow();
            int column = coordinate.getColumn();

            int shipStart, shipEnd;

            if (currentShip.isHorizontal()) {
                shipStart = column;
                shipEnd = column + currentShip.getLength();
            } else {
                shipStart = row;
                shipEnd = row + currentShip.getLength();
            }

            if (!coordinate.isOccupied()) {
                for (int i = shipStart; i < shipEnd; i++) {
                    String coordinateName = Misc.ALPHABET[currentShip.isHorizontal() ? i : column] + ""
                            + (currentShip.isHorizontal() ? row : i);
                    Coordinate tempCoordinate = playerBoard.getCoordinate(coordinateName);
                    if (tempCoordinate != null && !tempCoordinate.isOccupied()) {
                        tempCoordinate.setBackground(Misc.UNSELECTED_COLOR);
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            Coordinate coordinate = (Coordinate) e.getSource();
            if (e.getButton() == MouseEvent.BUTTON3) {
                int row = coordinate.getRow();
                int column = coordinate.getColumn();
                int shipStart = currentShip.isHorizontal() ? column : row;
                int shipEnd = shipStart + currentShip.getLength();

                for (int i = shipStart + 1; i < shipEnd; i++) {
                    String coordinateName = Misc.ALPHABET[currentShip.isHorizontal() ? i : column] + "" +
                            (currentShip.isHorizontal() ? row : i);
                    Coordinate tempCoordinate = playerBoard.getCoordinate(coordinateName);

                    if (tempCoordinate != null && !tempCoordinate.isOccupied()) {
                        System.out.println(coordinateName);
                        tempCoordinate.setBackground(Misc.UNSELECTED_COLOR);
                    }
                }

                currentShip.switchAlignment();
                shipStart = currentShip.isHorizontal() ? column : row;
                shipEnd = shipStart + currentShip.getLength();

                for (int i = shipStart; i < shipEnd; i++) {
                    String coordinateName = Misc.ALPHABET[currentShip.isHorizontal() ? i : column] + "" +
                            (currentShip.isHorizontal() ? row : i);
                    Coordinate tempCoordinate = playerBoard.getCoordinate(coordinateName);

                    if (tempCoordinate == null || tempCoordinate.isOccupied()) {
                        for (int j = shipStart; j < i; j++) {
                            String coordinateName_1 = Misc.ALPHABET[currentShip.isHorizontal() ? j : column] + "" +
                                    (currentShip.isHorizontal() ? row : j);
                            playerBoard.getCoordinate(coordinateName_1).setBackground(Color.RED);
                        }
                        isAppropriateForShip = false;
                        return;
                    }
                    tempCoordinate.setHover();
                }

            } else if (e.getButton() == MouseEvent.BUTTON1) {
                if (isAppropriateForShip) {
                    try {
                        setShip(playerBoard, currentShip, coordinate.getRow(), coordinate.getColumn());
                        currentShip = playerBoard.getShip(++playerShipCount);
                    } catch (Exception ex) {
                        endDesignMode();
                    }
                }
            }

        }

    };

    /**
     * Constructs a Model object with default dimension and boards.
     */
    public Model() {
        dimension = 1;
        setNewBoards();
    }

    /**
     * Generates ships for the specified board based on the dimension.
     * 
     * @param board the Board object to generate ships for
     */
    public void generateShip(Board board) {
        int index = 0;
        Random rand = new Random();
        for (int i = dimension; i >= 1; i--) {
            for (int j = 0; j < dimension - i + 1; j++) {
                board.setShip(index, i, rand.nextBoolean());
                index++;
            }
        }
        playerShipCount = computerShipCount = maxShipCount = board.getShipCount();
    }

    /**
     * Checks if the specified range of coordinates is suitable for placing a ship
     * of the given length.
     *
     * @param board        the board on which the ship is being placed
     * @param row          the row index of the starting coordinate
     * @param col          the column index of the starting coordinate
     * @param length       the length of the ship
     * @param isHorizontal true if the ship should be placed horizontally, false if
     *                     vertically
     * @return true if the range is suitable for placing a ship of the given length,
     *         false otherwise
     */
    public boolean isSuitableForShip(Board board, Ship ship, int row, int col) {

        Coordinate[][] coordinatesArray = board.getCoordinateArray();
        if (ship.isHorizontal()) {
            if (col + ship.getLength() > coordinatesArray[row].length) {
                return false; // Range exceeds the board dimensions
            }
            for (int i = col; i < col + ship.getLength(); i++) {
                if (coordinatesArray[row][i].isOccupied()) {
                    return false; // Range contains a coordinate that is already occupied
                }
            }

        } else {
            if (row + ship.getLength() > coordinatesArray.length) {
                return false; // Range exceeds the board dimensions
            }
            for (int i = row; i < row + ship.getLength(); i++) {
                if (coordinatesArray[i][col].isOccupied()) {
                    return false; // Range contains a coordinate that is already occupied
                }
            }
        }
        return true; // Range is suitable for placing a ship of the given length
    }

    /**
     * Randomly places the ships on the board.
     *
     * @param board the Board object representing the game board
     */
    public void randomizeShip(Board board) {
        generateShip(board);
        Random rand = new Random();

        for (Ship ship : board.getShips()) {
            boolean shipPlaced = false;
            while (!shipPlaced) {
                int randRow = 1 + rand.nextInt(2 * dimension);
                int randCol = 1 + rand.nextInt(2 * dimension);
                if (isSuitableForShip(board, ship, randRow, randCol)) {
                    setShip(board, ship, randRow, randCol);
                    break;
                }
            }
        }

        updateHealth(board);
    }

    /**
     * Randomly places the ships on both the computer and player boards.
     */
    public void randomizeShips() {
        setNewBoards();
        randomizeShip(computerBoard);
        randomizeShip(playerBoard);
    }

    /**
     * Sets the ships on the board based on the specified starting coordinate and
     * orientation.
     * 
     * @param board      the Board object on which to set the ships
     * @param ship       the Ship object to set
     * @param randRow    the row index of the starting coordinate
     * @param randCol    the column index of the starting coordinate
     * @param horizontal true if the ship should be placed horizontally, false if
     *                   vertically
     */
    public void setShip(Board board, Ship ship, int randRow, int randCol) {
        Coordinate[][] coordinatesArray = board.getCoordinateArray();
        int length = ship.getLength();
        if (ship.isHorizontal()) {
            for (int i = randCol; i < randCol + length; i++) {
                coordinatesArray[randRow][i].setOccupied(length);
                ship.setCoordinate(coordinatesArray[randRow][i]);
            }
        } else {
            for (int i = randRow; i < randRow + length; i++) {
                coordinatesArray[i][randCol].setOccupied(length);
                ship.setCoordinate(coordinatesArray[i][randCol]);

            }
        }
    }

    /**
     * Receives a shot on the specified board at the given coordinate.
     * 
     * @param board      the Board object to receive the shot on
     * @param coordinate the Coordinate object representing the shot coordinate
     * @return true if the shot hits a ship, false otherwise
     */
    public boolean receiveShot(Board board, Coordinate coordinate) {
        for (Ship ship : board.getShips()) {
            int hit = Arrays.binarySearch(ship.getCoordinates(), coordinate);
            if (hit >= 0) {
                coordinate.setHit();
                ship.decreaseHealth();
                if (ship.isShipDestroyed()) {
                    destroyShip(board, ship);
                }
                return true;
            }
        }
        coordinate.setMissed();
        return false;
    }

    /**
     * Retrieves the player board.
     * 
     * @return the PlayerBoard object representing the player's board
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Retrieves the computer board.
     * 
     * @return the ComputerBoard object representing the computer's board
     */
    public ComputerBoard getComputerBoard() {
        return computerBoard;
    }

    /**
     * Sets the hit state for the specified coordinate.
     * 
     * @param coordinate the Coordinate object to set the hit state for
     */
    public void setHit(Coordinate coordinate) {
        coordinate.setHit();
    }

    /**
     * Sets the new hit color for the game.
     * 
     * @param color the new hit color
     */
    public void setNewHitColor(Color color) {
        colorSet[1] = color;
        for (int row = 0; row < (dimension * 2) + 1; row++) {
            for (int col = 0; col < (dimension * 2) + 1; col++) {
                if (row != 0 && col != 0) {
                    String coordinateName = Misc.ALPHABET[row] + Integer.toString(col);
                    try {
                        playerBoard.getCoordinate(coordinateName).setHitColor(color);
                        computerBoard.getCoordinate(coordinateName).setHitColor(color);
                    } catch (Exception e) {
                        System.out.println("An error occurred while setting the hit color.");
                    }
                }
            }
        }
    }

    /**
     * Sets the missed state for the specified coordinate.
     * 
     * @param coordinate the Coordinate object to set the missed state for
     */
    public void setMissed(Coordinate coordinate) {
        coordinate.setMissed();
    }

    /**
     * Sets the new missed color for the game.
     * 
     * @param color the new missed color
     */
    public void setNewMissedColor(Color color) {
        colorSet[2] = color;
        for (int row = 0; row < (dimension * 2) + 1; row++) {
            for (int col = 0; col < (dimension * 2) + 1; col++) {
                if (row != 0 && col != 0) {
                    String coordinateName = Misc.ALPHABET[row] + Integer.toString(col);
                    try {
                        playerBoard.getCoordinate(coordinateName).setMissedColor(color);
                        computerBoard.getCoordinate(coordinateName).setMissedColor(color);
                    } catch (Exception e) {
                        System.out.println("An error occurred while setting the missed color.");
                    }
                }
            }
        }
    }

    /**
     * Sets the new unselected color for the game.
     * 
     * @param color the new unselected color
     */
    public void setNewUnselectedColor(Color color) {
        colorSet[0] = color;
        for (int row = 0; row < (dimension * 2) + 1; row++) {
            for (int col = 0; col < (dimension * 2) + 1; col++) {
                if (row != 0 && col != 0) {
                    String coordinateName = Misc.ALPHABET[row] + Integer.toString(col);
                    try {

                        playerBoard.getCoordinate(coordinateName).setUnselectedColor(color);
                        computerBoard.getCoordinate(coordinateName).setUnselectedColor(color);

                    } catch (Exception e) {
                        System.out.println("An error occurred while setting the unselected color.");
                    }
                }
            }
        }
    }

    /**
     * Retrieves the player's ship count.
     * 
     * @return the number of player's ships remaining
     */
    public int getPlayerShipCount() {
        return playerShipCount;
    }

    /**
     * Retrieves the computer's ship count.
     * 
     * @return the number of computer's ships remaining
     */
    public int getComputerShipCount() {
        return computerShipCount;
    }

    /**
     * Destroys the specified ship on the board.
     * 
     * @param board the Board object on which the ship is destroyed
     * @param ship  the Ship object to destroy
     */
    public void destroyShip(Board board, Ship ship) {
        if (board instanceof PlayerBoard) {
            playerShipCount--;
        } else {
            computerShipCount--;
        }

        for (Coordinate coordinate : ship.getCoordinates()) {
            coordinate.setDestroyed();
        }

        updateHealth(board);
    }

    /**
     * Calculates the health status of the specified board.
     * 
     * @param board the Board object to calculate the health for
     * @return the health percentage of the board
     */
    public int calculateHealth(Board board) {
        if (board instanceof PlayerBoard)
            return (int) ((float) playerShipCount / maxShipCount) * 100;
        else
            return (int) ((float) computerShipCount / maxShipCount) * 100;
    }

    /**
     * Sets the dimension of the game.
     * 
     * @param dimension the new dimension value
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
        setNewBoards();
    }

    /**
     * Sets new player and computer boards.
     */
    public void setNewBoards() {
        playerBoard = new PlayerBoard(dimension, colorSet, "Player", controller);
        computerBoard = new ComputerBoard(dimension, colorSet, "CPU", controller);
        playerShipCount = computerShipCount = 0;
    }

    /**
     * Sets the controller for the model.
     * 
     * @param controller the Controller object to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Retrieves the dimension of the game.
     * 
     * @return the dimension value
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Updates the health status of the specified board.
     * 
     * @param board the Board object to update the health for
     */
    public void updateHealth(Board board) {
        float health;
        if (board instanceof PlayerBoard) {
            health = ((float) playerShipCount / maxShipCount) * 100;
            playerBoard.setHealth((int) health);
        } else {
            health = ((float) computerShipCount / maxShipCount) * 100;
            computerBoard.setHealth((int) health);
        }
    }

    /**
     * Enters the design mode for the player board.
     * Creates a new player board, generates ships, and initializes the ship count
     * and current ship.
     */
    public void enterDesignMode() {
        // Create a new player board
        playerBoard = new PlayerBoard(dimension, colorSet, "Player", controller, mouseAdapter);
        playerBoard.requestFocusInWindow();

        // Generate ships on the player board
        generateShip(playerBoard);

        // Reset ship count and set the current ship
        playerShipCount = 0;
        currentShip = playerBoard.getShip(playerShipCount);
    }

    /**
     * Ends the design mode for the player board.
     * Updates the health of the player board and ends the design mode on the board.
     */
    public void endDesignMode() {
        // Update the health of the player board
        updateHealth(playerBoard);
        // End the design mode on the player board
        playerBoard.endDesignMode();
    }

    /**
     * 
     * @return a boolean indicating the game can start or continue or not
     */
    public boolean canPlay() {
        return playerShipCount > 0 && computerShipCount > 0;
    }

    public void showSolution() {
        computerBoard.showSolution();
    }

}
