package model;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import miscellaneous.Misc;

/**
 * The Coordinate class represents a JButton with row and column properties.
 * It is used to represent a coordinate on the game board.
 * Each coordinate can have different states, such as hit, missed, occupied, or
 * destroyed.
 * The class provides methods to update the state and appearance of the
 * coordinate.
 * It also implements the Comparable interface for comparing coordinates based
 * on their names.
 * The coordinate's appearance is determined by different colors for hit,
 * missed, and unselected states.
 * By default, the unselected color is used.
 * The coordinate's state and appearance can be updated by calling the
 * appropriate methods.
 */
public class Coordinate extends JButton implements Comparable<Coordinate> {

    private int column;
    private int row;
    private int length;
    private boolean isMissed;
    private boolean isHit;
    private boolean isOccupied;
    private boolean isDesignMode;
    private boolean isForPlayberBoard;
    private boolean isDestroyed;
    private Color hitColor;
    private Color missedColor;
    private Color unselectedColor;
    private MouseListener mouseListener;

    /**
     * Constructs a Coordinate object with the specified row, column, and colors.
     * The coordinate is initially unoccupied, not hit or missed, and not destroyed.
     * The appearance of the coordinate is determined by the unselected color.
     *
     * @param newRow       the row value of the coordinate
     * @param newColumn    the column value of the coordinate
     * @param colors       an array of colors for hit, missed, and unselected states
     * @param mouseAdapter the MouseAdapter to handle mouse events for the
     *                     coordinate
     */
    public Coordinate(int newRow, int newColumn, boolean isForPlayberBoard, Color[] colors, MouseAdapter mouseAdapter) {
        this(newRow, newColumn, colors); // Call the other constructor
        this.mouseListener = mouseAdapter;
        this.isForPlayberBoard = isForPlayberBoard;
        addMouseListener(mouseAdapter);
    }

    /**
     * Constructs a Coordinate object with the specified row, column, and colors.
     * The coordinate is initially unoccupied, not hit or missed, and not destroyed.
     * The appearance of the coordinate is determined by the unselected color.
     *
     * @param newRow    the row value of the coordinate
     * @param newColumn the column value of the coordinate
     * @param colors    an array of colors for hit, missed, and unselected states
     */
    public Coordinate(int newRow, int newColumn, Color[] colors) {
        isOccupied = false;
        isMissed = false;
        isHit = false;
        isDestroyed = false;
        row = newRow;
        column = newColumn;
        unselectedColor = colors[0];
        hitColor = colors[1];
        missedColor = colors[2];
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(unselectedColor);
    }

    /**
     * Returns the row value of the coordinate.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column value of the coordinate.
     *
     * @return the column value
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the name of the coordinate, which combines the column letter and the
     * row number.
     *
     * @return the name of the coordinate
     */
    public String getName() {
        return Misc.ALPHABET[column] + Integer.toString(row);
    }

    /**
     * Sets the coordinate as occupied by a ship with the specified length.
     * The appearance of the coordinate is updated to reflect the occupied state.
     *
     * @param newLength the length of the ship occupying the coordinate
     */
    public void setOccupied(int newLength) {
        length = newLength;
        if (isForPlayberBoard) {
            setBackground(Misc.SHIP_COLOR);
            setText(Integer.toString(length));
        }
        setForeground(Misc.WHITE);
        setOpaque(true);
        isOccupied = true;
    }

    /**
     * Sets the hit color of the coordinate.
     * If the coordinate is hit and not destroyed, the appearance is updated with
     * the new hit color.
     *
     * @param color the new hit color
     */
    public void setHitColor(Color color) {
        hitColor = color;
        if (isHit() && !isDestroyed)
            setHit();
    }

    /**
     * Sets the missed color of the coordinate.
     * If the coordinate is missed, the appearance is updated with the new missed
     * color.
     *
     * @param color the new missed color
     */
    public void setMissedColor(Color color) {
        missedColor = color;
        if (isMissed())
            setMissed();
    }

    /**
     * Sets the unselected color of the coordinate.
     * If the coordinate is not hit, missed, or occupied, the appearance is updated
     * with the new unselected color.
     *
     * @param color the new unselected color
     */
    public void setUnselectedColor(Color color) {
        unselectedColor = color;
        if (!isMissed() && !isHit() && !isForPlayberBoard)
            setBackground(unselectedColor);
    }

    /**
     * Updates the state and appearance of the coordinate to indicate a hit.
     * The appearance is updated with the hit color.
     */
    public void setHit() {
        setBackground(hitColor);
        setText(Integer.toString(length));
        setForeground(Misc.WHITE);
        setOpaque(true);
        isHit = true;
        revalidate();
        repaint();
    }

    /**
     * Updates the state and appearance of the coordinate to indicate a missed shot.
     * The appearance is updated with the missed color.
     */
    public void setMissed() {
        setBackground(missedColor);
        setForeground(Misc.WHITE);
        setOpaque(true);
        isMissed = true;
        revalidate();
        repaint();
    }

    /**
     * Updates the state and appearance of the coordinate to indicate that the ship
     * occupying the coordinate has been destroyed.
     * The appearance is updated with the destroyed color.
     */
    public void setDestroyed() {
        setBackground(Misc.DESTROYED_COLOR);
        setForeground(Color.WHITE);
        setText("X");
        setOpaque(true);
        isDestroyed = true;
    }

    /**
     * Checks if the coordinate is occupied by a ship.
     *
     * @return true if the coordinate is occupied, false otherwise
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Checks if the coordinate has been shot (hit or missed).
     *
     * @return true if the coordinate has been shot, false otherwise
     */
    public boolean isShot() {
        return isMissed || isHit;
    }

    /**
     * Checks if the coordinate has been hit.
     *
     * @return true if the coordinate has been hit, false otherwise
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Checks if the coordinate has been missed.
     *
     * @return true if the coordinate has been missed, false otherwise
     */
    public boolean isMissed() {
        return isMissed;
    }

    /**
     * Compares this coordinate with another coordinate based on their names.
     * The comparison is case-sensitive.
     *
     * @param o the coordinate to compare with
     * @return the value 0 if the coordinates are equal, a value less than 0 if this
     *         coordinate is lexicographically less than the other coordinate,
     *         a value greater than 0 if this coordinate is lexicographically
     *         greater than the other coordinate
     */
    @Override
    public int compareTo(Coordinate o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * Sets the design mode of the coordinate.
     *
     * @param designMode true if the coordinate is in design mode, false otherwise
     */
    public void setDesignMode(boolean designMode) {
        isDesignMode = designMode;
    }

    /**
     * Ends the design mode by removing the mouse listener and updating the state.
     * This method removes the mouse listener associated with the coordinate,
     * indicating that the design mode is no longer active.
     */
    public void endDesignMode() {
        removeMouseListener(mouseListener);
        isDesignMode = false;
    }

    /**
     * Sets the background color of the coordinate to indicate a hover effect.
     * This method is typically called when the mouse pointer enters the coordinate.
     */
    public void setHover() {
        setBackground(Misc.HOVER_COLOR);
    }

    /**
     * Sets the background color of the coordinate to indicate an over-ranged state.
     * This method is typically called when the coordinate is invalid for ship
     * placement
     * due to being outside the allowed range.
     */
    public void setOverRanged() {
        setBackground(Misc.OVER_RANGED_COLOR);
    }
}
