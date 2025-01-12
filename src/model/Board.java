package model;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.util.*;
import controller.*;
import miscellaneous.Misc;

/**
 * The Board class represents a game board. It is an abstract class that
 * provides common functionality and properties
 * for both the player's and computer's boards.
 * It handles the creation of coordinate labels and buttons, as well as the
 * progress bar and labels.
 * Subclasses of Board must implement the addCoordinate method to add specific
 * coordinate buttons to the board.
 * 
 * @author [Author]
 */
public abstract class Board extends JPanel {
    protected boolean designMode = false;

    protected JPanel innerBoard;
    protected Map<String, Coordinate> coordinatesMap;
    protected Coordinate[][] coordinatesArray;
    protected Ship[] ships;
    protected JProgressBar progressBar;
    protected JLabel progressLabel;
    protected Coordinate button;
    protected String position;
    protected Controller controller;
    protected int dimension;
    protected Color[] colorSet;
    protected int maxHealth;
    protected MouseAdapter mouseAdapter;

    /**
     * Constructs a new Board instance with the specified dimension, colors, name,
     * and controller.
     * 
     * @param dimension  the dimension of the board
     * @param colors     an array of colors for the board
     * @param name       the name of the board
     * @param masterMind the controller for the board
     */
    public Board(int dimensionGiven, Color[] colors, String name, Controller masterMind) {
        this.colorSet = colors;
        dimension = dimensionGiven;
        int numCell = (dimension * 2) + 1;
        controller = masterMind;

        ships = new Ship[(dimension + 1) * dimension / 2];
        coordinatesMap = new HashMap<>();
        coordinatesArray = new Coordinate[numCell][numCell];

        setPreferredSize(new Dimension(Misc.WIDTH, Misc.HEIGHT));
        setBackground(Misc.COMPUTER_COLOR[2]);

        // Create the inner board panel
        innerBoard = new JPanel(new GridLayout(numCell + 1, numCell + 1));
        innerBoard.setBackground(Misc.COMPUTER_COLOR[2]);

        // Add coordinate labels and buttons to the inner board panel
        int buttonSize = Misc.WIDTH / numCell;
        for (int row = 0; row < numCell; row++) {
            for (int col = 0; col < numCell; col++) {
                if (row == 0) {
                    addAlphabetLabel(col, buttonSize, Misc.COMPUTER_COLOR[0]);
                } else if (col == 0) {
                    addNumericLabel(row, buttonSize, Misc.COMPUTER_COLOR[1]);
                } else {
                    addCoordinate(row, col, buttonSize);
                }
            }
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Create and configure the progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Enable percentage text
        progressBar.setValue(0); // Set initial value
        progressBar.setPreferredSize(new Dimension(Misc.WIDTH, 20));

        // Create and configure the progress label
        progressLabel = new JLabel("0%"); // Initial label text
        progressLabel.setForeground(Color.white);
        progressLabel.setPreferredSize(new Dimension(Misc.WIDTH, 20));
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create and configure the player label
        JLabel playerLabel = new JLabel(name, JLabel.CENTER);
        playerLabel.setVerticalAlignment(JLabel.BOTTOM);
        playerLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        playerLabel.setForeground(Color.white);

        // Add the components to the board panel
        add(innerBoard);
        add(playerLabel);
        add(progressBar);
    }

    /**
     * Constructs a new Board instance with the specified dimension, colors, name,
     * and controller.
     * 
     * @param dimension  the dimension of the board
     * @param colors     an array of colors for the board
     * @param name       the name of the board
     * @param masterMind the controller for the board
     * @param designMode the controller for the board
     */
    public Board(int dimensionGiven, Color[] colors, String name, Controller masterMind, MouseAdapter mouseAdapter ){
        this.colorSet = colors;
        this.mouseAdapter = mouseAdapter;
        dimension = dimensionGiven;
        int numCell = (dimension * 2) + 1;
        controller = masterMind;

        ships = new Ship[(dimension + 1) * dimension / 2];
        coordinatesMap = new HashMap<>();
        coordinatesArray = new Coordinate[numCell][numCell];

        setPreferredSize(new Dimension(Misc.WIDTH, Misc.HEIGHT));
        setBackground(Misc.COMPUTER_COLOR[2]);

        // Create the inner board panel
        innerBoard = new JPanel(new GridLayout(numCell + 1, numCell + 1));
        innerBoard.setBackground(Misc.COMPUTER_COLOR[2]);

        // Add coordinate labels and buttons to the inner board panel
        int buttonSize = Misc.WIDTH / numCell;
        for (int row = 0; row < numCell; row++) {
            for (int col = 0; col < numCell; col++) {
                if (row == 0) {
                    addAlphabetLabel(col, buttonSize, Misc.COMPUTER_COLOR[0]);
                } else if (col == 0) {
                    addNumericLabel(row, buttonSize, Misc.COMPUTER_COLOR[1]);
                } else {
                    addCoordinate(row, col, buttonSize);
                }
            }
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Create and configure the progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Enable percentage text
        progressBar.setValue(0); // Set initial value
        progressBar.setPreferredSize(new Dimension(Misc.WIDTH, 20));

        // Create and configure the progress label
        progressLabel = new JLabel("0%"); // Initial label text
        progressLabel.setForeground(Color.white);
        progressLabel.setPreferredSize(new Dimension(Misc.WIDTH, 20));
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create and configure the player label
        JLabel playerLabel = new JLabel(name, JLabel.CENTER);
        playerLabel.setVerticalAlignment(JLabel.BOTTOM);
        playerLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        playerLabel.setForeground(Color.white);

        // Add the components to the board panel
        add(innerBoard);
        add(playerLabel);
        add(progressBar);
    }

    /**
     * Adds an alphabet coordinate label to the inner board panel.
     *
     * @param col        the column index
     * @param buttonSize the size of the button
     * @param color      the color of the label
     */
    public void addAlphabetLabel(int col, int buttonSize, Color color) {
        JLabel label = new JLabel(Character.toString(Misc.ALPHABET[col]), SwingConstants.CENTER);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(buttonSize, buttonSize));
        innerBoard.add(label);
    }

    /**
     * Adds a numeric coordinate label to the inner board panel.
     *
     * @param row        the row index
     * @param buttonSize the size of the button
     * @param color      the color of the label
     */
    public void addNumericLabel(int row, int buttonSize, Color color) {
        JLabel label = new JLabel(Integer.toString(row), SwingConstants.CENTER);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(buttonSize, buttonSize));
        innerBoard.add(label);
    }

    /**
     * Adds a coordinate button to the inner board panel.
     *
     * @param row        the row index
     * @param col        the column index
     * @param buttonSize the size of the button
     */
    public abstract void addCoordinate(int row, int col, int buttonSize);

    /**
     * Retrieves the ships on the board.
     *
     * @return an array of Ship objects representing the ships on the board
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * Retrieves the Coordinate object associated with the specified coordinate
     * name.
     *
     * @param coordinateName the name of the coordinate
     * @return the Coordinate object associated with the name
     */
    public Coordinate getCoordinate(String coordinateName) {
        return coordinatesMap.get(coordinateName);
    }

    /**
     * Retrieves the map of coordinate names to Coordinate objects.
     *
     * @return the map of coordinate names to Coordinate objects
     */
    public Map<String, Coordinate> getCoordinateMap() {
        return coordinatesMap;
    }

    /**
     * Sets a ship at the specified index with the given length.
     *
     * @param index  the index of the ship
     * @param length the length of the ship
     */
    public void setShip(int index, int length, boolean horizontal) {

        ships[index] = new Ship(length, horizontal);
    }

    /**
     * Retrieves the array of Coordinate objects representing the coordinates on the
     * board.
     *
     * @return the array of Coordinate objects
     */
    public Coordinate[][] getCoordinateArray() {
        return coordinatesArray;
    }

    /**
     * Retrieves the ship count on the board.
     *
     * @return the ship count
     */
    public int getShipCount() {
        return ships.length;
    }

    /**
     * Sets the health value of the board's progress bar.
     *
     * @param health the health value to set
     */
    public void setHealth(int health) {
        progressBar.setValue(health);
    }

}
