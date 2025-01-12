package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.util.Map;

import controller.Controller;

/**
 * The PlayerBoard class represents the game board for the player. It extends
 * the
 * Board class and adds functionality specific to the player's board.
 * 
 * The player board contains coordinate buttons that the player can interact
 * with.
 * 
 * This class provides a method to add coordinate buttons to the inner board
 * panel.
 * 
 * It also inherits methods from the Board class for managing ships and
 * coordinates.
 * 
 * @author Gia Bao Tran - Kiet Tran
 */
public class PlayerBoard extends Board {


    /**
     * Constructs a PlayerBoard object with the specified dimension, color set,
     * name, and controller.
     * 
     * @param dimension  the dimension of the board
     * @param colorSet   the array of colors for the board
     * @param name       the name of the player
     * @param masterMind the controller instance for the game
     */
    public PlayerBoard(int dimension, Color[] colorSet, String name, Controller masterMind) {
        super(dimension, colorSet, name, masterMind);
    }

    /**
     * Constructs a PlayerBoard object with the specified dimension, color set,
     * name, and controller.
     * 
     * @param dimension  the dimension of the board
     * @param colorSet   the array of colors for the board
     * @param name       the name of the player
     * @param masterMind the controller instance for the game
     * @param designMode indicate if we are going to make the board interactable
     *                   with human hovers
     */

    public PlayerBoard(int dimension, Color[] colorSet, String name, Controller masterMind, MouseAdapter mouseAdapter) {
        super(dimension, colorSet, name, masterMind, mouseAdapter);
    }

    /**
     * Adds a coordinate button to the inner board panel.
     * 
     * @param row        the row index
     * @param col        the column index
     * @param buttonSize the size of the button
     */
    public void addCoordinate(int row, int col, int buttonSize) {
        Coordinate coordinate = new Coordinate(row, col, true, colorSet, mouseAdapter);
        coordinatesArray[row][col] = coordinate;
        coordinatesMap.put(coordinate.getName(), coordinate);
        coordinate.setPreferredSize(new Dimension(buttonSize, buttonSize));
        innerBoard.add(coordinate);
    }

    /**
     * This method will loop through each Coordinate, and tell them to stop
     * listening, because they are done with DESIGNING
     */
    public void endDesignMode() {
        for (Map.Entry<String, Coordinate> entry : coordinatesMap.entrySet()) {
            entry.getValue().endDesignMode();
        }
    }

    /**
     * 
     * @param index
     * @return the Ship at the desired index
     */
    public Ship getShip(int index) {
        return ships[index];
    }
}
