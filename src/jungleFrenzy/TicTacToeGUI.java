package jungleFrenzy;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private TicTacToe game;
    private Player currentPlayer;
    private String player1Name;
    private String player2Name;

    private JLabel timerLabel;
    private JLabel turnLabel; // Label to display whose turn it is
    private Timer timer;
    private int timeLeft; // Time left for the current player in seconds
    private final int boardSize; // Store the board size for reset functionality
    private final boolean playAgainstAI; // Store whether the game is against AI

    public TicTacToeGUI(boolean playAgainstAI, int boardSize, String player1Name, String player2Name) {
        this.playAgainstAI = playAgainstAI;
        this.boardSize = boardSize;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        initializeGame();
        initializeUI();
        startTimer();
    }


    private void initializeGame() {
        game = new TicTacToe(boardSize); // Create a new game with the selected board size
        game.setPlayerX(new HumanPlayer(Seed.CROSS)); // Set player X as CROSS
        if (playAgainstAI) {
            game.setPlayerO(new AIPlayer(Seed.NOUGHT)); // Set player O as NOUGHT
        } else {
            game.setPlayerO(new HumanPlayer(Seed.NOUGHT)); // Set player O as NOUGHT
        }
        currentPlayer = game.getPlayerX(); // Start with player X
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout()); // Use BorderLayout for the main panel

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/image/TTTBgNew.gif")); // Adjust path as needed
        backgroundLabel.setLayout(new BorderLayout());

        // Grid Panel for Tic Tac Toe with fixed size
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(boardSize, boardSize, 0, 0));
        gamePanel.setOpaque(false);
        gamePanel.setPreferredSize(new Dimension(650, 650)); // Set a fixed preferred size

        buttons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBorder(new LineBorder(Color.BLACK, 5));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }

        // Centering the game panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);
        centerPanel.add(gamePanel);

        // Create top panel with the menu button and timer label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel leftTopPanel = getjPanel(); // The panel with Back button
        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new BoxLayout(rightTopPanel, BoxLayout.Y_AXIS));
        rightTopPanel.setOpaque(false);
        rightTopPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50)); // Margin on the right

        timerLabel = new JLabel("Time Left: 30");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text

        rightTopPanel.add(timerLabel);

        topPanel.add(leftTopPanel, BorderLayout.WEST);
        topPanel.add(rightTopPanel, BorderLayout.EAST);

        // Create bottom panel with the reset button and turn info
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel leftBottomPanel = new JPanel(); // Panel for reset button
        leftBottomPanel.setOpaque(false);
        leftBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align left
        JButton resetButton = new JButton("   ");
        resetButton.setFont(new Font("Book Antiqua", Font.PLAIN, 22));
        resetButton.setOpaque(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setForeground(Color.BLACK);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> resetGame());
        leftBottomPanel.add(resetButton);

        JPanel rightBottomPanel = new JPanel();
        rightBottomPanel.setLayout(new BoxLayout(rightBottomPanel, BoxLayout.Y_AXIS));
        rightBottomPanel.setOpaque(false);

        turnLabel = new JLabel("Turn: " + player1Name);
        turnLabel.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text

        rightBottomPanel.add(turnLabel);
        rightBottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 50)); // Margin on the right

        bottomPanel.add(leftBottomPanel, BorderLayout.WEST);
        bottomPanel.add(rightBottomPanel, BorderLayout.EAST);

        // Add elements to background label
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);
        backgroundLabel.add(topPanel, BorderLayout.NORTH); // Add top panel to the north of the background
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the south of the background

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel getjPanel() {
        JButton backButton = new JButton("   ");
        backButton.setFont(new Font("Book Antiqua", Font.PLAIN, 22));
        backButton.setOpaque(false); // Membuat tombol transparan
        backButton.setContentAreaFilled(false); // Menghapus latar belakang tombol
        backButton.setBorderPainted(false); // Menghapus border tombol
        backButton.setForeground(Color.BLACK); // Warna teks tombol menjadi hitam
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new ScreenAwal(); // Pastikan StartPage() ada dalam proyek
        });

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new BoxLayout(leftTopPanel, BoxLayout.Y_AXIS)); // Vertikal
        leftTopPanel.setOpaque(false);
        leftTopPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Margin atas dan kiri
        leftTopPanel.add(backButton);

        return leftTopPanel;
    }

    private void startTimer() {
        timeLeft = 30;
        timerLabel.setText("Time Left: " + timeLeft);
        timer = new Timer(900, new ActionListener() {
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
        timer.stop(); // Stop the timer for the current player
        currentPlayer = (currentPlayer == game.getPlayerX()) ? game.getPlayerO() : game.getPlayerX();
        String currentPlayerName = (currentPlayer == game.getPlayerX()) ? player1Name : player2Name;
        turnLabel.setText("Turn: " + currentPlayerName); // Update the turn label
        startTimer(); // Restart the timer for the next player

        if (currentPlayer instanceof AIPlayer) {
            currentPlayer.makeMove(game);
            updateBoard();

            if (game.checkForWin()) {
                JOptionPane.showMessageDialog(null, currentPlayerName + " wins!");
                resetGame();
            } else if (game.isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a tie!");
                resetGame();
            } else {
                switchPlayer(); // Switch back to the human player
            }
        }
    }


    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.isValidMove(row, col)) {
                game.placeMove(row, col, currentPlayer.getSeed());
                Image img = currentPlayer.getSeed().getImage();
                if (img != null) {
                    img = img.getScaledInstance(buttons[row][col].getWidth(), buttons[row][col].getHeight(), Image.SCALE_SMOOTH);
                    buttons[row][col].setIcon(new ImageIcon(img));
                } else {
                    System.err.println("Image not found for: " + currentPlayer.getSeed().getDisplayName());
                }

                if (game.checkForWin()) {
                    timer.stop();
                    String winnerName = (currentPlayer == game.getPlayerX()) ? player1Name : player2Name;
                    JOptionPane.showMessageDialog(null, winnerName + " wins!");
                    resetGame();
                } else if (game.isBoardFull()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "The game is a tie!");
                    resetGame();
                } else {
                    switchPlayer();
                }
            }
        }
    }


    private void updateBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Seed cellSeed = game.getBoard()[i][j];
                if (cellSeed != Seed.NO_SEED) {
                    Image img = cellSeed.getImage();
                    if (img != null) {
                        img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
                        buttons[i][j].setIcon(new ImageIcon(img));
                    }
                } else {
                    buttons[i][j].setIcon(null); // Hapus ikon
                }
            }
        }
    }



    private void resetGame() {
        timer.stop(); // Stop the timer
        initializeGame(); // Reinitialize the game state
        resetBoard(); // Clear the board
        turnLabel.setText("Turn: " + player1Name); // Reset the turn label
        startTimer(); // Restart the timer
    }

    private void resetBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setIcon(null); // Hapus gambar
            }
        }
    }

}
