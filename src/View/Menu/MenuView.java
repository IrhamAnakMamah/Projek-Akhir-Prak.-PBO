// irhamanakmamah/projek-akhir-prak.-pbo/Projek-Akhir-Prak.-PBO-master/src/View/Menu/MenuView.java
package View.Menu;

import Controller.ControllerData;
import Model.Data.ModelData;
import Model.User.ModelUser;
import View.Form.LoginView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuView extends JFrame {

    ControllerData controller;
    private ModelUser loggedInUser;

    // Komponen UI yang berubah
    private JPanel dataListPanel; // Panel utama untuk menampung baris-baris data
    private JScrollPane scrollPane; // Scroll pane untuk dataListPanel

    private JLabel logoLabel;
    private JLabel welcomeLabel;
    private JButton addButton, editButton, deleteButton, logoutButton;

    private Font helveticaFont;
    private Font customHeaderTableFont;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;

    // Konstanta warna dan style tetap sama
    private final Color DATA_AREA_BORDER_COLOR = new Color(200, 160, 80, 230);
    private final int DATA_AREA_CORNER_RADIUS = 35;
    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);

    public MenuView(ModelUser user) {
        this.loggedInUser = user;

        setTitle("PersonaPrediction - Menu Utama");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setMinimumSize(new Dimension(850, 650));
        setLocationRelativeTo(null);
        setResizable(true);

        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) backgroundImage = new ImageIcon(bgUrl);
            else System.err.println("BG Image GAK KETEMU di MenuView!");

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) personaPredictionLogo = new ImageIcon(logoUrl);
            else System.err.println("Logo GAK KETEMU di MenuView!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);
        customHeaderTableFont = helveticaFont.deriveFont(Font.BOLD, 16f);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15)) {
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

        // === TOP BAR (Tidak berubah) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        if (personaPredictionLogo != null) logoLabel = new JLabel(personaPredictionLogo);
        else {
            logoLabel = new JLabel("PersonaPrediction");
            logoLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 32f));
            logoLabel.setForeground(TEXT_COLOR_LIGHT);
        }
        topPanel.add(logoLabel, BorderLayout.WEST);

        String userName = (this.loggedInUser != null && this.loggedInUser.getNama() != null && !this.loggedInUser.getNama().isEmpty()) ? this.loggedInUser.getNama().toUpperCase() : "GUEST";
        welcomeLabel = new JLabel("WELCOME, " + userName);
        welcomeLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 16f));
        welcomeLabel.setForeground(TEXT_COLOR_LIGHT);
        topPanel.add(welcomeLabel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // === CENTER AREA: DATA DISPLAY (INI YANG DIUBAH TOTAL) ===
        JPanel centerContentPanel = new JPanel(new BorderLayout(0, 0));
        centerContentPanel.setOpaque(false);
        centerContentPanel.setBorder(new EmptyBorder(15, 5, 0, 5)); // Kurangin padding atas

        // Panel luar yang punya border rounded
        JPanel dataAreaWithRoundedBorder = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(10, 12, 18, 50));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), DATA_AREA_CORNER_RADIUS, DATA_AREA_CORNER_RADIUS);
                g2.setColor(DATA_AREA_BORDER_COLOR);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, DATA_AREA_CORNER_RADIUS, DATA_AREA_CORNER_RADIUS);
                g2.dispose();
            }
        };
        dataAreaWithRoundedBorder.setOpaque(false);
        dataAreaWithRoundedBorder.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Panel untuk menampung list data, dengan layout vertikal
        dataListPanel = new JPanel();
        dataListPanel.setLayout(new BoxLayout(dataListPanel, BoxLayout.Y_AXIS));
        dataListPanel.setOpaque(false);

        // Header "manual" di atas list
        JPanel headerPanel = createHeaderPanel();
        dataListPanel.add(headerPanel);

        // Bungkus list panel dengan JScrollPane
        scrollPane = new JScrollPane(dataListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Biar scroll lebih smooth

        dataAreaWithRoundedBorder.add(scrollPane, BorderLayout.CENTER);
        centerContentPanel.add(dataAreaWithRoundedBorder, BorderLayout.CENTER);
        mainPanel.add(centerContentPanel, BorderLayout.CENTER);

        // === BOTTOM BUTTONS (Tidak berubah) ===
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomButtonPanel.setOpaque(false);
        bottomButtonPanel.setBorder(new EmptyBorder(25,0,10,0));

        addButton = new JButton("ADD DATA");
        editButton = new JButton("EDIT DATA");
        deleteButton = new JButton("DELETE DATA");
        logoutButton = new JButton("LOGOUT");

        styleTextLikeButton(addButton);
        styleTextLikeButton(editButton);
        styleTextLikeButton(deleteButton);
        styleTextLikeButton(logoutButton);

        bottomButtonPanel.add(addButton);
        bottomButtonPanel.add(createSeparatorLabel());
        bottomButtonPanel.add(editButton);
        bottomButtonPanel.add(createSeparatorLabel());
        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(createSeparatorLabel());
        bottomButtonPanel.add(logoutButton);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        // Controller akan mengisi dataListPanel
        controller = new ControllerData(this);
        controller.showAllData();

        // Action listener button bawah masih sama, TAPI perlu penyesuaian untuk edit/delete
        // karena kita tidak bisa "getSelectedRow" lagi. Ini contoh, bisa dikembangkan.
        addButton.addActionListener(e -> new AddView(loggedInUser.getId(), this).setVisible(true));

        editButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Edit perlu cara baru untuk memilih data.", "Info", JOptionPane.INFORMATION_MESSAGE));
        deleteButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Delete perlu cara baru untuk memilih data.", "Info", JOptionPane.INFORMATION_MESSAGE));

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Yakin mau logout, bro?", "Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    // Method untuk membuat header palsu
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(10,10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel namaHeader = new JLabel("Nama");
        styleHeaderLabel(namaHeader);
        header.add(namaHeader, BorderLayout.WEST);

        JLabel tanggalHeader = new JLabel("Tanggal Lahir");
        styleHeaderLabel(tanggalHeader);
        header.add(tanggalHeader, BorderLayout.CENTER);

        // Spacer buat kolom prediksi
        JLabel prediksiHeader = new JLabel("Prediksi");
        styleHeaderLabel(prediksiHeader);
        prediksiHeader.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(prediksiHeader, BorderLayout.EAST);

        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getPreferredSize().height)); // Penting untuk BoxLayout

        return header;
    }

    // Method baru untuk menambahkan satu baris data ke list panel
    public void addDataRow(ModelData data) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 10));
        rowPanel.setOpaque(false);
        rowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255,255,255,40))); // Garis pemisah tipis
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Biar tinggi tiap baris sama
        rowPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Label Nama
        JLabel namaLabel = new JLabel(data.getNama());
        styleDataLabel(namaLabel);
        rowPanel.add(namaLabel, BorderLayout.WEST);

        // Label Tanggal
        JLabel tanggalLabel = new JLabel(data.getTanggal());
        styleDataLabel(tanggalLabel);
        rowPanel.add(tanggalLabel, BorderLayout.CENTER);

        // Tombol Lihat
        JButton lihatButton = new JButton("LIHAT");
        styleLihatButton(lihatButton);
        lihatButton.addActionListener(e -> {
            System.out.println("Tombol 'Lihat' diklik untuk: " + data.getNama());
            String teksPrediksi = "Ini adalah contoh teks prediksi untuk " + data.getNama() + ". Implementasi fetching dari DB diperlukan.";
            ImageIcon gambarZodiak = null;
            new PrediksiView(data.getNama(), data.getTanggal(), teksPrediksi, gambarZodiak).setVisible(true);
        });
        rowPanel.add(lihatButton, BorderLayout.EAST);

        dataListPanel.add(rowPanel);
    }

    // Method baru untuk membersihkan list sebelum di-refresh
    public void clearDataList() {
        dataListPanel.removeAll();
        dataListPanel.add(createHeaderPanel()); // Tambahkan header lagi setelah dibersihkan
        dataListPanel.revalidate();
        dataListPanel.repaint();
    }

    public void refreshTableData() {
        if (controller != null) {
            controller.showAllData();
        }
    }

    // --- STYLING HELPERS ---
    private void styleHeaderLabel(JLabel label) {
        label.setFont(customHeaderTableFont);
        label.setForeground(TEXT_COLOR_LIGHT);
    }

    private void styleDataLabel(JLabel label) {
        label.setFont(helveticaFont.deriveFont(15f));
        label.setForeground(new Color(210, 215, 220));
    }

    private void styleLihatButton(JButton button) {
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 12f));
        button.setForeground(TEXT_COLOR_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setForeground(TEXT_COLOR_BUTTON_HOVER); }
            @Override public void mouseExited(MouseEvent e) { button.setForeground(TEXT_COLOR_LIGHT); }
        });
    }

    private void styleTextLikeButton(JButton button) {
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 15F));
        button.setForeground(TEXT_COLOR_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8,15,8,15));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setForeground(TEXT_COLOR_BUTTON_HOVER); }
            @Override public void mouseExited(MouseEvent e) { button.setForeground(TEXT_COLOR_LIGHT); }
        });
    }

    private JLabel createSeparatorLabel() {
        JLabel separator = new JLabel("|");
        separator.setFont(helveticaFont.deriveFont(Font.BOLD, 15F));
        separator.setForeground(TEXT_COLOR_LIGHT);
        return separator;
    }

    public int getIdUser(){ return loggedInUser.getId(); }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        ModelUser dummyUser = new ModelUser();
        dummyUser.setId(1);
        dummyUser.setNama("IRHAM UHUY");
        SwingUtilities.invokeLater(() -> new MenuView(dummyUser).setVisible(true));
    }
}