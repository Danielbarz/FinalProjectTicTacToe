package connectfour;

import javax.swing.*;
import java.awt.*;

public class NameCF extends JFrame {
    private JTextField player1Field;
    private JTextField player2Field;
    private JButton back;
    private JButton startGame;

    private JPanel backgroundPanel;

    public NameCF() {
        setTitle("Enter Name");
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
                ImageIcon icon = new ImageIcon(getClass().getResource("/image/nameCF.gif"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Membuat tombol Back
        back = new JButton("");
        back.setPreferredSize(new Dimension(120, 60));
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(false); // Transparan
        back.addActionListener(e -> backisclicked());


        // Membuat input nama player 1
        player1Field = new JTextField();
        player1Field.setOpaque(false); // Transparan
        player1Field.setBorder(null);
        player1Field.setPreferredSize(new Dimension(150, 50));
        player1Field.setFont(new Font("Forte", Font.BOLD, 22));

        // Membuat input nama player 2
        player2Field = new JTextField();
        player2Field.setOpaque(false); // Transparan
        player2Field.setBorder(null);
        player2Field.setPreferredSize(new Dimension(150, 50));
        player2Field.setFont(new Font("Forte", Font.BOLD, 22));

        // Tombol Start Game
        startGame = new JButton("");
        startGame.setContentAreaFilled(false);
        startGame.setFocusPainted(false);
        startGame.setBorderPainted(false);
        startGame.setOpaque(false); // Transparan
        startGame.setPreferredSize(new Dimension(150, 40));
        startGame.addActionListener(e -> startGameClicked());

        // Membuat panel kontrol untuk input nama dan tombol
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(35, 0, 0, 320);
        controlsPanel.add(player1Field, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(70, 0, 0, 320);
        controlsPanel.add(player2Field, gbc);

        // Tambahkan Tombol Start Game
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(40, 0, 40, 320);
        controlsPanel.add(startGame, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 170, 220); // Sesuaikan posisi
        controlsPanel.add(back, gbc);

        // Menambahkan panel kontrol ke panel latar belakang
        backgroundPanel.add(controlsPanel);

        // Menampilkan frame
        setVisible(true);
    }

    private void backisclicked() {
        // Menutup layar ini dan kembali ke layar sebelumnya
        setVisible(false);
        new Connect4Vs(); // Ganti dengan layar sebelumnya
    }

    private void startGameClicked() {
        // Mendapatkan nama pemain dari input field
        String player1Name = player1Field.getText();
        String player2Name = player2Field.getText();

        // Validasi input
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both player names!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Memulai permainan dengan nama pemain
        System.out.println("Player 1: " + player1Name);
        System.out.println("Player 2: " + player2Name);
        setVisible(false);

        // Panggil layar permainan di sini
        // new GameScreen(player1Name, player2Name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NameCF::new);
    }
}
