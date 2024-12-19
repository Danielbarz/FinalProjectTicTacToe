package connectfour;

import javax.swing.*;
import java.awt.*;

public class TicTacToeVs extends JFrame {
    private JButton vsai;
    private JButton players;
    private JButton back;

    private JPanel backgroundPanel;

    public TicTacToeVs() {
        setTitle("Choose Player");
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
                ImageIcon icon = new ImageIcon(getClass().getResource("/image/TICTACTOE.gif"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Membuat tombol VS AI
        vsai = new JButton("");
        vsai.setContentAreaFilled(false);
        vsai.setFocusPainted(false);
        vsai.setBorderPainted(false);
        vsai.setOpaque(false); // Transparan
        vsai.setPreferredSize(new Dimension(300, 130));

        // Membuat tombol 2 Players
        players = new JButton("");
        players.setContentAreaFilled(false);
        players.setFocusPainted(false);
        players.setBorderPainted(false);
        players.setOpaque(false); // Transparan
        players.setPreferredSize(new Dimension(420, 130));

        // Membuat tombol Back
        back = new JButton("");
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(false); // Transparan
        back.setPreferredSize(new Dimension(210, 50));

        // Menambahkan aksi ke tombol
        vsai.addActionListener(e -> vsaiclicked());
        players.addActionListener(e -> playersclicked());
        back.addActionListener(e -> backisclicked());

        // Membuat panel kontrol
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Menambahkan tombol ke panel kontrol
        // Tombol VS AI
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, -250, 70, 100);
        controlsPanel.add(vsai, gbc);

        // Tombol 2 Players
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 70, -100);
        controlsPanel.add(players, gbc);

        // Tombol Back
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -10, 250, 120); // Sesuaikan posisi
        controlsPanel.add(back, gbc);

        // Menambahkan panel kontrol ke panel latar belakang
        backgroundPanel.add(controlsPanel);

        setVisible(true);
    }

    private void vsaiclicked() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan Tic Tac Toe
        System.out.println("Tic Tac Toe (VS AI) game started!");
    }

    private void playersclicked() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan 2 Players
        new NameTTT();
    }

    private void backisclicked() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Kembali ke layar awal
        new ScreenAwal();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeVs::new);
    }
}
