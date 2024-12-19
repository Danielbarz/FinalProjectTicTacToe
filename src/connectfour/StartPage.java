package connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage extends JFrame {
    private boolean playAgainstAI; // To store whether the user wants to play against AI or Human

    public StartPage() {
        setTitle("Welcome to Tic Tac Toe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel);

        // Buttons for choosing opponent type
        JButton playAgainstAIButton = new JButton("Play Against AI");
        JButton playAgainstHumanButton = new JButton("Play Against Human");

        playAgainstAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainstAI = true; // Set to play against AI
                showDifficultySelection(); // Show difficulty selection
            }
        });

        playAgainstHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainstAI = false; // Set to play against Human
                showDifficultySelection(); // Show difficulty selection
            }
        });

        add(playAgainstAIButton);
        add(playAgainstHumanButton);

        setVisible(true);
    }

    private void showDifficultySelection() {
        // Close the current window
        dispose();

        // Create a new window for difficulty selection
        JFrame difficultyFrame = new JFrame("Select Difficulty");
        difficultyFrame.setSize(400, 300);
        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        difficultyFrame.setLayout(new FlowLayout());

        JLabel difficultyLabel = new JLabel("Select Difficulty Level:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyFrame.add(difficultyLabel);

        // Buttons for difficulty levels
        JButton easyButton = new JButton("Easy (3x3)");
        JButton mediumButton = new JButton("Medium (4x4)");
        JButton hardButton = new JButton("Hard (5x5)");

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3); // Start game with 3x3 board
                difficultyFrame.dispose();
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(4); // Start game with 4x4 board
                difficultyFrame.dispose();
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(5); // Start game with 5x5 board
                difficultyFrame.dispose();
            }
        });

        difficultyFrame.add(easyButton);
        difficultyFrame.add(mediumButton);
        difficultyFrame.add(hardButton);

        difficultyFrame.setVisible(true);
    }

    private void startGame(int boardSize) {
        // Start the game with the selected board size and opponent type
        new TicTacToeGUI(playAgainstAI, boardSize);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartPage::new);
    }
}