package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.Controller;
import model.Model;

/**
 * The View class represents the main game window. It extends the JFrame class
 * and contains panels and components for displaying the game elements.
 * 
 * The view is responsible for setting up and managing the graphical user
 * interface (GUI) components, such as menus and game boards.
 * 
 * It provides methods to set the start menu, set the game, and set up the game
 * boards. It also has methods to update and clear the history of moves.
 * 
 * The view interacts with the controller to handle user input and update the
 * model.
 * 
 * This class uses the BorderLayout to organize its components.
 * 
 * @author Gia Bao Tran - Kiet Tran
 */
public class View extends JFrame {

    private MiddleMenu middleMenu;
    private MenuBar menuBar;
    private StartMenu startMenu;
    private Controller controller;

    /**
     * Constructs a View object.
     */
    public View() {
        setTitle("Bang Bang Simulation");
        setVisible(true);
        setSize(1500, 1000);
        setResizable(false);
        getContentPane().setBackground(new Color(26, 20, 35));
        setLayout((LayoutManager) new BorderLayout());

    }

    /**
     * Sets the start menu in the game window.
     * 
     * @param masterMind the controller instance for the game
     */
    public void setStartMenu(Controller masterMind) {
        controller = masterMind;
        startMenu = new StartMenu(controller);
        add(startMenu);
        revalidate();
        repaint();
    }

    /**
     * Sets up the game window for the game mode.
     * 
     * @param model the game model instance
     */
    public void setGame(Model model) {
        remove(startMenu);
        middleMenu = new MiddleMenu(controller);
        menuBar = new MenuBar(controller);
        setJMenuBar(menuBar);
        add(middleMenu, BorderLayout.CENTER);
        setUpBoard(model);
    }

    /**
     * Sets up the game boards in the game window.
     * 
     * @param model the game model instance
     */
    public void setUpBoard(Model model) {
        removeExistingBoard();
        add(model.getPlayerBoard(), BorderLayout.WEST);
        add(model.getComputerBoard(), BorderLayout.EAST);
        clearHistory();
        revalidate();
        repaint();
    }

    /**
     * Retrieves the middle menu from the game window.
     * 
     * @return the middle menu component
     */
    public MiddleMenu getMenu() {
        return middleMenu;
    }

    /**
     * Retrieves the start menu from the game window.
     * 
     * @return the start menu component
     */
    public StartMenu getStartMenu() {
        return startMenu;
    }

    @Override
    public MenuBar getJMenuBar() {
        return menuBar;
    }

    /**
     * Removes the existing board components from the game window.
     */
    public void removeExistingBoard() {
        try {
            Component[] components = getContentPane().getComponents();
            for (Component component : components) {
                Object constraint = ((BorderLayout) getContentPane().getLayout()).getConstraints(component);
                if (constraint == BorderLayout.WEST || constraint == BorderLayout.EAST) {
                    remove(component);
                }
            }
        } catch (Exception e) {
            // Exception handling
        }
    }

    /**
     * Updates the history of moves in the middle menu.
     * 
     * @param coordinateName the name of the coordinate
     * @param subjective     the description of the move
     */
    public void updateHistory(String coordinateName, String subjective, boolean isHit) {
        middleMenu.updateHistory(coordinateName, subjective, isHit);
    }

    /**
     * Clears the history of moves in the middle menu.
     */
    public void clearHistory() {
        middleMenu.clearHistory();
    }

    public void showGuide() {
        String filePath = "/Users/transtephen/Desktop/Algonquin College/Level 4/Java Application/ASSIGNMENT/A2.2/MVC 1.4/src/resource/help.txt";

        String message = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                message = line;
            }
            System.out.println(line);
            JOptionPane.showMessageDialog(this, message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
