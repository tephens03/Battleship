package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;

import miscellaneous.Misc;
import model.ComputerBoard;
import model.Coordinate;
import model.Model;
import model.PlayerBoard;
import view.*;

/**
 * The Controller class handles user interactions and manages the game logic.
 * It listens to user actions and updates the model and view accordingly.
 */
public class Controller implements ActionListener {

    private ArrayList<Coordinate> playerShotCoordinates;
    private MiddleMenu middleMenu;
    private MenuBar menuBar;
    private StartMenu startMenu;
    private boolean canPlay;
    private View view;
    private Model model;
    private Clip clip;

    /**
     * Constructs a new Controller instance.
     * 
     * @param view  the View object representing the game view
     * @param model the Model object representing the game model
     */
    public Controller(View view, Model model) {
        this.playerShotCoordinates = new ArrayList<>();
        this.view = view;
        this.model = model;
    }

    /**
     * Sets up the controller by establishing a relationship with the view
     * components.
     */
    public void setUpController() {
        view.setStartMenu(this);
        middleMenu = view.getMenu();
        menuBar = view.getJMenuBar();
        startMenu = view.getStartMenu();
    }

    /**
     * Generates a random value and shoots at the player.
     */
    public void shootAtPlayer() {
        Coordinate coordinate;
        PlayerBoard playerBoard = model.getPlayerBoard();
        do {
            Random rand = new Random();
            int randRow = 1 + rand.nextInt(2 * model.getDimension());
            int randCol = 1 + rand.nextInt(2 * model.getDimension());
            String coordinateName = Misc.ALPHABET[randCol] + Integer.toString(randRow);
            coordinate = playerBoard.getCoordinate(coordinateName);
        } while (playerShotCoordinates.contains(coordinate));

        playerShotCoordinates.add(coordinate);

        boolean isHit = model.receiveShot(playerBoard, coordinate);
        view.updateHistory(coordinate.getName(), "Computer", isHit);

        if (model.getPlayerShipCount() == 0)
            gameStop("Over");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == startMenu.getSingleButton()) {
            view.setGame(model);
            middleMenu = view.getMenu();
            menuBar = view.getJMenuBar();
        } else if (source instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) source;

            ComputerBoard computerBoard = model.getComputerBoard();

            if (model.canPlay() && !coordinate.isShot()) {

                boolean isHit = model.receiveShot(computerBoard, coordinate);

                view.updateHistory(coordinate.getName(), "Player", isHit);

                if (model.getComputerShipCount() == 0) {
                    gameStop("Win");
                    return;
                }

                shootAtPlayer();
                return;
            }
        } else if (source == middleMenu.getMenuPlayButton()) {
            if (model.canPlay()) {
                canPlay = true;
                middleMenu.setStartState();
            }
        } else if (source == middleMenu.getMenuRandButton()) {
            model.randomizeShips();
            view.setUpBoard(model);
            playerShotCoordinates = new ArrayList<>();
        } else if (source == middleMenu.getMenuDimensionBox()) {
            JComboBox<?> comboBox = (JComboBox<?>) middleMenu.getMenuDimensionBox();
            int dimension = Integer.parseInt((String) comboBox.getSelectedItem());
            model.setDimension(dimension);
            view.setUpBoard(model);
        } else if (source == middleMenu.getMenuResetButton()) {
            model.setNewBoards();
            middleMenu.setResetState();
            view.setUpBoard(model);
        } else if (source == middleMenu.getMenuLanguageBox()) {
            middleMenu.setLanguage();
        } else if (source == middleMenu.getDesignButton()) {
            model.enterDesignMode();
            view.setUpBoard(model);


        } else if (source == menuBar.getSolutionItem()) {
            model.showSolution();
            canPlay = false;
        } else if (source == menuBar.getHitColorItem()) {
            Color color = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
            model.setNewHitColor(color);
        } else if (source == menuBar.getMissedColorItem()) {
            Color color = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
            model.setNewMissedColor(color);
        } else if (source == menuBar.getUnselectedColorItem()) {
            Color color = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
            model.setNewUnselectedColor(color);
        } else if (source == menuBar.getGuideItem()) {
            view.showGuide();
        }
    }

    /**
     * Stops the game and plays the specified sound effect.
     * 
     * @param sound the sound effect to play
     */
    public void gameStop(String sound) {
        playTheme(sound);
        middleMenu.stopTimer();
        canPlay = false;
    }

    /**
     * Plays the theme audio file.
     * 
     * @param sound the sound file to play
     */
    public void playTheme(String sound) {
        try {
            System.out.println("../resource/game" + sound + ".wav");
            File audioFile = new File("../resource/game" + sound + ".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            // clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing audio!");
        }
    }

}
