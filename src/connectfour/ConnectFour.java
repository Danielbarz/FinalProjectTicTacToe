package connectfour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class ConnectFour extends JPanel {
    private static final long serialVersionUID = 1L;

    // Define game objects
    private Board board;         // the game board
    private State currentState;  // the current state of the game
    private Seed currentPlayer;  // the current player
    private JLabel turnLabel;    // For displaying current turn
    private JLabel redCounterLabel;  // Counter for Red wins
    private JLabel yellowCounterLabel; // Counter for Yellow wins
    private JLabel timerLabel;   // Timer display
    private JButton clearButton; // Clear All button
    private JButton homeButton;  // Back to Home button
    private Timer timer;         // Timer for the game
    private int timeElapsed;     // Time elapsed in seconds
    private int redWins = 0;     // Number of wins for Red
    private int yellowWins = 0;  // Number of wins for Yellow

    private String playerXName;  // Name of Player X (Red)
    private String playerOName;  // Name of Player O (Yellow)

    /** Constructor to setup the UI and game components */
    public ConnectFour(String playerXName, String playerOName) {
        this.playerXName = playerXName;
        this.playerOName = playerOName;
        setLayout(new BorderLayout());

        // Right-side status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setPreferredSize(new Dimension(200, Board.CANVAS_HEIGHT));
        statusPanel.setBackground(new Color(240, 240, 240));

        // Turn label
        turnLabel = new JLabel("Turn: " + playerXName + " (Red)");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Timer label
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Red counter label
        redCounterLabel = new JLabel(playerXName + " (Red): 0");
        redCounterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        redCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Yellow counter label
        yellowCounterLabel = new JLabel(playerOName + " (Yellow): 0");
        yellowCounterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        yellowCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Clear All button
        clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener(e -> clearAll()); // Clear all points and board

        // Back to Home button
        homeButton = new JButton("Back to Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(e -> backToHome()); // Go back to ScreenAwal

        // Add components to status panel
        statusPanel.add(Box.createVerticalStrut(20)); // Spacer
        statusPanel.add(turnLabel);
        statusPanel.add(Box.createVerticalStrut(20)); // Spacer
        statusPanel.add(timerLabel);
        statusPanel.add(Box.createVerticalStrut(10)); // Spacer
        statusPanel.add(redCounterLabel);
        statusPanel.add(Box.createVerticalStrut(10)); // Spacer
        statusPanel.add(yellowCounterLabel);
        statusPanel.add(Box.createVerticalStrut(20)); // Spacer
        statusPanel.add(clearButton);
        statusPanel.add(Box.createVerticalStrut(10)); // Spacer
        statusPanel.add(homeButton);

        // Add components to main layout
        add(statusPanel, BorderLayout.EAST);
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH + 200, Board.CANVAS_HEIGHT)); // Adjust for status panel

        // Initialize game board and state
        board = new Board();
        initGame();
        newGame();

        // Timer setup
        timeElapsed = 0;
        timer = new Timer(1000, e -> updateTimer());
        timer.start();

        // Mouse listener for game interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / Cell.SIZE; // Determine column clicked
                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        for (int row = Board.ROWS - 1; row >= 0; row--) {
                            if (board.cells[row][col].content == Seed.NO_SEED) {
                                board.cells[row][col].content = currentPlayer; // Make a move
                                currentState = board.stepGame(currentPlayer, row, col); // Update game state
                                updateTurn(); // Update turn or game state
                                break;
                            }
                        }
                    }
                } else {
                    newGame(); // Restart game after win or draw
                }
                repaint();
            }
        });
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board(); // Allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // All cells empty
            }
        }
        currentPlayer = Seed.CROSS; // Red (Player X) plays first
        currentState = State.PLAYING; // Ready to play
        timeElapsed = 0; // Reset timer
        turnLabel.setText("Turn: " + playerXName + " (Red)");
    }

    /** Clear all points and reset the game */
    private void clearAll() {
        redWins = 0;
        yellowWins = 0;
        redCounterLabel.setText(playerXName + " (Red): 0");
        yellowCounterLabel.setText(playerOName + " (Yellow): 0");
        newGame();
    }

    /** Update the game state and turn label */
    private void updateTurn() {
        if (currentState == State.PLAYING) {
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            turnLabel.setText("Turn: " + (currentPlayer == Seed.CROSS ? playerXName + " (Red)" : playerOName + " (Yellow)"));
        } else if (currentState == State.CROSS_WON) {
            redWins++;
            redCounterLabel.setText(playerXName + " (Red): " + redWins);
            turnLabel.setText(playerXName + " (Red) Won!");
        } else if (currentState == State.NOUGHT_WON) {
            yellowWins++;
            yellowCounterLabel.setText(playerOName + " (Yellow): " + yellowWins);
            turnLabel.setText(playerOName + " (Yellow) Won!");
        } else if (currentState == State.DRAW) {
            turnLabel.setText("It's a Draw!");
        }
    }

    /** Update the timer */
    private void updateTimer() {
        timeElapsed++;
        timerLabel.setText("Time: " + timeElapsed + "s");
    }

    /** Return to the home screen */
    private void backToHome() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose();
        }
        new ScreenAwal(); // Go back to ScreenAwal
    }

    /** Custom painting codes on this JPanel */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE); // Set its background color
        board.paint(g); // Ask the game board to paint itself
    }

    /** The entry "main" method */
    public static void play(String playerXName, String playerOName) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect Four");
            frame.setContentPane(new ConnectFour(playerXName, playerOName));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
