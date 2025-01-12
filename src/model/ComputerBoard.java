package model;

import java.awt.Color;
import java.awt.Dimension;
import controller.Controller;

/**
 * The ComputerBoard class represents the computer's game board. It extends the Board class
 * and provides methods specific to the computer's board.
 * It handles the addition of coordinate buttons and the ability to show the solution by exposing
 * the coordinates occupied by ships.
 * 
 * @author [Author]
 */
public class ComputerBoard extends Board {

    /**
     * Constructs a ComputerBoard object with the specified dimension, color set, name, and controller.
     * 
     * @param dimension  the dimension of the board
     * @param colorSet   the color set for the board
     * @param name       the name of the board
     * @param masterMind the controller for the board
     */
    public ComputerBoard(int dimension, Color[] colorSet, String name, Controller masterMind) {
        super(dimension, colorSet, name, masterMind);
    }

    /**
     * Adds a coordinate button to the inner board panel.
     *
     * @param row        the row index
     * @param col        the column index
     * @param buttonSize the size of the button
     */
    public void addCoordinate(int row, int col, int buttonSize) {
        Coordinate coordinate = new Coordinate(row, col, colorSet);
        coordinatesArray[row][col] = coordinate;
        coordinatesMap.put(coordinate.getName(), coordinate);
        coordinate.setPreferredSize(new Dimension(buttonSize, buttonSize));
        coordinate.addActionListener(controller);
        innerBoard.add(coordinate);
    }

    /**
     * Exposes the coordinates occupied by ships on the computer's board by marking them as hit.
     * This method is used to show the solution.
     */
    public void showSolution() {
        for (Ship ship : getShips()) {
            for (Coordinate coordinate : ship.getCoordinates()) {
                coordinate.setHit();
            }
        }
        revalidate();
        repaint();
    }

}
