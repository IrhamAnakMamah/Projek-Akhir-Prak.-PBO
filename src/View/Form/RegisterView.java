package View.Form; // Pastikan package-nya bener ya

import Controller.ControllerUser;
import View.MainView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView extends JFrame {

    ControllerUser controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backButton;
    private JLabel titleLabel;
    private Font helveticaFont;
    private ImageIcon backgroundImage;

    public RegisterView() {
        setTitle("Register - Persona Prediction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load background image
        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) {
                backgroundImage = new ImageIcon(bgUrl);
            } else {
                System.err.println("Background image GAK KETEMU di RegisterView! Path: /resources/BackgroundMainView.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error pas ngeload background di RegisterView: " + e.getMessage());
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

        titleLabel = new JLabel("CREATE ACCOUNT");
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

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        styleFormLabel(confirmPasswordLabel);
        gbc.gridy = 5;
        mainPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        styleFormField(confirmPasswordField);
        gbc.gridy = 6;
        mainPanel.add(confirmPasswordField, gbc);

        // Panel buat tombol REGISTER | BACK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Jarak antar item 15px
        buttonPanel.setOpaque(false); // Panelnya transparan
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8); // Jarak atas panel tombol
        mainPanel.add(buttonPanel, gbc);

        registerButton = new JButton("REGISTER");
        styleTextLikeButton(registerButton);
        buttonPanel.add(registerButton);

        // Ini dia PEMBATAS | nya
        JLabel separatorLabelRegister = new JLabel("|");
        separatorLabelRegister.setForeground(new Color(210, 210, 220)); // Warna disamain teks tombol
        separatorLabelRegister.setFont(helveticaFont.deriveFont(Font.PLAIN, 15F)); // Font dan ukuran disamain
        buttonPanel.add(separatorLabelRegister);

        backButton = new JButton("BACK");
        styleTextLikeButton(backButton);
        buttonPanel.add(backButton);

        controller = new ControllerUser(this); //

        backButton.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        registerButton.addActionListener(e -> {
            String pass = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());
            if (getNama().isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterView.this, "Username and Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (pass.equals(confirmPass)) {
                controller.cekRegister();
            } else {
                JOptionPane.showMessageDialog(RegisterView.this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                confirmPasswordField.setText("");
                passwordField.requestFocus();
            }
        });

        confirmPasswordField.addActionListener(e -> registerButton.doClick());

        JRootPane rootPane = SwingUtilities.getRootPane(registerButton);
        if (rootPane != null) {
            rootPane.setDefaultButton(registerButton);
        }

        setVisible(true);
    }

    private void styleFormLabel(JLabel label) {
        label.setForeground(new Color(180, 190, 200));
        label.setFont(helveticaFont.deriveFont(Font.PLAIN, 13F));
    }

    private void styleFormField(JTextField field) {
        field.setOpaque(false);
        field.setBackground(new Color(0,0,0,0));
        field.setForeground(new Color(225, 230, 235));
        field.setCaretColor(new Color(210, 215, 220));
        field.setFont(helveticaFont.deriveFont(Font.PLAIN, 15F));
        Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(150, 160, 170));
        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        field.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }

    private void styleTextLikeButton(JButton button) {
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 15F));
        button.setForeground(new Color(210, 210, 220));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color originalColor = button.getForeground();
        Color hoverColor = new Color(150, 180, 255);

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
    public void Reset() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        usernameField.requestFocus();
    } //

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}