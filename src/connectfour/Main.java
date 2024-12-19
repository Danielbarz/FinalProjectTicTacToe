package connectfour;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Menjalankan aplikasi dengan layar awal
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScreenAwal(); // Menampilkan layar awal
            }
        });
    }
}

