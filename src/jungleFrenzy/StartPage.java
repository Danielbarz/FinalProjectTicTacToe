package jungleFrenzy;

import javax.swing.*;
import java.awt.*;

public class StartPage extends JFrame {
    private boolean playAgainstAI;

    public StartPage() {
        setTitle("Welcome to Tic Tac Toe");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        add(welcomeLabel);

        JButton playAgainstAIButton = new JButton("vs AI");
        JButton playAgainstHumanButton = new JButton("vs Human");

        playAgainstAIButton.addActionListener(e -> {
            playAgainstAI = true;
            showDifficultySelection();
        });

        playAgainstHumanButton.addActionListener(e -> {
            playAgainstAI = false;
            showDifficultySelection();
        });

        add(playAgainstAIButton);
        add(playAgainstHumanButton);

        setVisible(true);
    }

    private void showDifficultySelection() {
        dispose();

        JFrame difficultyFrame = new JFrame("Select Difficulty");
        difficultyFrame.setSize(900, 600);
        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        difficultyFrame.setLayout(new FlowLayout());

        JLabel difficultyLabel = new JLabel("Select Difficulty Level:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 50));
        difficultyFrame.add(difficultyLabel);

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        easyButton.addActionListener(e -> {
            new TicTacToeGUI(playAgainstAI, 3); // Start game with 3x3 board
            difficultyFrame.dispose();
        });

        mediumButton.addActionListener(e -> {
            new TicTacToeGUI(playAgainstAI, 4); // Start game with 4x4 board
            difficultyFrame.dispose();
        });

        hardButton.addActionListener(e -> {
            new TicTacToeGUI(playAgainstAI, 5); // Start game with 5x5 board
            difficultyFrame.dispose();
        });

        difficultyFrame.add(easyButton);
        difficultyFrame.add(mediumButton);
        difficultyFrame.add(hardButton);

        difficultyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartPage::new);
    }
}
