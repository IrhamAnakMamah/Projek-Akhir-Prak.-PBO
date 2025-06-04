package View.Menu; // Sesuaikan package

import Controller.ControllerData; // Asumsi controller
import Model.Data.ModelData;
import Model.User.ModelUser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditView extends JFrame {

    ControllerData controller;

    private JTextField namaField;
    private JTextField tanggalField;
    private JButton saveButton, cancelButton;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;
    private Font helveticaFont;
    private Font labelFont;

    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);
    private final Color TEXT_FIELD_UNDERLINE_COLOR = new Color(170, 175, 180);
    private final Color TEXT_FIELD_TEXT_COLOR = new Color(230, 235, 240);
    private final Color TEXT_FIELD_CARET_COLOR = TEXT_COLOR_LIGHT;

    private int idDataToEdit;
    private MenuView parentMenuView; // Referensi ke MenuView buat refresh

    public EditView(ModelData dataToEdit, MenuView parent) {
        this.idDataToEdit = dataToEdit.getId_data();
        this.parentMenuView = parent;

        setTitle("Edit Persona Data");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setMinimumSize(new Dimension(450, 350));
        setLocationRelativeTo(parent);
        setResizable(false);

        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) backgroundImage = new ImageIcon(bgUrl);
            else System.err.println("BG Image GAK KETEMU di EditView!");

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) personaPredictionLogo = new ImageIcon(logoUrl);
            else System.err.println("Logo GAK KETEMU di EditView!");
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
        namaField.setText(dataToEdit.getNama()); // Isi data awal
        formPanel.add(namaField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2; gbc.fill = GridBagConstraints.NONE;
        JLabel tanggalLabel = new JLabel("TANGGAL:");
        styleFormLabel(tanggalLabel);
        formPanel.add(tanggalLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.8; gbc.fill = GridBagConstraints.HORIZONTAL;
        tanggalField = new JTextField();
        styleFormField(tanggalField);
        tanggalField.setToolTipText("Format: YYYY-MM-DD");
        tanggalField.setText(dataToEdit.getTanggal()); // Isi data awal
        formPanel.add(tanggalField, gbc);

        gbc.gridy = 2; gbc.weighty = 1.0;
        formPanel.add(new JLabel(), gbc); // Spacer

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10,0,0,0));

        cancelButton = new JButton("CANCEL");
        styleTextLikeButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());
        // ini ya
        controller = new ControllerData(this);
        controller.updateData(13);

        saveButton = new JButton("SAVE");
        styleTextLikeButton(saveButton);
        saveButton.addActionListener(e -> {
            String nama = namaField.getText().trim();
            String tanggal = tanggalField.getText().trim();

            if (nama.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Tanggal Lahir tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Validasi format tanggal bisa ditambah

            ModelData updatedData = new ModelData();
            updatedData.setId_data(idDataToEdit);
            updatedData.setId_user(parentMenuView.getIdUser()); // Ambil id_user dari parent (logged in user)
            updatedData.setNama(nama);
            updatedData.setTanggal(tanggal);
            // updatedData.setPrediksi(...); // Jika masih ada field prediksi di ModelData

            // Panggil ControllerData buat update
            // boolean success = parentMenuView.controller.updateData(updatedData);

            // SIMULASI UPDATE (GANTI DENGAN LOGIC CONTROLLER ASLI)
            System.out.println("Simulating UPDATE data: ID Data=" + idDataToEdit + ", Nama=" + nama + ", Tanggal=" + tanggal);
            boolean success = true; // Anggap sukses

            if (success) {
                JOptionPane.showMessageDialog(this, "Data Persona berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                parentMenuView.refreshTableData(); // Refresh tabel di MenuView
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleFormLabel(JLabel label) { /* ... sama kayak di AddView ... */
        label.setFont(labelFont);
        label.setForeground(TEXT_COLOR_LIGHT);
    }
    private void styleFormField(JTextField field) { /* ... sama kayak di AddView ... */
        field.setOpaque(false);
        field.setBackground(new Color(0,0,0,0));
        field.setForeground(TEXT_FIELD_TEXT_COLOR);
        field.setCaretColor(TEXT_FIELD_CARET_COLOR);
        field.setFont(helveticaFont.deriveFont(Font.PLAIN, 15f));
        Border underline = BorderFactory.createMatteBorder(0, 0, 1, 0, TEXT_FIELD_UNDERLINE_COLOR);
        Border padding = BorderFactory.createEmptyBorder(5, 8, 5, 8);
        field.setBorder(BorderFactory.createCompoundBorder(underline, padding));
    }
    private void styleTextLikeButton(JButton button) { /* ... sama kayak di AddView ... */
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
        return "Ham";
    }

    public String getInputTanggal(){
        return "2005-08-05";
    }
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        ModelData dummyData = new ModelData();
        dummyData.setId_data(101); dummyData.setNama("Budi Edit"); dummyData.setTanggal("2000-01-15");
        ModelUser dummyUser = new ModelUser(); dummyUser.setId(1); dummyUser.setNama("TEST USER");
        MenuView dummyMenuView = new MenuView(dummyUser); // Buat referensi parent

        SwingUtilities.invokeLater(() -> new EditView(dummyData, dummyMenuView).setVisible(true));
    }
}