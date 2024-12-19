package connectfour;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class Song {
    private static Clip clip;  // Menyimpan referensi ke clip yang sedang diputar

    // Method untuk memutar suara dengan durasi pendek
    public static void playSound(String soundFile) {
        try {
            InputStream audioLink = Song.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            Clip shortClip = AudioSystem.getClip();  // Gunakan clip lokal untuk suara pendek
            shortClip.open(audio);
            shortClip.start();
            // Tidak perlu sleep atau loop, karena clip akan selesai sendiri
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk memutar suara yang berulang terus menerus
    public static void playbackSound(String soundFile) {
        try {
            // Jika clip sedang aktif, hentikan sebelum memutar baru
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            InputStream audioLink = Song.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            clip = AudioSystem.getClip();  // Simpan clip yang sedang diputar untuk musik latar
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Suara berulang terus menerus
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghentikan musik latar
    public static void stopPlayback() {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();  // Hentikan pemutaran
                clip.close(); // Tutup sumber audio
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
