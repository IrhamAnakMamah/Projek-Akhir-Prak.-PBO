package View.Form; // Pastikan package-nya bener ya

import Controller.ControllerUser;
import View.MainView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    ControllerUser controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel titleLabel;
    private Font helveticaFont;
    private ImageIcon backgroundImage;

    public LoginView() {
        setTitle("Login - Persona Prediction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load background image
        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) {
                backgroundImage = new ImageIcon(bgUrl);
            } else {
                System.err.println("Background image GAK KETEMU di LoginView! Path: /resources/BackgroundMainView.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error pas ngeload background di LoginView: " + e.getMessage());
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(48, 52, 59)); // Fallback color
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 30, 20, 30));
        getContentPane().add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14); //

        titleLabel = new JLabel("USER LOGIN");
        titleLabel.setForeground(new Color(220, 220, 225));
        titleLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 8, 15, 8);
        mainPanel.add(titleLabel, gbc);
        gbc.insets = new Insets(8, 8, 8, 8); // Reset insets

        JLabel usernameLabel = new JLabel("Username");
        styleFormLabel(usernameLabel);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        styleFormField(usernameField);
        gbc.gridy = 2;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        styleFormLabel(passwordLabel);
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        styleFormField(passwordField);
        gbc.gridy = 4;
        mainPanel.add(passwordField, gbc);

        // Panel buat tombol LOGIN | BACK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Jarak antar item 15px
        buttonPanel.setOpaque(false); // Panelnya transparan
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8); // Jarak atas panel tombol
        mainPanel.add(buttonPanel, gbc);

        loginButton = new JButton("LOGIN");
        styleTextLikeButton(loginButton);
        buttonPanel.add(loginButton);

        // Ini dia PEMBATAS | nya
        JLabel separatorLabelLogin = new JLabel("|");
        separatorLabelLogin.setForeground(new Color(210, 210, 220)); // Warna disamain teks tombol
        separatorLabelLogin.setFont(helveticaFont.deriveFont(Font.PLAIN, 15F)); // Font dan ukuran disamain
        buttonPanel.add(separatorLabelLogin);

        backButton = new JButton("BACK");
        styleTextLikeButton(backButton);
        buttonPanel.add(backButton);

        controller = new ControllerUser(this); //

        backButton.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        loginButton.addActionListener(e -> controller.cekLogin());
        passwordField.addActionListener(e -> loginButton.doClick());

        JRootPane rootPane = SwingUtilities.getRootPane(loginButton);
        if (rootPane != null) {
            rootPane.setDefaultButton(loginButton);
        }

        setVisible(true);
    }

    private void styleFormLabel(JLabel label) {
        label.setForeground(new Color(180, 190, 200));
        label.setFont(helveticaFont.deriveFont(Font.PLAIN, 13F));
    }

    private void styleFormField(JTextField field) {
        field.setOpaque(false);
        field.setBackground(new Color(0,0,0,0)); // Background fully transparent
        field.setForeground(new Color(225, 230, 235)); // Warna teks inputan
        field.setCaretColor(new Color(210, 215, 220)); // Warna caret
        field.setFont(helveticaFont.deriveFont(Font.PLAIN, 15F));
        // Border garis bawah aja
        Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(150, 160, 170));
        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5); // Padding dalem field
        field.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }

    private void styleTextLikeButton(JButton button) {
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 15F)); // Ukuran font tombol
        button.setForeground(new Color(210, 210, 220)); // Warna teks tombol
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color originalColor = button.getForeground();
        Color hoverColor = new Color(150, 180, 255); // Warna pas di-hover

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

    public String getNama() { return usernameField.getText(); } //
    public String getPassword() { return new String(passwordField.getPassword()); } //
    public void Reset() { usernameField.setText(""); passwordField.setText(""); usernameField.requestFocus(); } //

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}