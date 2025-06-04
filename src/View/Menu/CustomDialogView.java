package View.Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomDialogView {

    // Method utama buat nampilin dialog custom kita
    public static void showStyledMessageDialog(Component parentComponent, String message, String title, int messageType, ImageIcon backgroundImage, Font baseFont) {
        // Bikin JDialog modal (nge-block window parent)
        JDialog dialog = new JDialog(parentComponent instanceof Frame ? (Frame) parentComponent : null, title, true);
        dialog.setResizable(false);
        dialog.setUndecorated(true); // Biar ilangin title bar default, kalo mau lebih custom lagi

        // Panel utama dengan background image
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15)) { // Kasih sedikit gap
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Gambar background, dibikin nge-fit
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback kalo background gak ada
                    g.setColor(new Color(38, 42, 49)); // Warna gelap kayak di screenshot message lu
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(25, 25, 20, 25)); // Padding dalem dialog

        // Panel buat icon dan message (biar icon di kiri, message di kanan/tengah)
        JPanel messageAreaPanel = new JPanel(new BorderLayout(15, 0)); // Jarak icon dan message
        messageAreaPanel.setOpaque(false); // Transparan

        // Icon (ngambil dari UIManager biar konsisten sama tipe message)
        Icon typeIcon = null;
        if (messageType == JOptionPane.ERROR_MESSAGE) {
            typeIcon = UIManager.getIcon("OptionPane.errorIcon");
        } else if (messageType == JOptionPane.INFORMATION_MESSAGE) {
            typeIcon = UIManager.getIcon("OptionPane.informationIcon"); // Kayak di screenshot lu
        } else if (messageType == JOptionPane.WARNING_MESSAGE) {
            typeIcon = UIManager.getIcon("OptionPane.warningIcon");
        } else if (messageType == JOptionPane.QUESTION_MESSAGE) {
            typeIcon = UIManager.getIcon("OptionPane.questionIcon");
        }

        if (typeIcon != null) {
            JLabel iconLabel = new JLabel(typeIcon);
            messageAreaPanel.add(iconLabel, BorderLayout.WEST);
        }

        // Label buat message-nya
        // Pake HTML biar bisa multi-line dan text-align center
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message.replace("\n", "<br>") + "</div></html>");
        messageLabel.setForeground(new Color(210, 215, 220)); // Warna teks message
        messageLabel.setFont(baseFont.deriveFont(Font.PLAIN, 14f)); // Font message
        messageAreaPanel.add(messageLabel, BorderLayout.CENTER);

        mainPanel.add(messageAreaPanel, BorderLayout.CENTER);

        // Panel buat tombol OK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5)); // Tombol di tengah, ada jarak atas dikit
        buttonPanel.setOpaque(false); // Transparan

        JButton okButton = new JButton("OK");
        styleDialogButton(okButton, baseFont); // Pake style tombol teks kita

        okButton.addActionListener(e -> dialog.dispose()); // Tutup dialog pas tombol OK diklik
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.pack(); // Ukuran dialog disesuaikan sama isinya

        // Kalo mau kasih ukuran minimal atau default yg lebih gede dikit
        int minWidth = 350;
        int currentWidth = dialog.getWidth();
        int currentHeight = dialog.getHeight();
        if (currentWidth < minWidth) {
            dialog.setSize(minWidth, currentHeight + 10); // Tambah tinggi dikit juga
        } else {
            dialog.setSize(currentWidth + 20, currentHeight + 10); // Lebarin dikit aja
        }


        dialog.setLocationRelativeTo(parentComponent); // Muncul di tengah parent
        dialog.setVisible(true);
    }

    // Method buat styling tombol di dialog (mirip styleTextLikeButton)
    private static void styleDialogButton(JButton button, Font baseFont) {
        button.setFont(baseFont.deriveFont(Font.BOLD, 13F)); // Font tombol
        button.setForeground(new Color(210, 210, 220)); // Warna teks tombol
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false); // Gak pake border
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor tangan

        // Padding dalem tombol biar area kliknya enak
        button.setBorder(new EmptyBorder(8, 20, 8, 20));


        Color originalColor = button.getForeground();
        Color hoverColor = new Color(150, 180, 255); // Warna hover

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
}