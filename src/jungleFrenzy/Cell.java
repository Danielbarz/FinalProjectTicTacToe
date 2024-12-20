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
        // Hapus latar belakang solid (g.fillRect)
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size); // Gambar garis tepi cell (grid)

        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            // Gambar content (CROSS atau NOUGHT) di tengah cell
            int padding = size / 5;
            int seedSize = size - 2 * padding;
            g.drawImage(content.getImage(), x + padding, y + padding, seedSize, seedSize, null);
        }
    }

}