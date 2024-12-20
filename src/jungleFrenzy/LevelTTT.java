package jungleFrenzy;

import javax.swing.*;
import java.awt.*;

public class LevelTTT extends JFrame {
    private JButton Easy;
    private JButton Medium;
    private JButton Hard;
    private JButton back;

    private JPanel backgroundPanel;

    private final String player1Name;
    private final String player2Name;

    public LevelTTT(String p1, String p2) {
        this.player1Name = p1;
        this.player2Name = p2;

        setTitle("Choose Level");
        setSize(900, 600);
        setLocationRelativeTo(null);  // Menempatkan window di tengah
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Membuat panel latar belakang
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Memuat gambar latar belakang GIF
                ImageIcon icon = new ImageIcon(getClass().getResource("/image/LevelTTT.gif"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Membuat tombol Easy
        Easy = new JButton("");
        Easy.setContentAreaFilled(false);
        Easy.setFocusPainted(false);
        Easy.setBorderPainted(false);
        Easy.setOpaque(false); // Transparan
        Easy.setPreferredSize(new Dimension(150, 100));
        Easy.addActionListener(e -> startGame(3));

        // Membuat tombol Medium
        Medium = new JButton("");
        Medium.setContentAreaFilled(false);
        Medium.setFocusPainted(false);
        Medium.setBorderPainted(false);
        Medium.setOpaque(false); // Transparan
        Medium.setPreferredSize(new Dimension(150, 100));
        Medium.addActionListener(e -> startGame(4));

        // Membuat tombol Hard
        Hard = new JButton("");
        Hard.setContentAreaFilled(false);
        Hard.setFocusPainted(false);
        Hard.setBorderPainted(false);
        Hard.setOpaque(false); // Transparan
        Hard.setPreferredSize(new Dimension(150, 100));
        Hard.addActionListener(e -> startGame(5));

        // Membuat tombol Back
        back = new JButton("");
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(false); // Transparan
        back.setPreferredSize(new Dimension(110, 50));
        back.addActionListener(e -> backisclicked());

        // Membuat panel kontrol
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Easy button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, -150, 130, 30);
        controlsPanel.add(Easy, gbc);

        // Medium button
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 80, 130, 80);
        controlsPanel.add(Medium, gbc);

        // Hard button
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 130, 100);
        controlsPanel.add(Hard, gbc);

        // Back button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -10, 250, 120);
        controlsPanel.add(back, gbc);

        // Menambahkan panel kontrol ke panel latar belakang
        backgroundPanel.add(controlsPanel);

        setVisible(true);
    }

    private void startGame(int boardSize) {
        setVisible(false);
        new TicTacToeGUI(false, boardSize, player1Name, player2Name);
    }

    private void backisclicked() {
        setVisible(false);
        new NameTTT();
    }
}
