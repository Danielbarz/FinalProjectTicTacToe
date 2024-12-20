package jungleFrenzy;

import javax.swing.*;
import java.awt.*;

public class ScreenAwal extends JFrame {
    private JButton tictactoe;
    private JButton connectfour;

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

                ImageIcon icon = new ImageIcon(getClass().getResource("/image/HOME.gif"));
                Image img = icon.getImage();

                int width = getWidth();  // Menyesuaikan dengan ukuran window
                int height = getHeight();  // Menyesuaikan dengan ukuran window

                g.drawImage(img, 0, 0, width, height, this);
            }
        };
        layeredPane.setLayout(new BorderLayout());
        layeredPane.setPreferredSize(new Dimension(900, 600));



        // Membuat tombol tictactoe
        tictactoe = new JButton("");
        tictactoe.setContentAreaFilled(false);
        tictactoe.setFocusPainted(false);
        tictactoe.setBorderPainted(false);
        tictactoe.setPreferredSize(new Dimension(155, 80));

        // Membuat tombol connectfour
        connectfour = new JButton("");
        connectfour.setContentAreaFilled(false);
        connectfour.setFocusPainted(false);
        connectfour.setBorderPainted(false);
        connectfour.setPreferredSize(new Dimension(160, 80));

        // Menambahkan action listener untuk memulai permainan
        tictactoe.addActionListener(e -> tictactoeclicked());
        connectfour.addActionListener(e -> connect4clicked());

        // Membuat panel kontrol untuk input nama dan tombol start game
        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();



        // Menambah jarak untuk tombol start
        gbc.gridy = 4;
        gbc.insets = new Insets(75, 0, 0, 25);
        controlsPanel.add(tictactoe, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(75, 10, 0, 0);
        controlsPanel.add(connectfour, gbc);

        // Menambahkan panel kontrol ke layered pane
        layeredPane.add(controlsPanel, BorderLayout.CENTER);

        // Mengatur frame
        add(layeredPane);
        setSize(900, 600);
        setLocationRelativeTo(null);  // Menempatkan window di tengah
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void tictactoeclicked() {


        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan dengan nama pemain
        new TicTacToeVs();
    }
    private void connect4clicked() {
        // Mengambil nama pemain X dan O dari input field


        // Menyembunyikan layar awal
        setVisible(false);

        // Memulai permainan dengan nama pemain
        new NameCF();
    }

}
