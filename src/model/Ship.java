package model;

/**
 * The Ship class represents a ship in a game. It stores information about the
 * ship's length, position on the game board, health, ship coordinates, and
 * orientation.
 * 
 * Note: The ship coordinates are stored in an array.
 * 
 * The health of the ship represents the number of hits it can sustain before
 * being destroyed.
 * 
 * The ship can be placed either horizontally or vertically on the game board.
 * 
 * This class provides methods to access and modify the ship's attributes.
 * 
 * @author Gia Bao Tran - Kiet Tran
 * 
 */
public class Ship {
    private int length;
    private int row;
    private int col;
    private int health;
    private Coordinate[] shipCoordinates;
    private boolean isHorizontal;

    /**
     * Constructs a Ship object with the specified length.
     * 
     * @param length the length of the ship
     */
    public Ship(int length, boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
        this.length = length;
        this.shipCoordinates = new Coordinate[length];
    }

    /**
     * Returns the length of the ship.
     * 
     * @return the length of the ship
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the row coordinate of the ship.
     * 
     * @return the row coordinate of the ship
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column coordinate of the ship.
     * 
     * @return the column coordinate of the ship
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the health of the ship.
     * 
     * @return the health of the ship
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the ship's coordinates.
     * 
     * @return an array of coordinates representing the ship's position
     */
    public Coordinate[] getCoordinates() {
        return shipCoordinates;
    }

    /**
     * Returns whether the ship is placed horizontally.
     * 
     * @return true if the ship is horizontal, false if it is vertical
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Sets the ship's position coordinates.
     * 
     * @param coordinate the coordinate object representing the ship's position
     */
    public void setCoordinate(Coordinate coordinate) {
        shipCoordinates[health++] = coordinate;
    }

    /**
     * Decreases the health of the ship.
     */
    public void decreaseHealth() {
        health--;
    }

    /**
     * Checks if the ship is destroyed (health is 0).
     * 
     * @return true if the ship is destroyed, false otherwise
     */
    public boolean isShipDestroyed() {
        return health == 0;
    }

    /**
     * Set new status for the ships horizontal or vertical
     * 
     */
    public void switchAlignment() {
        isHorizontal = !isHorizontal;
    }
}
