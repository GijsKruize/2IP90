
/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part PlayingField
 * 
 * @author Gijs Kruize
 * @author Gijs Nolet
 * assignment group 64
 * 
 * assignment copyright Kees Huizing
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.BreakIterator;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PlayingField extends JPanel implements ActionListener, ChangeListener {

    private Patch[][] grid = new Patch[PrisonersDilemma.GRID_SIZE][PrisonersDilemma.GRID_SIZE];

    public double alpha = 1.5; // defection award factor

    public int frequency = 1;

    private Timer timer;

    private final int NUMBER_OF_NEIGHBOURS = 8;

    private PrisonersDilemma prisonersDilemma;

    public String buttonState = "Go";

    // random number genrator
    private static final long SEED = 37L; // seed for random number generator; any number goes
    public static final Random random = new Random(SEED);

    // Avoid creating a new playingfield, but rather use the existing one
    public PlayingField(PrisonersDilemma prisonersDilemma) {
        this.prisonersDilemma = prisonersDilemma;
    }

    // Butonpress reactions
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Go".equals(e.getActionCommand())) {
            simulation();
            buttonState = "Pause";
        } else if ("Reset".equals(e.getActionCommand())) { // screenshot
            timer.stop();
            alpha = 1.5;
            frequency = 1;
            prisonersDilemma.screenshot();
            grid = new Patch[PrisonersDilemma.GRID_SIZE][PrisonersDilemma.GRID_SIZE];
            prisonersDilemma.makeStartingGrid((new boolean[PrisonersDilemma.GRID_SIZE][PrisonersDilemma.GRID_SIZE]));
            prisonersDilemma.buildGUI(true);
            if (buttonState.equals("Pause")) {
                prisonersDilemma.goButton.setActionCommand("Go");
                prisonersDilemma.goButton.setText("Go");
            }
        } else if ("Pause".equals(e.getActionCommand())) {
            timer.stop();
            prisonersDilemma.goButton.setActionCommand("Go");
            prisonersDilemma.goButton.setText("Go");
        } else {
            for (int ii = 0; ii < prisonersDilemma.GRID_SIZE; ii++) {
                for (int jj = 0; jj < prisonersDilemma.GRID_SIZE; jj++) {
                    if (prisonersDilemma.buttonGrid[ii][jj] == e.getSource()) {
                        System.out.println(ii + " - " + jj + " - " + grid[ii][jj].isCooperating());
                        if (grid[ii][jj].isCooperating() ) {
                        grid[ii][jj].setCooperating(false);
                        grid[ii][jj].setColor(Color.red);
                        prisonersDilemma.buildGUI(false);
                        } else if (grid[ii][jj].isCooperating() == false) {
                        grid[ii][jj].setCooperating(true);
                        grid[ii][jj].setColor(Color.blue);
                        prisonersDilemma.buildGUI(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        double sliderValue = PrisonersDilemma.alphaSlider.getValue();
        alpha = sliderValue / 10;
        frequency = prisonersDilemma.freqSlider.getValue();
    }

    public void simulation() {
        timer = new Timer(1000 / frequency, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setAlpha(alpha);
                step();
                prisonersDilemma.buildGUI(false);
            }
        });
        timer.setRepeats(true);
        timer.start();

    }

    /**
     * calculate and execute one step in the simulation
     */
    public void step() {
        // First, simulate each individal Patch
        calculateScores();

        // Secondly, change each Patch's statergy accordingly.
        changeStratergies();
    }

    public Patch getNeighbour(int rowPatch, int columnPatch, int neighbourNumber) {
        int row, column;

        // neighbourNumber 8 is the Patch itself.
        if (neighbourNumber == 8) {
            return grid[rowPatch][columnPatch];
        }

        // Give the row of the neighbour
        row = getNeighbourRow(rowPatch, neighbourNumber);

        // Give the column of the neighbour
        column = getNeighbourColumn(columnPatch, neighbourNumber);
        return grid[row][column];
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }

    // return grid as 2D array of booleans
    // true for cooperators, false for defectors
    // precondition: grid is rectangular, has non-zero size and elements are
    // non-null
    public boolean[][] getGrid() {
        boolean[][] resultGrid = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                resultGrid[x][y] = grid[x][y].isCooperating();
            }
        }

        return resultGrid;
    }

    // sets grid according to parameter inGrid
    // a patch should become cooperating if the corresponding
    // item in inGrid is true
    public void setGrid(boolean[][] inGrid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                grid[row][column] = new Patch();
                grid[row][column].setCooperating(inGrid[row][column]);
            }
        }
    }

    private void calculateScores() {
        // For each Patch in grid
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                // Now, play the prisoners dilemma game for each neighbour
                double score = 0.0;
                for (int neighbour = 0; neighbour < NUMBER_OF_NEIGHBOURS; neighbour++) {
                    // Next, calculate the score for this encounter
                    // Neighbour cooperates.
                    if (getNeighbour(row, column, neighbour).isCooperating()) {

                        // Both cooperate
                        if (grid[row][column].isCooperating()) {
                            score += 1.0;
                        } else {
                            // Only neigbour cooperates
                            score += getAlpha();
                        }
                    }

                    // Neighbour does not cooperate.
                    if (!getNeighbour(row, column, neighbour).isCooperating()) {
                        score += 0.0;
                    }
                }
                // Give each patch it's score
                grid[row][column].setScore(score);
            }
        }
    }

    private void changeStratergies() {
        // For each Patch in grid
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                // Check which neighbour has the highest score.
                // First, create a Arraylist with values (index = neighbourNumber and score)
                ArrayList<Double> scores = new ArrayList<Double>();

                // Secondly, create a Arraylist with all neighbourNumbers with maximum scores
                ArrayList<Integer> neighbours = new ArrayList<Integer>();

                for (int neighbourNumber = 0; neighbourNumber < NUMBER_OF_NEIGHBOURS; neighbourNumber++) {
                    scores.add(getNeighbour(row, column, neighbourNumber).getScore());
                }
                // Add it's own score
                scores.add(grid[row][column].getScore());
                double maxScore = Collections.max(scores);

                // Now check at which indexes the max score appears
                for (int i = 0; i < scores.size(); i++) {
                    if (scores.get(i) == maxScore) {
                        neighbours.add(i);
                    }
                }

                // Randomly choose which neighbour's strategy gets copied
                int chosenNeighbour = neighbours.get((new Random()).nextInt(neighbours.size()));

                // Change the color
                if (getNeighbour(row, column, chosenNeighbour).isCooperating()) {
                    // If no change: blue
                    if (grid[row][column].isCooperating()) {
                        grid[row][column].setColor(Color.blue);
                    } else {
                        // If strategy does change to cooperating: light blue
                        grid[row][column].setColor((new Color(172, 216, 230)));
                    }
                } else {
                    // If no change: red
                    if (!grid[row][column].isCooperating()) {
                        grid[row][column].setColor(Color.red);
                    } else {
                        // If strategy does change to cooperating: orange
                        grid[row][column].setColor(Color.orange);
                    }
                }

                // Set the strategy equal to it's highest scoring neighbour.
                grid[row][column].setCooperating(getNeighbour(row, column, chosenNeighbour).isCooperating());
            }
        }
    }

    private int getNeighbourRow(int rowPatch, int neighbourNumber) {
        int row;
        if (!(neighbourNumber > 2)) {
            row = rowPatch - 1;
            // Check if rowPatch is the first row
            if (row < 0) {
                // Minus 1 because row 1 is index 0;
                row = PrisonersDilemma.GRID_SIZE - 1;
            }
        } else if (!(neighbourNumber > 4)) {
            row = rowPatch;
        } else {
            row = rowPatch + 1;
            // Check if rowPatch is the last row
            if (row == PrisonersDilemma.GRID_SIZE) {
                row = 0;
            }
        }

        return row;
    }

    private int getNeighbourColumn(int columnPatch, int neighbourNumber) {
        int column;

        if (neighbourNumber == 0 || neighbourNumber == 3 || neighbourNumber == 5) {
            column = columnPatch - 1;

            // Check if columnPatch is the first row
            if (column < 0) {
                column = PrisonersDilemma.GRID_SIZE - 1;
            }
        } else if (neighbourNumber == 1 || neighbourNumber == 6) {
            column = columnPatch;
        } else {
            column = columnPatch + 1;

            // Check if columnPatch is the last row
            if (column == PrisonersDilemma.GRID_SIZE) {
                column = 0;
            }
        }

        return column;
    }

    public void setColor(int ii, int jj, Color color) {
        grid[ii][jj].setColor(color);
    }

    public Color getColor(int ii, int jj) {
        return grid[ii][jj].getColor();
    }

    public Patch getPatch(int ii, int jj) {
        return grid[ii][jj];
    }
}
