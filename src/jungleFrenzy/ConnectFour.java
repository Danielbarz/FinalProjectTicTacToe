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
        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(board.getCanvasWidth() + 200, board.getCanvasHeight()));

        JLabel backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/image/CFBG.gif"))); // Replace with your image path
        backgroundLabel.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel boardPanel = createBoardPanel();
        JPanel statusPanel = createStatusPanel(playerXName, playerOName);

        backgroundLabel.add(boardPanel, BorderLayout.CENTER);
        backgroundLabel.add(statusPanel, BorderLayout.EAST);

        mainPanel.add(backgroundLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Set up a timer
        timer = new Timer(1000, e -> updateTimer());
        timer.start();

        // Add mouse listener for the board
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Directly calculate the column based on raw x-coordinate
                int col = e.getX() / board.getCellSize();

                // Debug: Print mouse click info
                System.out.println("Mouse X: " + e.getX());
                System.out.println("Calculated Col: " + col);

                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        // Process the column click
                        for (int row = Board.ROWS - 1; row >= 0; row--) {
                            if (board.cells[row][col].content == Seed.NO_SEED) {
                                board.cells[row][col].content = currentPlayer;
                                currentState = board.stepGame(currentPlayer, row, col);
                                updateTurn(playerXName, playerOName);
                                break;
                            }
                        }
                    }
                } else {
                    newGame(playerXName, playerOName);
                }
                repaint();
            }
        });
    }

    private JPanel createStatusPanel(String playerXName, String playerOName) {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setPreferredSize(new Dimension(200, board.getCanvasHeight()));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        statusPanel.setOpaque(false);

        // Initialize labels and buttons
        turnLabel = new JLabel("Turn: " + playerXName + " (Red)");
        turnLabel.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        turnLabel.setForeground(new Color(0, 0, 0));

        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setForeground(new Color(0, 0, 0));

        redCounterLabel = new JLabel(playerXName + " (Apple): 0");
        redCounterLabel.setFont(new Font("Book Antiqua", Font.BOLD, 14));
        redCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        redCounterLabel.setForeground(new Color(255, 0, 0));

        yellowCounterLabel = new JLabel(playerOName + " (Banana): 0");
        yellowCounterLabel.setFont(new Font("Book Antiqua", Font.BOLD, 14));
        yellowCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        yellowCounterLabel.setForeground(new Color(255, 222, 0));

        clearButton = new JButton("   ");
        clearButton.setFont(new Font("Book Antiqua", Font.BOLD, 14));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener(e -> clearAll());
        clearButton.setOpaque(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);

        homeButton = new JButton("   ");
        homeButton.setFont(new Font("Book Antiqua", Font.BOLD, 14));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(e -> backToHome());
        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);

        // Add components to the info panel
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(turnLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(timerLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(redCounterLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(yellowCounterLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(clearButton);
        infoPanel.add(Box.createVerticalStrut(25));
        infoPanel.add(homeButton);

        infoPanel.setOpaque(false);

        // Add components to the status panel
        statusPanel.add(infoPanel, BorderLayout.CENTER);

        return statusPanel;
    }

    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.paint(g); // Draw the board
            }
        };
        boardPanel.setOpaque(false);
        boardPanel.setPreferredSize(new Dimension(board.getCanvasWidth(), board.getCanvasHeight()));
        return boardPanel;
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
            turnLabel.setText("Turn: " + (currentPlayer == Seed.CROSS ? playerXName + " (Apple)" : playerOName + " (Banana)"));
        } else if (currentState == State.CROSS_WON) {
            redWins++;
            redCounterLabel.setText(playerXName + " (Apple): " + redWins);
            turnLabel.setText(playerXName + " (Apple) Won!");
        } else if (currentState == State.NOUGHT_WON) {
            yellowWins++;
            yellowCounterLabel.setText(playerOName + " (Banana): " + yellowWins);
            turnLabel.setText(playerOName + " (Banana) Won!");
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
            new ScreenAwal();
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
}
