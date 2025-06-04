package Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlayMusic implements Runnable {

    private Clip clip;
    String link;
    public PlayMusic() {
        link = "C:\\Users\\ASUS\\IdeaProjects\\Projek-Akhir-Prak.-PBO\\src\\Main\\background.wav";

    }

    @Override
    public void run() {
        Sound(link);
        try {
            // Contoh: Biarkan berjalan selama 30 detik
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stopSound();
        }
    }

    public void Sound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.err.println("File audio tidak ditemukan: " + soundFilePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            // Untuk memutar berulang kali (loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // Untuk memutar sekali
            // clip.start();

            System.out.println("Memutar backsound...");

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Format audio tidak didukung: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Line audio tidak tersedia: " + e.getMessage());
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            System.out.println("Backsound dihentikan.");
        }
    }
}
