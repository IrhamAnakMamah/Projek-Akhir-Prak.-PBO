package View;

import View.Form.LoginView;
import View.Form.RegisterView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;

public class MainView extends JFrame {

    private JButton loginButton;
    private JButton registerButton;
    private JLabel titleLabel;
    private JLabel subTitleLabel;
    private Font helveticaFont;

    public MainView() {
        setTitle("Persona Prediction"); // Judul window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350); // Ukuran window
        setLocationRelativeTo(null); // Posisi di tengah layar
        setResizable(false); // Gak bisa di-resize

        // Panel utama
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Background gradasi
                Color color1 = new Color(48, 52, 59);
                Color color2 = new Color(56, 60, 67);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout()); // Layout utama
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Padding
        getContentPane().add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Jarak antar elemen

        // Font
        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);

        // Judul utama
        titleLabel = new JLabel("Persona Prediction");
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span 2 kolom
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Sub-judul
        subTitleLabel = new JLabel("Please Login or Register to continue.");
        subTitleLabel.setForeground(new Color(180, 180, 180));
        subTitleLabel.setFont(helveticaFont.deriveFont(Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10); // Jarak bawah
        mainPanel.add(subTitleLabel, gbc);
        gbc.insets = new Insets(10, 10, 10, 10); // Reset jarak

        // Panel buat tombol Login & Register (biar sejajar)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Layout horizontal, tengah, jarak 15px
        buttonPanel.setOpaque(false); // Transparan

        // Tombol Login
        loginButton = new JButton("Login");
        styleButton(loginButton, new Color(80, 100, 180), new Color(60, 80, 160)); // Style tombol
        buttonPanel.add(loginButton); // Masuk ke panel tombol

        // Tombol Register
        registerButton = new JButton("Register");
        styleButton(registerButton, new Color(90, 90, 90), new Color(70, 70, 70)); // Style tombol
        buttonPanel.add(registerButton); // Masuk ke panel tombol

        // Tambah panel tombol ke panel utama
        gbc.gridy = 2; // Di bawah sub-judul
        gbc.gridwidth = 2; // Span 2 kolom
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        // Aksi tombol Login
        loginButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose(); // Tutup window ini
        });

        // Aksi tombol Register
        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            this.dispose(); // Tutup window ini
        });
    }

    // Method buat styling tombol
    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setForeground(new Color(220, 220, 220));
        button.setBackground(bgColor);
        button.setBorder(new RoundedBorder(20)); // Border bulet
        button.setFocusPainted(false);
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40)); // Ukuran tombol
        // Efek hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    // Class buat bikin border bulet
    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(114, 137, 218)); // Warna border
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }

    // Kalo mau tes MainView ini aja, bisa uncomment main method di bawah
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}