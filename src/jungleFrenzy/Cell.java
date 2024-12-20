package jungleFrenzy;

import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static final int SIZE = 120; // cell width/height (square)
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    // Define properties (package-visible)
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    public Seed content;
    /** Row and column of this cell */
    public int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g, int x, int y, int size) {
        if (content == Seed.CROSS) {
            g.setColor(Color.RED);
            g.fillOval(x + 10, y + 10, size - 20, size - 20); // Draw RED token
        } else if (content == Seed.NOUGHT) {
            g.setColor(Color.YELLOW);
            g.fillOval(x + 10, y + 10, size - 20, size - 20); // Draw YELLOW token
        }
    }

}