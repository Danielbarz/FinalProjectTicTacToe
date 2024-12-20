package jungleFrenzy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class NameCF extends JFrame {
    private JTextField player1Field;
    private JTextField player2Field;
    private JButton back;
    private JButton startGame;

    private JPanel backgroundPanel;

    public NameCF() {
        setTitle("Enter Name");
        setSize(900, 600);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create background panel with a custom GIF
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/image/nameCF.gif"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Create Back button
        back = new JButton("");
        back.setPreferredSize(new Dimension(120, 60));
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(false); // Transparent
        back.addActionListener(e -> backisclicked());

        // Create input fields for player names
        player1Field = new JTextField();
        player1Field.setOpaque(false); // Transparent
        player1Field.setBorder(null);
        player1Field.setPreferredSize(new Dimension(150, 50));
        player1Field.setFont(new Font("Book Antiqua", Font.BOLD, 22));

        player2Field = new JTextField();
        player2Field.setOpaque(false); // Transparent
        player2Field.setBorder(null);
        player2Field.setPreferredSize(new Dimension(150, 50));
        player2Field.setFont(new Font("Book Antiqua", Font.BOLD, 22));

        // Create Start Game button
        startGame = new JButton("");
        startGame.setContentAreaFilled(false);
        startGame.setFocusPainted(false);
        startGame.setBorderPainted(false);
        startGame.setOpaque(false); // Transparent
        startGame.setPreferredSize(new Dimension(150, 40));
        startGame.addActionListener(e -> startGameClicked());

        // Create control panel for inputs and buttons
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(40, 0, 0, 320);
        controlsPanel.add(player1Field, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(60, 0, 0, 320);
        controlsPanel.add(player2Field, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(40, 0, 40, 320);
        controlsPanel.add(startGame, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 170, 220);
        controlsPanel.add(back, gbc);

        backgroundPanel.add(controlsPanel);
        setVisible(true);
    }

    private void backisclicked() {
        // Navigate back to the home screen
        setVisible(false);
        new ScreenAwal(); // Replace with the home screen class
    }

    private void startGameClicked() {
        // Get player names
        String player1Name = player1Field.getText().trim();
        String player2Name = player2Field.getText().trim();

        // Validate input
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both player names!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Launch ConnectFour with the entered names
        System.out.println("Player 1: " + player1Name);
        System.out.println("Player 2: " + player2Name);
        setVisible(false);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect Four");
            ConnectFour panel = new ConnectFour(player1Name, player2Name);

            // Lock the window size to 1280x720
            frame.setSize(1280, 720);
            frame.setResizable(false); // Prevent resizing

            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);

            // Maintain 16:9 aspect ratio during resizing
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Dimension size = frame.getSize();
                    int width = size.width;
                    int height = (int) (width / 16.0 * 9); // Calculate height based on 16:9 ratio
                    frame.setSize(width, height); // Adjust the window size
                }
            });
        });
    }
}
