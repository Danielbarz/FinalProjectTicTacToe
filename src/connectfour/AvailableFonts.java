package connectfour;

import java.awt.GraphicsEnvironment;

public class AvailableFonts {
    public static void main(String[] args) {
        // Mendapatkan semua font yang tersedia di sistem
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        // Menampilkan nama-nama font
        System.out.println("Font yang tersedia di Java:");
        for (String font : fonts) {
            System.out.println(font);
        }
    }
}
