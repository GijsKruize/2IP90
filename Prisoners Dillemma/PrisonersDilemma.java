
/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * main class
 * 
 * @author Gijs Kruize
 * @author Gijs Nolet
 * assignment group 64
 * 
 * assignment copyright Kees Huizing
 */
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PrisonersDilemma {

    // Public because the grid size is used in other classes
    public static final int GRID_SIZE = 50;
    // initialized is used to check if a board needs to be made, or updated
    private static boolean initialized = false;

    private JFrame frame;
    public JButton[][] buttonGrid = new JButton[GRID_SIZE][GRID_SIZE];
    JPanel dillemaGrid;
    public JButton goButton;
    private JButton resetButton;
    static JSlider alphaSlider;
    static JSlider freqSlider;
    private JLabel freqTitle;
    private JLabel alphaTitle;
    PlayingField playingfield = new PlayingField(this);
    JPanel btnPnl;
    JPanel sldrPnl;
    Dimension frameSize;

    JComponent buildGrid() {
        dillemaGrid = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        dillemaGrid.setBorder(null);

        // fill the panel
        for (int ii = 0; ii < GRID_SIZE; ii++) {
            for (int jj = 0; jj < GRID_SIZE; jj++) {
                dillemaGrid.add(buttonGrid[jj][ii]);
            }
        }
        return dillemaGrid;
    }

    public void screenshot() {
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        frame.paint(graphics); // alternately use .printAll(..)
        try {
            ImageIO.write(image, "PNG", new File("screenshot" + ".png"));
            System.out.println("Saved screenshot as screenshot.png");
        } catch (IOException e) {
            System.out.println("Saving screenshot failed: " + e);
        }
    }

    private void initializeButtonGrid(boolean initAgain) {
        boolean[][] inGrid = new boolean[GRID_SIZE][GRID_SIZE];

        // If not initialized make a new board
        if (!initialized || initAgain) {
            makeStartingGrid(inGrid);
        } else {
            // create the chess board circles
            for (int ii = 0; ii < buttonGrid.length; ii++) {
                for (int jj = 0; jj < buttonGrid[ii].length; jj++) {
                    JButton b = new RoundButton();
                    b.setSize(5, 5);
                    b.setBorder(null);
                    if (playingfield.getGrid()[ii][jj]) {
                        b.setBackground(playingfield.getColor(ii, jj));
                        inGrid[ii][jj] = true;
                    } else {
                        b.setBackground(playingfield.getColor(ii, jj));
                        inGrid[ii][jj] = false;
                    }
                    buttonGrid[ii][jj] = b;
                }
            }
        }
    }

    public void makeStartingGrid(boolean[][] inGrid) {
        // create the chess board circles
        for (int ii = 0; ii < buttonGrid.length; ii++) {
            for (int jj = 0; jj < buttonGrid[ii].length; jj++) {
                // Update the board
                playingfield.setGrid(inGrid);
                JButton b = new RoundButton();
                b.setSize(5, 5);
                b.setBorder(null);
                inGrid[ii][jj] = playingfield.random.nextBoolean();
                if (inGrid[ii][jj]) {
                    playingfield.setColor(ii, jj, Color.blue);
                    b.setBackground(Color.blue);
                } else if (inGrid[ii][jj] == false) {
                    playingfield.setColor(ii, jj, Color.red);
                    b.setBackground(Color.red);
                }
                b.addActionListener(playingfield);
                buttonGrid[ii][jj] = b;

            }
        }

    }

    public void buildGUI(boolean initAgain) {
        // Initialize the grid
        initializeButtonGrid(initAgain);
        if (!initialized) {
            SwingUtilities.invokeLater(() -> {
                // If not initialized, create a frame
                frame = new JFrame("Prisoners Dilemma");
                // All initializations have been excecuted at this point, so set initialized to
                // true.
                setInitialized(true);

                paintFrameContents();
                frame.repaint();
            });
        } else {
            // Frame already exist, so update it
            frame.getContentPane().removeAll();
            paintFrameContents();
            frame.repaint();
        }
        playingfield.setAlpha(playingfield.alpha);
    }

    public void setInitialized(boolean b) {
        initialized = b;
    }

    private void paintFrameContents() {
        int alphaValue = (int) (playingfield.alpha * 10);
        int freqValue = playingfield.frequency;
        freqTitle = new JLabel("Frequency (Hz)");
        alphaTitle = new JLabel("defection α");
        frame.add(buildGrid());
        btnPnl = new JPanel();
        sldrPnl = new JPanel();
        freqSlider = new JSlider(0, 60);
        freqSlider.setMajorTickSpacing(10);
        freqSlider.setMinorTickSpacing(1);
        freqSlider.setPaintLabels(true);
        freqSlider.setPaintTicks(true);
        freqSlider.setPaintTrack(true);
        freqSlider.setAutoscrolls(true);
        freqSlider.setValue(freqValue);
        freqSlider.addChangeListener(playingfield);
        sldrPnl.add(freqTitle);
        sldrPnl.add(freqSlider);
        alphaSlider = new JSlider(0, 30);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(0, new JLabel("0.0"));
        labelTable.put(10, new JLabel("1.0"));
        labelTable.put(20, new JLabel("2.0"));
        labelTable.put(30, new JLabel("3.0"));
        alphaSlider.setLabelTable(labelTable);
        alphaSlider.setMajorTickSpacing(10);
        alphaSlider.setMinorTickSpacing(1);
        alphaSlider.setPaintLabels(true);
        alphaSlider.setPaintTicks(true);
        alphaSlider.setPaintTrack(true);
        alphaSlider.setAutoscrolls(true);
        alphaSlider.setValue(alphaValue);
        alphaSlider.addChangeListener(playingfield);
        sldrPnl.add(alphaTitle);
        sldrPnl.add(alphaSlider);
        goButton = new JButton();
        goButton.setText(playingfield.buttonState);
        goButton.setActionCommand(playingfield.buttonState);
        goButton.addActionListener(playingfield);
        btnPnl.add(goButton);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(playingfield);
        btnPnl.add(resetButton);
        frame.add(sldrPnl, BorderLayout.NORTH);
        frame.add(btnPnl, BorderLayout.SOUTH);
        frame.setSize(600, 700);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] a) {
        (new PrisonersDilemma()).buildGUI(false);
    }
}
