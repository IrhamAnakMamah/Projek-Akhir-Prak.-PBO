package View; // Pastikan package view lu bener namanya ini

import View.Form.LoginView;
import View.Form.RegisterView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// Gak perlu import File, FontFormatException, IOException kalo gak load custom font dari file

public class MainView extends JFrame {

    private JButton loginButton;
    private JButton registerButton;
    private JLabel titleLabel; // Ini buat nampilin logo
    private JLabel subTitleLabel;
    private Font helveticaFont;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;

    public MainView() {
        setTitle("Persona Prediction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Ukuran window mungkin perlu lu sesuaikan lagi sama gambar background & logo lu
        setSize(550, 450); // Contoh ukuran, bisa diganti
        setLocationRelativeTo(null);
        setResizable(false);

        // Load images dari folder /resources/
        try {
            // Path buat ngambil dari folder 'resources' yang ada di dalem 'src' (atau di classpath)
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) {
                backgroundImage = new ImageIcon(bgUrl);
            } else {
                System.err.println("Background image GAK KETEMU! Cek path: /resources/BackgroundMainView.png");
            }

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) {
                personaPredictionLogo = new ImageIcon(logoUrl);
            } else {
                System.err.println("Logo image GAK KETEMU! Cek path: /resources/logoMainView.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Waduh, error pas ngeload gambar nih: " + e.getMessage());
        }

        // Panel utama
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Kalo background gagal, fallback ke warna item aja
                    g.setColor(new Color(30, 30, 30)); // Warna gelap
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        getContentPane().add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Font standar
        helveticaFont = new Font("Helvetica", Font.PLAIN, 14); //

        // Judul aplikasi (pake logo)
        if (personaPredictionLogo != null) {
            titleLabel = new JLabel(personaPredictionLogo);
            // Kalo mau atur ukuran logo dari kode (tapi mending resize file aslinya):
            // Image img = personaPredictionLogo.getImage().getScaledInstance(LEBAR_LOGO, TINGGI_LOGO, Image.SCALE_SMOOTH);
            // titleLabel.setIcon(new ImageIcon(img));
        } else {
            // Kalo logo gak ada, fallback pake teks
            titleLabel = new JLabel("Persona Prediction");
            titleLabel.setForeground(new Color(225, 225, 230));
            titleLabel.setFont(new Font("Serif", Font.BOLD, 40)); // Font fallback, bisa diganti
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span 2 kolom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10); // Kasih jarak bawah lebih buat logo
        mainPanel.add(titleLabel, gbc);

        // Sub-judul (opsional, kalo gak mau pake, tinggal apus aja dari sini sampe add-nya)
        subTitleLabel = new JLabel("Please Login or Register to continue.");
        subTitleLabel.setForeground(new Color(180, 190, 200)); // Warna disesuaikan sama background
        subTitleLabel.setFont(helveticaFont.deriveFont(Font.PLAIN, 13));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10); // Jarak bawah buat subtitle
        mainPanel.add(subTitleLabel, gbc);
        gbc.insets = new Insets(10, 10, 10, 10); // Reset jarak normal

        // Panel buat tombol "LOGIN | REGISTER"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Jarak antar item 5px
        buttonPanel.setOpaque(false); // Bikin transparan

        loginButton = new JButton();
        styleTextLikeButton(loginButton, "LOGIN");
        buttonPanel.add(loginButton);

        JLabel separatorLabel = new JLabel("|");
        separatorLabel.setForeground(new Color(180, 190, 200));
        separatorLabel.setFont(helveticaFont.deriveFont(Font.PLAIN, 15)); // Ukuran font separator
        buttonPanel.add(separatorLabel);

        registerButton = new JButton();
        styleTextLikeButton(registerButton, "REGISTER");
        buttonPanel.add(registerButton);

        gbc.gridy = 2; // Di bawah subtitle
        mainPanel.add(buttonPanel, gbc);

        // --- Action Listeners buat tombol ---
        loginButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose(); // Tutup window MainView
        });

        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            this.dispose(); // Tutup window MainView
        });
    }

    // Method buat styling tombol jadi kayak teks biasa
    private void styleTextLikeButton(JButton button, String text) {
        button.setText(text);
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 15)); // Font & ukuran tombol
        button.setForeground(new Color(210, 210, 220)); // Warna teks tombol
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor jadi tangan

        // Efek hover simpel: ganti warna teks
        Color originalColor = button.getForeground();
        Color hoverColor = new Color(150, 180, 255); // Warna pas di-hover, bisa diganti

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(originalColor);
            }
        });
    }

    // Main method buat nge-test tampilan MainView ini doang
    public static void main(String[] args) {
        // Coba pake LookAndFeel sistem biar lebih nyatu sama OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Gagal set LookAndFeel: " + e);
        }

        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}