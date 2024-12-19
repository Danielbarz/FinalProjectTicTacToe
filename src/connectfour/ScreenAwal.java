package connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenAwal extends JFrame {
    private JButton startGame;
    private JTextField playerXNameField, playerONameField;
    private JLayeredPane layeredPane;

    public ScreenAwal() {
        // Memulai musik latar
        Song.playbackSound("audio/Background.wav");

        setTitle("Connect Four");

        // Inisialisasi layered pane untuk background GIF
        layeredPane = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon icon = new ImageIcon(getClass().getResource("/image/CONNECTFOUR.gif"));
                Image img = icon.getImage();

                int width = getWidth();  // Menyesuaikan dengan ukuran window
                int height = getHeight();  // Menyesuaikan dengan ukuran window

                g.drawImage(img, 0, 0, width, height, this);
            }
        };
        layeredPane.setLayout(new BorderLayout());
        layeredPane.setPreferredSize(new Dimension(900, 600));

        // Membuat label dan input untuk nama pemain X dan O
        JLabel nameLabelX = new JLabel("Masukkan Nama Pemain X:");
        nameLabelX.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabelX.setForeground(Color.WHITE);

        JLabel nameLabelO = new JLabel("Masukkan Nama Pemain O:");
        nameLabelO.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabelO.setForeground(Color.WHITE);

        playerXNameField = new JTextField(20);  // Field untuk input nama Pemain X
        playerXNameField.setFont(new Font("Arial", Font.PLAIN, 16));

        playerONameField = new JTextField(20);  // Field untuk input nama Pemain O
        playerONameField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Membuat tombol "Start Game"
        startGame = new JButton("Start Game");
        startGame.setFont(new Font("Arial", Font.BOLD, 20));
        startGame.setForeground(Color.WHITE);
        startGame.setBackground(Color.RED);
        startGame.setFocusPainted(false);
        startGame.setPreferredSize(new Dimension(200, 50));

        // Menambahkan action listener untuk memulai permainan
        startGame.addActionListener(e -> startGameClicked());

        // Membuat panel kontrol untuk input nama dan tombol start game
        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Memberikan lebih banyak jarak antar elemen
        gbc.anchor = GridBagConstraints.CENTER;

        // Menambahkan label dan text field ke panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlsPanel.add(nameLabelX, gbc);

        gbc.gridy = 1;
        controlsPanel.add(playerXNameField, gbc);

        gbc.gridy = 2;
        controlsPanel.add(nameLabelO, gbc);

        gbc.gridy = 3;
        controlsPanel.add(playerONameField, gbc);

        // Menambah jarak untuk tombol start
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 10, 10, 10);
        controlsPanel.add(startGame, gbc);

        // Menambahkan panel kontrol ke layered pane
        layeredPane.add(controlsPanel, BorderLayout.CENTER);

        // Mengatur frame
        add(layeredPane);
        setSize(900, 600);
        setLocationRelativeTo(null);  // Menempatkan window di tengah
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startGameClicked() {
        // Mengambil nama pemain X dan O dari input field
        String playerXName = playerXNameField.getText().trim();
        String playerOName = playerONameField.getText().trim();

        // Mengecek apakah nama pemain X dan O tidak kosong
        if (playerXName.isEmpty() || playerOName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pemain X dan O tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hentikan musik latar
        Song.stopPlayback();

        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan dengan nama pemain
        ConnectFour.play(playerXName, playerOName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScreenAwal::new);
    }
}
