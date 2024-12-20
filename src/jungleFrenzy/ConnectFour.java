package jungleFrenzy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFour extends JPanel {
    private static final long serialVersionUID = 1L;

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

    public ConnectFour(String playerXName, String playerOName) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH + 200, Board.CANVAS_HEIGHT)); // Adjust for status panel

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setPreferredSize(new Dimension(200, Board.CANVAS_HEIGHT));
        statusPanel.setBackground(new Color(240, 240, 240));

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

        clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.addActionListener(e -> clearAll());

        homeButton = new JButton("Back to Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(e -> backToHome());

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

        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;

        timer = new Timer(1000, e -> updateTimer());
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / Cell.SIZE;
                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);
    }

    private void updateTurn(String playerXName, String playerOName) {
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

    private void updateTimer() {
        timeElapsed++;
        timerLabel.setText("Time: " + timeElapsed + "s");
    }

    private void clearAll() {
        redWins = 0;
        yellowWins = 0;
        redCounterLabel.setText("Red: 0");
        yellowCounterLabel.setText("Yellow: 0");
        newGame(null, null);
    }

    private void backToHome() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    }

    private void newGame(String playerXName, String playerOName) {
        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
        turnLabel.setText("Turn: " + playerXName + " (Red)");
        timeElapsed = 0;
    }

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
