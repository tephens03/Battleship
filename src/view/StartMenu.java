package view;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 * Background class representing a panel with background image and buttons.
 * 
 * @author Gia Bao Tran - Kiet Tran
 */
public class StartMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private CustomButton singleButton;
	private CustomButton multiButton;
	private CustomButton optionButton;
	private CustomButton authors;
	private Clip clip;
	private int buttonHeight;
	private int buttonCount;
	private Controller controller;

	/**
	 * Constructs a BackGround object.
	 */
	public StartMenu(Controller controller) {
		this.controller = controller;
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/resource/bg.png")).getScaledInstance(1500, 1000,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		configButtons();

		add(authors);
		// playTheme();
	}

	/**
	 * Draws the background image on the panel.
	 *
	 * @param g the Graphics object to draw on
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}

	/**
	 * Handles the actionPerformed event from the buttons.
	 *
	 * @param e the ActionEvent object representing the event
	 */
	// public void actionPerformed(ActionEvent e) {
	// if (e.getSource() == singleButton) {
	// masterGame.remove(this);
	// masterGame.add(new GameController(masterGame), BorderLayout.CENTER);
	// masterGame.revalidate();
	// masterGame.repaint();
	// setVisible(true);
	// } else if (e.getSource() == multiButton) {
	// System.out.println("Multi-player clicked");
	// } else if (e.getSource() == authors) {
	// System.out.println("Bang Bang Simulations & Gia Bao Tran");
	// } else {
	// audioOn = !audioOn;
	// if (audioOn) {
	// clip.stop();
	// } else {
	// clip.start();
	// clip.loop(Clip.LOOP_CONTINUOUSLY);
	// }
	// }
	// }

	/**
	 * Plays the theme audio file.
	 */
	public void playTheme() {
		try {
			File audioFile = new File("src/resource/audio.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("Error playing audio!");
		}
	}

	/**
	 * Calculates the y-coordinate for the buttons based on the button count.
	 *
	 * @param buttonCount the count of buttons
	 * @return the calculated y-coordinate
	 */
	public int calculateY(int buttonCount) {
		return 400 + (25 * buttonCount) + buttonHeight * buttonCount;
	}

	/**
	 * Configures the buttons and sets their properties.
	 */
	public void configButtons() {
		buttonCount = 0;
		buttonHeight = getHeight() / 10;

		singleButton = createCustomButton("Single-player");
		multiButton = createCustomButton("Multi-player");
		optionButton = createCustomButton("Options");
		authors = createCustomButton("Gia Bao Tran");

		singleButton.addActionListener(controller);
		multiButton.addActionListener(controller);
		optionButton.addActionListener(controller);
		add(singleButton);
		add(multiButton);
		add(optionButton);
		add(authors);
	}

	/**
	 * Creates a custom button with the specified text and button count.
	 *
	 * @param text the text to display on the button
	 * @return the created CustomButton object
	 */
	public CustomButton createCustomButton(String text) {
		CustomButton button = new CustomButton(text, buttonCount);
		buttonCount++;
		return button;
	}

	public CustomButton setSingleButton() {
		return singleButton;
	}

	/**
	 * Sets the multi-player button.
	 * 
	 * @param button the multi-player button
	 */
	public CustomButton setMultiButton() {
		return multiButton;
	}

	/**
	 * Sets the options button.
	 * 
	 * @param button the options button
	 */
	public CustomButton setOptionButton() {
		return optionButton;
	}

	/**
	 * Sets the authors button.
	 * 
	 * @param button the authors button
	 */
	public CustomButton setAuthorsButton() {
		return authors;
	}

	public CustomButton getSingleButton() {
		return singleButton;
	}

	/**
	 * gets the multi-player button.
	 * 
	 * @param button the multi-player button
	 */
	public CustomButton getMultiButton() {
		return multiButton;
	}

	/**
	 * gets the options button.
	 * 
	 * @param button the options button
	 */
	public CustomButton getOptionButton() {
		return optionButton;
	}

	/**
	 * gets the authors button.
	 * 
	 * @param button the authors button
	 */
	public CustomButton getAuthorsButton() {
		return authors;
	}
}

/**
 * CustomButton class representing a custom JButton.
 */
class CustomButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int buttonWidth;
	private int buttonHeight;

	/**
	 * Constructs a CustomButton object with the specified text and button count.
	 *
	 * @param text        the text to display on the button
	 * @param buttonCount the count of buttons
	 */
	public CustomButton(String text, int buttonCount) {
		setOpaque(false);
		setContentAreaFilled(true);
		setText(text);
		setFocusable(false);
		setFont(getFont().deriveFont(18f));
		setBounds(1100, calculateY(buttonCount), buttonWidth, buttonHeight);
	}

	/**
	 * Calculates the y-coordinate for the button based on the button count.
	 *
	 * @param buttonCount the count of buttons
	 * @return the calculated y-coordinate
	 */
	public int calculateY(int buttonCount) {
		return 400 + (25 * buttonCount) + buttonHeight * buttonCount;
	}
}
