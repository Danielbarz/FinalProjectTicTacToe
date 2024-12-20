package jungleFrenzy;


import javax.swing.*;
import java.awt.*;

public class LevelTTT extends JFrame {
    private JButton Easy;
    private JButton Medium;
    private JButton Hard;
    private JButton back;

    private JPanel backgroundPanel;

    public LevelTTT() {
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
                ImageIcon icon = new ImageIcon(getClass().getResource("/image/LevelTTT.gif"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Membuat tombol VS AI
        Easy = new JButton("");
        Easy.setContentAreaFilled(false);
        Easy.setFocusPainted(false);
        Easy.setBorderPainted(false);
        Easy.setOpaque(false); // Transparan
        Easy.setPreferredSize(new Dimension(150, 100));

        // Membuat tombol 2 Players
        Medium = new JButton("");
        Medium.setContentAreaFilled(false);
        Medium.setFocusPainted(false);
        Medium.setBorderPainted(false);
        Medium.setOpaque(false); // Transparan
        Medium.setPreferredSize(new Dimension(150, 100));

        Hard = new JButton("");
        Hard.setContentAreaFilled(false);
        Hard.setFocusPainted(false);
        Hard.setBorderPainted(false);
        Hard.setOpaque(false); // Transparan
        Hard.setPreferredSize(new Dimension(150, 100));

        // Membuat tombol Back
        back = new JButton("");
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(false); // Transparan
        back.setPreferredSize(new Dimension(110, 50));

        // Menambahkan aksi ke tombol
        Easy.addActionListener(e -> easy());
        Medium.addActionListener(e -> Medium());
        Hard.addActionListener(e -> Hard());
        back.addActionListener(e -> backisclicked());

        // Membuat panel kontrol
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        controlsPanel.setLayout(new GridBagLayout());

// Easy button
        gbc.gridx = 1; // Kolom pertama
        gbc.gridy = 3; // Baris pertama
        gbc.insets = new Insets(10, -150, 130, 30); // Margin sekitar tombol
        controlsPanel.add(Easy, gbc);

// Medium button
        gbc.gridx = 2; // Kolom kedua
        gbc.gridy = 3; // Baris pertama
        gbc.insets = new Insets(20, 80, 130, 80);
        controlsPanel.add(Medium, gbc);

// Hard button
        gbc.gridx = 3; // Kolom ketiga
        gbc.gridy = 3; // Baris pertama
        gbc.insets = new Insets(20, 10, 130, 100);
        controlsPanel.add(Hard, gbc);

        // Tombol Back
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -10, 250, 120); // Sesuaikan posisi
        controlsPanel.add(back, gbc);

        // Menambahkan panel kontrol ke panel latar belakang
        backgroundPanel.add(controlsPanel);

        setVisible(true);
    }

    private void easy() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan Tic Tac Toe
        new TicTacToeGUI(false, 3);
    }
    private void Medium() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan Tic Tac Toe
        new TicTacToeGUI(false, 4);
    }

    private void Hard() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan Tic Tac Toe
        new TicTacToeGUI(false, 5);
    }

    private void backisclicked() {
        // Menyembunyikan layar awal
        setVisible(false);

        // Kembali ke layar awal
        new TicTacToeVs();
    }
    public static void main(String args[]){
        new LevelTTT();
    };
}


