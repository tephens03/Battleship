package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.Controller;
import miscellaneous.Misc;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MiddleMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    // Components
    private ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
    private JLabel logoLabel = new JLabel(logoIcon);
    private JLabel languageLabel = new JLabel();
    private JComboBox<String> languageBox = new JComboBox<>(Misc.LANGUAGE);
    private JButton designButton = new JButton("Design");
    private JButton randButton = new JButton("Random");
    private JLabel dimensionLabel = new JLabel();
    private JComboBox<String> dimensionBox = new JComboBox<>(Misc.DIMENSION);
    private JTextArea historyBox = new JTextArea(25, 25);
    private JScrollPane historyScrollPane = new JScrollPane(historyBox);
    private JLabel timerLabel = new JLabel();
    private JLabel timerBox = new JLabel();
    private int elapsedTime = 0;
    private Timer timer = new Timer(1000, e -> updateTimer());

    private JButton resetButton = new JButton("Reset");
    private JButton playButton = new JButton("Play");

    private Controller controller;
    private LinkedList<JPanel> panelList = new LinkedList<>();
    private LinkedList<JButton> buttonList = new LinkedList<>();

    public MiddleMenu(Controller masterMind) {
        controller = masterMind;
        setPreferredSize(new Dimension(240, 800));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 254, 205));
        configurePanels();
        addComponentsToPanel();
        setLanguage();
        historyBox.setCaretPosition(0);
        resetButton.setEnabled(false);
    }

    private void configurePanels() {
        addPanel(languageLabel, languageBox);
        addPanel(designButton, randButton);
        addPanel(dimensionLabel, dimensionBox);
        addPanel(historyScrollPane);
        addPanel(timerLabel, timerBox);
        addPanel(resetButton);
        addPanel(playButton);

        setOpaqueAndMaxSize(panelList);
    }

    private void addPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));
        for (Component component : components) {
            panel.add(component);
        }
        panelList.add(panel);
    }

    private void setOpaqueAndMaxSize(LinkedList<JPanel> panels) {
        for (JPanel panel : panels) {
            panel.setOpaque(false);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));
        }
    }

    private void addButtonsToLinkedList() {
        buttonList.add(designButton);
        buttonList.add(randButton);
        buttonList.add(resetButton);
        buttonList.add(playButton);
    }

    private void updateTimer() {
        elapsedTime += 1000;
        int hours = (int) elapsedTime / 3600000;
        int minutes = (int) (elapsedTime % 3600000) / 60000;
        int seconds = (int) ((elapsedTime % 3600000) % 60000) / 1000;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerBox.setText(timeString);
    }

    public void addComponentsToPanel() {
        languageBox.addActionListener(controller);
        dimensionBox.addActionListener(controller);
        resetButton.addActionListener(controller);
        playButton.addActionListener(controller);
        randButton.addActionListener(controller);
        designButton.addActionListener(controller);

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));
        logoPanel.add(logoLabel);

        add(logoPanel);
        panelList.forEach(this::add);
    }

    public void setLanguage() {
        String language = (String) languageBox.getSelectedItem();
        String filePath = String.format(
                "/Users/transtephen/Desktop/Algonquin College/Level 4/Java Application/ASSIGNMENT/A2.2/src/resource/txt_%s.txt",
                language);

        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            languageLabel.setText(lines.get(0));
            designButton.setText(lines.get(1));
            randButton.setText(lines.get(2));
            dimensionLabel.setText(lines.get(3));
            timerLabel.setText(lines.get(4));
            resetButton.setText(lines.get(5));
            playButton.setText(lines.get(6));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartState() {
        timer.start();
        resetButton.setEnabled(true);
        designButton.setEnabled(false);
        randButton.setEnabled(false);
        languageBox.setEnabled(false);
        dimensionBox.setEnabled(false);
    }

    public void setResetState() {
        stopTimer();
        elapsedTime = 0;
        timerBox.setText("00:00:00");

        designButton.setEnabled(true);
        randButton.setEnabled(true);
        languageBox.setEnabled(true);
        dimensionBox.setEnabled(true);
    }

    public void stopTimer() {
        timer.stop();
    }

    public void updateHistory(String coordinate, String subject, boolean isHit) {
        String message = subject + ": " + coordinate + " (" + (isHit ? "Hit" : "Missed") + ")" + "\n"
                + historyBox.getText();
        historyBox.setText(message);
    }

    public void clearHistory() {
        historyBox.setText("");
    }

    public JButton getMenuPlayButton() {
        return playButton;
    }

    public JButton getMenuRandButton() {
        return randButton;
    }

    public JComboBox<String> getMenuDimensionBox() {
        return dimensionBox;
    }

    public JButton getMenuResetButton() {
        return resetButton;
    }

    public JComboBox<String> getMenuLanguageBox() {
        return languageBox;
    }

    public JButton getDesignButton() {
        return designButton;
    }
}
