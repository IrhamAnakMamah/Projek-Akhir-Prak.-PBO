package View.Menu; // Sesuaikan package

import Controller.ControllerData; // Asumsi controller ada di sini
import Model.Data.ModelData;
import Model.User.ModelUser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import com.toedter.calendar.JDateChooser; // Kalo mau pake

public class AddView extends JFrame {

    ControllerData controller;

    private JTextField namaField;
    private JTextField tanggalField;
    private JButton addButton, cancelButton;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;
    private Font helveticaFont;
    private Font labelFont;

    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);
    private final Color TEXT_FIELD_UNDERLINE_COLOR = new Color(170, 175, 180);
    private final Color TEXT_FIELD_TEXT_COLOR = new Color(230, 235, 240);
    private final Color TEXT_FIELD_CARET_COLOR = TEXT_COLOR_LIGHT;

    private int idCurrentUser; // ID user yang lagi login
    private MenuView parentMenuView; // Referensi ke MenuView buat refresh tabel

    public AddView(int idUser, MenuView parent) {
        this.idCurrentUser = idUser;
        this.parentMenuView = parent;

        setTitle("Add New Persona Data");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setMinimumSize(new Dimension(450, 350));
        setLocationRelativeTo(parent); // Muncul relatif ke MenuView
        setResizable(false);

        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) backgroundImage = new ImageIcon(bgUrl);
            else System.err.println("BG Image GAK KETEMU di AddView!");

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) personaPredictionLogo = new ImageIcon(logoUrl);
            else System.err.println("Logo GAK KETEMU di AddView!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);
        labelFont = helveticaFont.deriveFont(Font.BOLD, 15f);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(30, 30, 39));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(25, 40, 25, 40));
        getContentPane().add(mainPanel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        if (personaPredictionLogo != null) {
            topPanel.add(new JLabel(personaPredictionLogo));
        } else {
            JLabel logoFallback = new JLabel("PERSONA PREDICTION");
            logoFallback.setFont(helveticaFont.deriveFont(Font.BOLD, 28f));
            logoFallback.setForeground(TEXT_COLOR_LIGHT);
            topPanel.add(logoFallback);
        }
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel namaLabel = new JLabel("NAMA:");
        styleFormLabel(namaLabel);
        formPanel.add(namaLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8; gbc.fill = GridBagConstraints.HORIZONTAL;
        namaField = new JTextField();
        styleFormField(namaField);
        formPanel.add(namaField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2; gbc.fill = GridBagConstraints.NONE;
        JLabel tanggalLabel = new JLabel("TANGGAL:");
        styleFormLabel(tanggalLabel);
        formPanel.add(tanggalLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.8; gbc.fill = GridBagConstraints.HORIZONTAL;
        tanggalField = new JTextField();
        styleFormField(tanggalField);
        tanggalField.setToolTipText("Format: YYYY-MM-DD");
        formPanel.add(tanggalField, gbc);

        gbc.gridy = 2; gbc.weighty = 1.0;
        formPanel.add(new JLabel(), gbc); // Spacer

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10,0,0,0));

        controller = new ControllerData(this);
        controller.insertData(idUser);

        cancelButton = new JButton("CANCEL");
        styleTextLikeButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        addButton = new JButton("ADD");
        styleTextLikeButton(addButton);
        addButton.addActionListener(e -> {
            String nama = namaField.getText().trim();
            String tanggal = tanggalField.getText().trim();

            if (nama.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Tanggal Lahir tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Validasi format tanggal (YYYY-MM-DD) bisa ditambah di sini
            // if (!tanggal.matches("\\d{4}-\\d{2}-\\d{2}")) {
            //    JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            //    return;
            // }

            ModelData newData = new ModelData();
            newData.setId_user(idCurrentUser);
            newData.setNama(nama);
            newData.setTanggal(tanggal);
            // Kolom prediksi di tabel data SQL versi lama lu ada, tapi di versi baru gak ada
            // Jika Prediksi (persona_text) digenerate otomatis atau di-link nanti, biarin kosong
            // newData.setPrediksi("Belum ada prediksi"); // Sesuai ModelData jika masih ada field prediksi

            // Panggil ControllerData buat insert
            // Asumsi ControllerData punya method insertData(ModelData data)
            // boolean success = parentMenuView.controller.insertData(newData); // Panggil controller dari parent

            // SIMULASI INSERT (GANTI DENGAN LOGIC CONTROLLER ASLI)
            System.out.println("Simulating ADD data: ID User=" + idCurrentUser + ", Nama=" + nama + ", Tanggal=" + tanggal);
            boolean success = true; // Anggap sukses buat demo

            if (success) {
                JOptionPane.showMessageDialog(this, "Data Persona berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                parentMenuView.refreshTableData(); // Refresh tabel di MenuView
                dispose(); // Tutup AddView
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(addButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleFormLabel(JLabel label) { /* ... sama kayak di EditView ... */
        label.setFont(labelFont);
        label.setForeground(TEXT_COLOR_LIGHT);
    }
    private void styleFormField(JTextField field) { /* ... sama kayak di EditView ... */
        field.setOpaque(false);
        field.setBackground(new Color(0,0,0,0));
        field.setForeground(TEXT_FIELD_TEXT_COLOR);
        field.setCaretColor(TEXT_FIELD_CARET_COLOR);
        field.setFont(helveticaFont.deriveFont(Font.PLAIN, 15f));
        Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, TEXT_FIELD_UNDERLINE_COLOR);
        Border padding = BorderFactory.createEmptyBorder(5, 8, 5, 8);
        field.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }
    private void styleTextLikeButton(JButton button) { /* ... sama kayak di EditView ... */
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 15F));
        button.setForeground(TEXT_COLOR_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8,20,8,20));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setForeground(TEXT_COLOR_BUTTON_HOVER); }
            @Override public void mouseExited(MouseEvent e) { button.setForeground(TEXT_COLOR_LIGHT); }
        });
    }

    public String getInputNama(){
        return "Irham";
    }

    public String getInputTanggal(){
        return "2005-08-05";
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        // Buat testing, kita butuh frame parent dummy buat MenuView
        JFrame dummyParentFrame = new JFrame();
        ModelUser dummyUser = new ModelUser(); dummyUser.setId(1); dummyUser.setNama("TEST USER");
        MenuView dummyMenuView = new MenuView(dummyUser); // Ini cuma buat dapet referensi parent

        SwingUtilities.invokeLater(() -> new AddView(1, dummyMenuView).setVisible(true));
    }
}