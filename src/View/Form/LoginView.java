package View.Form;

import Controller.ControllerUser;
import View.MainView; // Ditambahin import MainView

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border; // Udah ada
// import java.awt.geom.RoundRectangle2D; // Komen ini gak dipake, bisa dihapus kalo mau

public class LoginView extends JFrame {

    ControllerUser controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton; // Diganti dari registerButton jadi backButton biar jelas
    private JLabel titleLabel;
    private Font helveticaFont;

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

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
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        getContentPane().add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);

        // Judul
        titleLabel = new JLabel("Persona Prediction");
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Label username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(new Color(200, 200, 200));
        usernameLabel.setFont(helveticaFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameLabel, gbc);

        // Field username
        usernameField = new JTextField(20);
        usernameField.setBackground(new Color(60, 60, 60));
        usernameField.setForeground(new Color(230, 230, 230));
        usernameField.setCaretColor(new Color(230, 230, 230));
        usernameField.setBorder(new RoundedTextFieldBorder(10));
        usernameField.setFont(helveticaFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usernameField, gbc);

        // Label password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(new Color(200, 200, 200));
        passwordLabel.setFont(helveticaFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordLabel, gbc);

        // Field password
        passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(60, 60, 60));
        passwordField.setForeground(new Color(230, 230, 230));
        passwordField.setCaretColor(new Color(230, 230, 230));
        passwordField.setBorder(new RoundedTextFieldBorder(10));
        passwordField.setFont(helveticaFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        // Tombol Login
        loginButton = new JButton("Login");
        loginButton.setForeground(new Color(220, 220, 220));
        loginButton.setBackground(new Color(80, 100, 180)); // Warna biru
        loginButton.setBorder(new RoundedBorder(25));
        loginButton.setFocusPainted(false);
        loginButton.setFont(helveticaFont.deriveFont(Font.BOLD, 12));
        loginButton.setPreferredSize(new Dimension(80, 25));
        loginButton.addMouseListener(new ButtonHoverEffect(loginButton, new Color(60, 80, 160)));
        buttonPanel.add(loginButton);

        // Tombol Back (sebelumnya Register)
        backButton = new JButton("Back"); // Teks diubah jadi "Back"
        backButton.setForeground(new Color(220, 220, 220));
        backButton.setBackground(new Color(90, 90, 90)); // Warna abu (cocok buat back)
        backButton.setBorder(new RoundedBorder(25));
        backButton.setFocusPainted(false);
        backButton.setFont(helveticaFont.deriveFont(Font.BOLD, 12));
        backButton.setPreferredSize(new Dimension(80, 25));
        backButton.addMouseListener(new ButtonHoverEffect(backButton, new Color(70, 70, 70)));
        buttonPanel.add(backButton);

        controller = new ControllerUser(this);

        // Aksi tombol Back (sebelumnya Register)
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainView().setVisible(true); // Balik ke MainView
                dispose(); // Tutup LoginView
            }
        });

        // Aksi tombol Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cekLogin();
            }
        });

        setVisible(true);
    }

    // --- Inner classes (RoundedBorder, RoundedTextFieldBorder, ButtonHoverEffect) tetep sama ---
    private static class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) { this.radius = radius; }
        @Override public Insets getBorderInsets(Component c) { return new Insets(this.radius, this.radius, this.radius, this.radius); }
        @Override public boolean isBorderOpaque() { return false; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(114, 137, 218));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }

    private static class RoundedTextFieldBorder implements Border {
        private int radius;
        RoundedTextFieldBorder(int radius) { this.radius = radius; }
        @Override public Insets getBorderInsets(Component c) { return new Insets(radius / 2, radius, radius / 2, radius); }
        @Override public boolean isBorderOpaque() { return false; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(230, 230, 230));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }

    private static class ButtonHoverEffect extends MouseAdapter {
        private JButton button;
        private Color originalColor;
        private Color hoverColor;
        public ButtonHoverEffect(JButton button, Color hoverColor) {
            this.button = button;
            this.originalColor = button.getBackground();
            this.hoverColor = hoverColor;
        }
        @Override public void mouseEntered(MouseEvent e) { button.setBackground(hoverColor); }
        @Override public void mouseExited(MouseEvent e) { button.setBackground(originalColor); }
    }

    public String getNama() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public void Reset() { usernameField.setText(""); passwordField.setText(""); }

    public static void main(String[] args) { new LoginView(); }
}