package jungleFrenzy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFour extends JPanel {
    private static final long serialVersionUID = 1L;

    private Board board;         // The game board that holds the state of the game
    private State currentState;  // The current state of the game (PLAYING, DRAW, etc.)
    private Seed currentPlayer;  // The current player (Red or Yellow)
    private JLabel turnLabel;    // Label to display the current player's turn
    private JLabel redCounterLabel;  // Label to track Red player wins
    private JLabel yellowCounterLabel; // Label to track Yellow player wins
    private JLabel timerLabel;   // Label to display the game timer
    private JButton clearButton; // Button to reset the game
    private JButton homeButton;  // Button to navigate back to the home screen
    private Timer timer;         // Timer to count elapsed game time
    private int timeElapsed;     // Time elapsed in seconds
    private int redWins = 0;     // Number of wins for Red
    private int yellowWins = 0;  // Number of wins for Yellow

    // Constructor to initialize the game interface and state
    public ConnectFour(String playerXName, String playerOName) {

        // Initialize the game board and state
        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(board.getCanvasWidth() + 200, board.getCanvasHeight())); // Adjust panel size

        // Create and configure the status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setPreferredSize(new Dimension(200, board.getCanvasHeight()));
        statusPanel.setBackground(new Color(255, 255, 255));

        // Initialize UI components for status panel
        turnLabel = new JLabel("Turn: " + playerXName + " (Red)");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        redCounterLabel = new JLabel(playerXName + " (Red): 0");
        redCounterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        redCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        yellowCounterLabel = new JLabel(playerOName + " (Yellow): 0");
        yellowCounterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        yellowCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize Clear All button
        clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener(e -> clearAll());

        // Initialize Back to Home button
        homeButton = new JButton("Back to Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(e -> backToHome());

        // Add components to the status panel
        statusPanel.add(Box.createVerticalStrut(20));
        statusPanel.add(turnLabel);
        statusPanel.add(Box.createVerticalStrut(20));
        statusPanel.add(timerLabel);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(redCounterLabel);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(yellowCounterLabel);
        statusPanel.add(Box.createVerticalStrut(20));
        statusPanel.add(clearButton);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(homeButton);

        add(statusPanel, BorderLayout.EAST);

        // Set up a timer to update every second
        timer = new Timer(1000, e -> updateTimer());
        timer.start();

        // Add mouse interaction for the game board
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / Cell.SIZE; // Determine the column clicked
                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        for (int row = Board.ROWS - 1; row >= 0; row--) { // Start from the bottom row
                            if (board.cells[row][col].content == Seed.NO_SEED) {
                                board.cells[row][col].content = currentPlayer; // Place the player's seed
                                currentState = board.stepGame(currentPlayer, row, col); // Check the game state
                                updateTurn(playerXName, playerOName); // Update the turn or announce results
                                break;
                            }
                        }
                    }
                } else {
                    newGame(playerXName, playerOName); // Restart the game if finished
                }
                repaint(); // Redraw the board
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int availableWidth = getWidth() - 200; // Adjust for status panel width
        int availableHeight = getHeight();
        board.updateDimensions(availableWidth, availableHeight);
        board.paint(g);
    }

    // Update the turn label and game state based on the current player's move
    private void updateTurn(String playerXName, String playerOName) {
        if (currentState == State.PLAYING) {
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS; // Switch players
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

    // Update the timer every second
    private void updateTimer() {
        timeElapsed++;
        timerLabel.setText("Time: " + timeElapsed + "s");
    }

    // Reset the scores and start a new game
    private void clearAll() {
        redWins = 0;
        yellowWins = 0;
        redCounterLabel.setText("Red: 0");
        yellowCounterLabel.setText("Yellow: 0");
        newGame(null, null);
    }

    // Close the current frame and navigate back to the home screen
    private void backToHome() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    }

    // Start a new game by resetting the board and game state
    private void newGame(String playerXName, String playerOName) {
        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
        turnLabel.setText("Turn: " + playerXName + " (Red)");
        timeElapsed = 0;
    }

    // Launch the game within a JFrame
    public static void play(String playerXName, String playerOName) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect Four");
            ConnectFour panel = new ConnectFour(playerXName, playerOName);
            frame.setContentPane(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    // Shortcut main method to quickly launch the game
    public static void main(String[] args) {
        play("Player 1", "Player 2");
    }
}
