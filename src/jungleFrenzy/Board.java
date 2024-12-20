package jungleFrenzy;

import java.awt.*;

/**
 * The Board class models the ROWS-by-COLS game board.
 */
public class Board {
    // Define named constants
    public static final int ROWS = 6;  // ROWS x COLS cells
    public static final int COLS = 7;
    // Define named constants for drawing
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final int GRID_WIDTH = 8;  // Grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;  // grid lines
    public static final int Y_OFFSET = 1;  // Fine tune for better display

    // Define properties (package-visible)
    /** Composes of 2D array of ROWS-by-COLS Cell instances */
    Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the game objects (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS]; // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
                // Cells are initialized in the constructor
            }
        }
    }

    /** Reset the game board, ready for new game */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame(); // clear the cell content
            }
        }
    }

    /**
     * Check for a winning condition (4 in a row, column, or diagonal).
     */
    private boolean hasWon(Seed player, int selectedRow, int selectedCol) {
        // Check horizontal
        int count = 0;
        for (int col = 0; col < COLS; ++col) {
            count = (cells[selectedRow][col].content == player) ? count + 1 : 0;
            if (count == 4) return true;
        }

        // Check vertical
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            count = (cells[row][selectedCol].content == player) ? count + 1 : 0;
            if (count == 4) return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        int startRow = Math.max(0, selectedRow - selectedCol);
        int startCol = Math.max(0, selectedCol - selectedRow);
        while (startRow < ROWS && startCol < COLS) {
            count = (cells[startRow][startCol].content == player) ? count + 1 : 0;
            if (count == 4) return true;
            startRow++;
            startCol++;
        }

        // Check anti-diagonal (bottom-left to top-right)
        count = 0;
        int diff = selectedRow + selectedCol;
        startRow = Math.min(ROWS - 1, diff);
        startCol = diff - startRow;
        while (startRow >= 0 && startCol < COLS) {
            count = (cells[startRow][startCol].content == player) ? count + 1 : 0;
            if (count == 4) return true;
            startRow--;
            startCol++;
        }

        return false;
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Compute and return the new game state
        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Nobody wins. Check for DRAW (all cells occupied) or PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING; // still have empty cells
                    }
                }
            }
            return State.DRAW; // no empty cell, it's a draw
        }
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Draw the grid-lines
        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
                    GRID_WIDTH, CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw all the cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);  // ask the cell to paint itself
            }
        }
    }
}
