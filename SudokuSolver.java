/**
 * Class that solves the Asterisk Sudoku. Prints the number of solutions of a
 * Sudoku if there are multiple. If there is only a single solution, prints this
 * solution instead.
 *
 * by Gijs Kruize 1658662 and Gijs Nolet 1679570 as group 64
 */

class SudokuSolver {

    int SUDOKU_SIZE = 9; // Size of the grid.
    int SUDOKU_MIN_NUMBER = 1; // Minimum digit to be filled in.
    int SUDOKU_MAX_NUMBER = 9; // Maximum digit to be filled in.
    int SUDOKU_BOX_DIMENSION = 3; // Dimension of the boxes (sub-grids that should contain all digits).

    int[][] grid = new int[][] { // The puzzle grid; 0 represents empty.
            { 0, 9, 0, 7, 3, 0, 4, 0, 0 }, // One solution.
            { 0, 0, 0, 0, 0, 0, 5, 0, 0 }, 
            { 3, 0, 0, 0, 0, 6, 0, 0, 0 },

            { 0, 0, 0, 0, 0, 2, 6, 4, 0 }, 
            { 0, 0, 0, 6, 5, 1, 0, 0, 0 }, 
            { 0, 0, 6, 9, 0, 7, 0, 0, 0 },

            { 5, 8, 0, 0, 0, 0, 0, 0, 0 }, 
            { 9, 0, 0, 0, 0, 3, 0, 2, 5 }, 
            { 6, 0, 3, 0, 0, 0, 8, 0, 0 }, };

    int solutionCounter = 0; // Solution counter

    // Is there a conflict when we fill in d at position (r, c)?
    boolean givesConflict(int r, int c, int d) {
        return !(!rowConflict(r, d) && !columnConflict(c, d) && !boxConflict(r, c, d) && !asteriskConflict(r, c, d));
    }

    // Is there a conflict when we fill in d in row r?
    boolean rowConflict(int r, int d) {
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (grid[r][i] == d) {
                return true;
            }
        }
        return false;
    }

    // Is there a conflict in column c when we fill in d?
    boolean columnConflict(int c, int d) {
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (grid[i][c] == d) {
                return true;
            }
        }
        return false;
    }

    // Is there a conflict in the box at (r, c) when we fill in d?
    boolean boxConflict(int r, int c, int d) {
        int rBox = r - r % 3;
        int cBox = c - c % 3;

        for (int i = rBox; i < rBox + 3; i++) {
            for (int j = cBox; j < cBox + 3; j++) {
                if (grid[i][j] == d) {
                    return true;
                }
            }
        }
        return false;
    }

    // Is there a conflict in the asterisk when we fill in d?
    boolean asteriskConflict(int r, int c, int d) {
        int[] asteriskX = new int[] { 1, 2, 2, 4, 4, 4, 6, 6, 7 };
        int[] asteriskY = new int[] { 4, 2, 6, 1, 4, 7, 2, 6, 4 };

        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (r == asteriskX[i] && c == asteriskY[i]) {
                for (int j = 0; j < SUDOKU_MAX_NUMBER; j++) {
                    if (grid[asteriskX[j]][asteriskY[j]] == d) {
                        // System.out.println("Coordinates " + asteriskX[i] + " and " + asteriskY[i] + "
                        // have value " + d);
                        return true;
                    }
                }
                return false;
            }

        }

        return false;

    }

    // Finds the next empty square (in "reading order")
    int[] findEmptySquare() {
        for (int r = 0; r < SUDOKU_SIZE; r++) {
            for (int c = 0; c < SUDOKU_SIZE; c++) {
                if (grid[r][c] == 0) {
                    return new int[] { r, c };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    boolean solved;

    void solve() {
        int r = findEmptySquare()[0];
        int c = findEmptySquare()[1];

        if (r != -1 && c != -1) {
            for (int d = SUDOKU_MIN_NUMBER; d <= SUDOKU_MAX_NUMBER; d++) {
                if (!givesConflict(r, c, d)) {
                    // Fill in the number that doesn't give a conflict.
                    grid[r][c] = d;

                    // Solve the next cell
                    solve();

                    // Check if the next cell was fillable.
                    if (solved) {
                        // All cells filled and correct? Stop the recursion.
                        solved = true;
                        return;
                    } else {
                        grid[r][c] = 0;
                    }
                }
            }
            // Empty cell not fillable, so a prior fill must be false;
            solved = false;
            return;
        }
        // No empty cells, aka solved.
        solved = true;
        return;
    }

    // Print the sudoku grid.
    void print() { // De grid is 19 karakters breed en 13 karakters lang
        String[][] printGrid = new String[13][19];
        // assigning a " " (WhiteSpace) to every empty point in the gridd so it wont
        // print "null"
        for (int r = 0; r < 13; r++) {
            for (int c = 0; c < 19; c++) {
                printGrid[r][c] = " ";
            }
        }
        // Next two for loops make the visual grid/box outlines
        for (int r = 0; r < 13; r++) {
            printGrid[r][0] = "|";
            printGrid[r][6] = "|";
            printGrid[r][12] = "|";
            printGrid[r][18] = "|";
        }
        for (int i = 0; i < 13; i += 4) {
            for (int c = 0; c < 19; c++) {
                printGrid[i][c] = "-";
            }
            printGrid[i][0] = "+";
            printGrid[i][18] = "+";
        }
        // Assigning the indications for the asterisk conflict
        printGrid[2][8] = ">";
        printGrid[2][10] = "<";
        printGrid[3][4] = ">";
        printGrid[3][14] = "<";
        printGrid[6][2] = ">";
        printGrid[6][4] = "<";
        printGrid[6][8] = ">";
        printGrid[6][10] = "<";
        printGrid[6][14] = ">";
        printGrid[6][16] = "<";
        printGrid[9][4] = ">";
        printGrid[9][14] = "<";
        printGrid[10][8] = ">";
        printGrid[10][10] = "<";
        // Next 3 for loops print the actual numbers of the sudoku grid in the right
        // spots
        for (int r = 1; r < 4; r++) {
            for (int c = 1; c < 19; c += 2) {
                if (grid[r - 1][(c - 1) / 2] != 0) {
                    printGrid[r][c] = String.valueOf(grid[r - 1][(c - 1) / 2]);
                }
            }
        }
        for (int r = 5; r < 8; r++) {
            for (int c = 1; c < 19; c += 2) {
                if (grid[r - 2][(c - 1) / 2] != 0) {
                    printGrid[r][c] = String.valueOf(grid[r - 2][(c - 1) / 2]);
                }
            }
        }
        for (int r = 9; r < 12; r++) {
            for (int c = 1; c < 19; c += 2) {
                if (grid[r - 3][(c - 1) / 2] != 0) {
                    printGrid[r][c] = String.valueOf(grid[r - 3][(c - 1) / 2]);
                }
            }
        }
        // This for loops takes care of the actual printing
        for (int r = 0; r < 13; r++) {

            String[] row = new String[19];
            row[r] = "";

            for (int c = 0; c < 19; c++) {
                row[r] = row[r] + printGrid[r][c];
            }

            System.out.println(row[r]);
        }

    }

    // Run the actual solver.
    void solveIt() {
        solve();
        print();
    }

    public static void main(String[] args) {
        (new SudokuSolver()).solveIt();
    }
}