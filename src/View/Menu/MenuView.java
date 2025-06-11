package View.Menu;

import Controller.ControllerData;
import Model.Data.DAOData;
import Model.Data.ModelData;
import Model.User.ModelUser;
import View.Form.LoginView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuView extends JFrame {

    ControllerData controller;
    private ModelUser loggedInUser;
    private JPanel dataListPanel;
    private JScrollPane scrollPane;
    private JLabel logoLabel;
    private JLabel welcomeLabel;
    private JButton addButton, logoutButton;

    private Font helveticaFont;
    private Font customHeaderTableFont;
    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;

    private final Color DATA_AREA_BORDER_COLOR = new Color(200, 160, 80, 230);
    private final int DATA_AREA_CORNER_RADIUS = 35;
    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);

    private GridBagConstraints gbcData;

    public MenuView(ModelUser user) {
        this.loggedInUser = user;
        controller = new ControllerData(this);

        setTitle("PersonaPrediction - Menu Utama");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setMinimumSize(new Dimension(850, 650));
        setLocationRelativeTo(null);
        setResizable(true);

        // Load resources
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

        // Fonts
        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);
        customHeaderTableFont = helveticaFont.deriveFont(Font.BOLD, 16f);

        // Panel utama dengan background
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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        if (personaPredictionLogo != null) logoLabel = new JLabel(personaPredictionLogo);
        else {
            logoLabel = new JLabel("PersonaPrediction");
            logoLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 32f));
            logoLabel.setForeground(TEXT_COLOR_LIGHT);
        }
        topPanel.add(logoLabel, BorderLayout.WEST);

        String userName = (this.loggedInUser != null && this.loggedInUser.getNama() != null && !this.loggedInUser.getNama().isEmpty()) ? this.loggedInUser.getNama() : "GUEST";
        welcomeLabel = new JLabel("Welcome, " + userName);
        welcomeLabel.setFont(helveticaFont.deriveFont(Font.BOLD, 16f));
        welcomeLabel.setForeground(TEXT_COLOR_LIGHT);
        topPanel.add(welcomeLabel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel tengah (area data)
        JPanel centerContentPanel = new JPanel(new BorderLayout(0, 0));
        centerContentPanel.setOpaque(false);
        centerContentPanel.setBorder(new EmptyBorder(15, 5, 0, 5));

        // Panel dengan border rounded
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
        dataAreaWithRoundedBorder.setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding dalem border

        dataListPanel = new JPanel(new GridBagLayout());
        dataListPanel.setOpaque(false);

        gbcData = new GridBagConstraints();
        gbcData.fill = GridBagConstraints.HORIZONTAL;
        gbcData.insets = new Insets(8, 10, 8, 10); // Jarak antar sel

        JPanel wrapperForScrollPane = new JPanel(new BorderLayout());
        wrapperForScrollPane.setOpaque(false);
        wrapperForScrollPane.add(dataListPanel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(wrapperForScrollPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        dataAreaWithRoundedBorder.add(scrollPane, BorderLayout.CENTER);
        centerContentPanel.add(dataAreaWithRoundedBorder, BorderLayout.CENTER);
        mainPanel.add(centerContentPanel, BorderLayout.CENTER);

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomButtonPanel.setOpaque(false);
        bottomButtonPanel.setBorder(new EmptyBorder(25, 0, 10, 0));

        addButton = new JButton("ADD DATA");
        logoutButton = new JButton("LOGOUT");

        styleTextLikeButton(addButton);
        styleTextLikeButton(logoutButton);

        bottomButtonPanel.add(addButton);
        bottomButtonPanel.add(createSeparatorLabel());
        bottomButtonPanel.add(logoutButton);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e ->{
                new AddView(loggedInUser.getId(), this).setVisible(true);
                refreshTableData();
        });
        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Yakin mau logout, bro?", "Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        // Tampilkan semua data saat pertama kali buka
        refreshTableData();
        setVisible(true);
    }

    private void createHeaderPanel() {
        gbcData.gridy = 0;
        gbcData.weighty = 0;
        gbcData.anchor = GridBagConstraints.WEST;
        gbcData.insets = new Insets(8, 10, 8, 10);

        // Kolom Nama
        gbcData.gridx = 0;
        gbcData.weightx = 0.4;
        JLabel namaHeader = new JLabel("Nama");
        styleHeaderLabel(namaHeader);
        dataListPanel.add(namaHeader, gbcData);

        // Kolom Tanggal Lahir
        gbcData.gridx = 1;
        gbcData.weightx = 0.4;
        JLabel tanggalHeader = new JLabel("Tanggal Lahir");
        styleHeaderLabel(tanggalHeader);
        dataListPanel.add(tanggalHeader, gbcData);

        // Kolom Actions
        gbcData.gridx = 2;
        gbcData.weightx = 0.2;
        gbcData.anchor = GridBagConstraints.CENTER;
        gbcData.insets = new Insets(8, 125, 8, 10);
        JLabel actionsHeader = new JLabel("Actions");
        styleHeaderLabel(actionsHeader);
        dataListPanel.add(actionsHeader, gbcData);

        gbcData.insets = new Insets(8, 10, 8, 10);
    }

    public void addDataRow(ModelData data, int rowIndex) {
        gbcData.gridy = rowIndex;
        gbcData.weighty = 0;
        gbcData.anchor = GridBagConstraints.WEST;
        gbcData.insets = new Insets(12, 10, 12, 10);

        // Kolom Nama
        gbcData.gridx = 0;
        gbcData.weightx = 0.4;
        JLabel namaLabel = new JLabel(data.getNama());
        styleDataLabel(namaLabel);
        dataListPanel.add(namaLabel, gbcData);

        // Kolom Tanggal Lahir
        gbcData.gridx = 1;
        gbcData.weightx = 0.4;
        JLabel tanggalLabel = new JLabel(data.getTanggal());
        styleDataLabel(tanggalLabel);
        dataListPanel.add(tanggalLabel, gbcData);

        // Kolom Actions
        gbcData.gridx = 2;
        gbcData.weightx = 0.2;
        gbcData.anchor = GridBagConstraints.CENTER;
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        actionPanel.setOpaque(false);

        JButton lihatButton = new JButton("Lihat");
        styleActionButton(lihatButton);
        lihatButton.addActionListener(e -> {
            ModelData dataPrediksi = new ModelData();
            DAOData daoData = new DAOData();
            int idData = daoData.getIdData(data.getNama());
            data.setId_data(idData); // Set id_data yang didapat ke objek data

            new PrediksiView(data).setVisible(true);
        });

        JButton editButton = new JButton("Edit");
        styleActionButton(editButton);
        editButton.addActionListener(e -> {
            DAOData daoData = new DAOData();
            int idData = daoData.getIdData(data.getNama());
            data.setId_data(idData); // Pastikan id_data ter-set sebelum dikirim ke EditView
            new EditView(data, this).setVisible(true);
            refreshTableData();
        });

        JButton deleteButton = new JButton("Delete");
        styleActionButton(deleteButton);
        deleteButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Yakin mau hapus data \"" + data.getNama() + "\"?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                DAOData daoData = new DAOData();
                int idData = daoData.getIdData(data.getNama());
                controller.deleteData(idData);
                refreshTableData();
            }
        });

        actionPanel.add(lihatButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        dataListPanel.add(actionPanel, gbcData);
    }

    public void showAllData(List<ModelData> daftarData) {
        dataListPanel.removeAll();
        createHeaderPanel();
        if (daftarData != null && !daftarData.isEmpty()) {
            for (int i = 0; i < daftarData.size(); i++) {
                addDataRow(daftarData.get(i), i + 1);
            }
        }
        dataListPanel.revalidate();
        dataListPanel.repaint();
    }

    public void refreshTableData() {
        if (controller != null) {
            controller.showAllData();
        }
    }

    private void styleHeaderLabel(JLabel label) {
        label.setFont(customHeaderTableFont);
        label.setForeground(TEXT_COLOR_LIGHT);
    }

    private void styleDataLabel(JLabel label) {
        label.setFont(helveticaFont.deriveFont(15f));
        label.setForeground(new Color(210, 215, 220));
    }

    private void styleActionButton(JButton button) {
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
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
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

    public int getIdUser() { return loggedInUser.getId(); }
}