package Main;

// Jangan lupa import MainView-nya. Pastikan package View-nya bener.
// Kalo MainView.java ada di package View (langsung di bawah src/View), berarti gini:
import View.MainView;

public class Main {
    public static void main(String[] args) {
        // Sekarang kita panggil MainView yang baru
        new MainView().setVisible(true);
    }
}