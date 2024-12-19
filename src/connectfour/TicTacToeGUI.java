package connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private TicTacToe game;
    private Player currentPlayer;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft; // Time left for the current player in seconds

    public TicTacToeGUI(boolean playAgainstAI, int boardSize) {
        game = new TicTacToe(boardSize);
        game.setPlayerX(new HumanPlayer('X')); // Set player X as human
        if (playAgainstAI) {
            game.setPlayerO(new AIPlayer('O')); // Set player O as AI
        } else {
            game.setPlayerO(new HumanPlayer('O')); // Set player O as human
        }
        currentPlayer = game.getPlayerX(); // Start with player X
        buttons = new JButton[boardSize][boardSize];
        initializeUI(boardSize);
        startTimer(30); // Set timer for 30 seconds
    }

    private void initializeUI(int boardSize) {
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(boardSize, boardSize));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                buttonPanel.add(buttons[i][j]);
            }
        }

        timerLabel = new JLabel("Time Left: 30", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current game window
                new StartPage(); // Return to the start page
            }
        });

        add(timerLabel, BorderLayout.NORTH); // Add timer label at the top
        add(buttonPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH); // Add the back button at the bottom

        setVisible(true);
    }

    private void startTimer(int seconds) {
        timeLeft = seconds;
        timerLabel.setText("Time Left: " + timeLeft);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Switching to the next player.");
                    switchPlayer();
                }
            }
        });
        timer.start();
    }

    private void switchPlayer() {
        // Stop the timer for the current player
        timer.stop();

        // Switch the current player
        currentPlayer = (currentPlayer == game.getPlayerX()) ? game.getPlayerO() : game.getPlayerX();

        // Restart the timer for the next player
        startTimer(30); // Set timer for 30 seconds

        // If the current player is AI, make the move automatically
        if (currentPlayer instanceof AIPlayer) {
            currentPlayer.makeMove(game);
            updateBoard(); // Update the board after AI makes its move

            // Check for win or tie after AI's move
            if (game.checkForWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getSymbol() + " wins!");
                resetGame();
            } else if (game.isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a tie!");
                resetGame();
            } else {
                // If the game continues, switch back to the human player
                switchPlayer(); // Call switchPlayer again to allow the human player to play
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.isValidMove(row, col) && currentPlayer instanceof HumanPlayer) {
                game.placeMove(row, col, currentPlayer.getSymbol());
                buttons[row][col].setText(String.valueOf(currentPlayer.getSymbol()));

                if (game.checkForWin()) {
                    timer.stop(); // Stop the timer
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getSymbol() + " wins!");
                    resetGame();
                } else if (game.isBoardFull()) {
                    timer.stop(); // Stop the timer
                    JOptionPane.showMessageDialog(null, "The game is a tie!");
                    resetGame();
                } else {
                    switchPlayer(); // Switch to the next player
                }
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                buttons[i][j].setText(String.valueOf(game.getBoard()[i][j]));
            }
        }
        if (game.checkForWin()) {
            timer.stop(); // Stop the timer
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.getSymbol() + " wins!");
            resetGame();
        } else if (game.isBoardFull()) {
            timer.stop(); // Stop the timer
            JOptionPane.showMessageDialog(null, "The game is a tie!");
            resetGame();
        }
    }

    private void resetGame() {
        dispose(); // Close the current game window
        new StartPage(); // Restart the game
    }
}