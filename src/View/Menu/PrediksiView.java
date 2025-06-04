package View.Menu; // Sesuaikan package

// import Model.Data.ModelData; // Kalo mau nerima ModelData langsung
import Model.Data.DAOData;
import Model.Data.ModelData;
import Model.Data.ModelPrediksi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrediksiView extends JFrame {
    // ... (variabel field lain sama kayak PrediksiView sebelumnya)
    private JLabel namaValueLabel, tanggalValueLabel;
    private JTextArea prediksiTextArea;
    private JPanel zodiacImagePanel;
    private JButton backButton;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;
    private Font helveticaFont;
    private Font labelFont;
    private Font valueFont;

    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);
    private final Color ZODIAC_PANEL_BORDER_COLOR = new Color(200, 160, 80, 220);
    private final int ZODIAC_PANEL_CORNER_RADIUS = 25;
    ModelPrediksi model;
    String nama, tanggalLahir, teksPrediksiFinal;
    ImageIcon gambarZodiakFinal;

    // Constructor nerima data yang udah siap tampil
    public PrediksiView(ModelData data) {
        DAOData dao = new DAOData();
        model = dao.getData(data.getId_data());
        nama = data.getNama();
        tanggalLahir = data.getTanggal();
        teksPrediksiFinal = model.getHuruf() + model.getZodiac();
        setTitle("Hasil Prediksi Persona");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 750);
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setResizable(true);

        try {
            java.net.URL bgUrl = getClass().getResource("/resources/BackgroundMainView.png");
            if (bgUrl != null) backgroundImage = new ImageIcon(bgUrl);
            else System.err.println("BG Image GAK KETEMU di PrediksiView!");

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) personaPredictionLogo = new ImageIcon(logoUrl);
            else System.err.println("Logo GAK KETEMU di PrediksiView!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);
        labelFont = helveticaFont.deriveFont(Font.BOLD, 16f);
        valueFont = helveticaFont.deriveFont(Font.PLAIN, 16f);

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
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
        getContentPane().add(mainPanel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        if (personaPredictionLogo != null) {
            topPanel.add(new JLabel(personaPredictionLogo));
        } else {
            JLabel logoFallback = new JLabel("PERSONA PREDICTION");
            logoFallback.setFont(helveticaFont.deriveFont(Font.BOLD, 30f));
            logoFallback.setForeground(TEXT_COLOR_LIGHT);
            topPanel.add(logoFallback);
        }
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        JLabel namaLabel = new JLabel("NAMA:");
        styleInfoLabel(namaLabel);
        contentPanel.add(namaLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        namaValueLabel = new JLabel(nama != null ? nama.toUpperCase() : "N/A");
        styleValueLabel(namaValueLabel);
        contentPanel.add(namaValueLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        JLabel tanggalLabel = new JLabel("TANGGAL:");
        styleInfoLabel(tanggalLabel);
        contentPanel.add(tanggalLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        tanggalValueLabel = new JLabel(tanggalLahir != null ? tanggalLahir : "N/A");
        styleValueLabel(tanggalValueLabel);
        contentPanel.add(tanggalValueLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        zodiacImagePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (gambarZodiakFinal == null) { // Pake gambarZodiakFinal dari parameter
                    g2.setColor(new Color(0,0,0,40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), ZODIAC_PANEL_CORNER_RADIUS, ZODIAC_PANEL_CORNER_RADIUS);
                    g2.setColor(ZODIAC_PANEL_BORDER_COLOR);
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, ZODIAC_PANEL_CORNER_RADIUS, ZODIAC_PANEL_CORNER_RADIUS);
                    String placeholderText = "Area Gambar Zodiak";
                    g2.setFont(helveticaFont.deriveFont(16f));
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(placeholderText);
                    int textHeight = fm.getAscent();
                    g2.setColor(TEXT_COLOR_LIGHT);
                    g2.drawString(placeholderText, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - fm.getDescent());
                } else {
                    g2.drawImage(gambarZodiakFinal.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
                g2.dispose();
            }
        };
        zodiacImagePanel.setOpaque(false);
        zodiacImagePanel.setPreferredSize(new Dimension(200, 200));
        contentPanel.add(zodiacImagePanel, gbc);

        gbc.gridy = 3; gbc.weighty = 0.4;
        gbc.insets = new Insets(15, 0, 8, 0);

        JLabel prediksiTitleLabel = new JLabel("PREDIKSI:");
        styleInfoLabel(prediksiTitleLabel);
        JPanel prediksiPanel = new JPanel(new BorderLayout(0,5));
        prediksiPanel.setOpaque(false);
        prediksiPanel.add(prediksiTitleLabel, BorderLayout.NORTH);

        prediksiTextArea = new JTextArea(teksPrediksiFinal != null ? teksPrediksiFinal : "Prediksi tidak tersedia."); // Pake teksPrediksiFinal
        prediksiTextArea.setFont(valueFont.deriveFont(15f));
        prediksiTextArea.setForeground(TEXT_COLOR_LIGHT);
        prediksiTextArea.setOpaque(false);
        prediksiTextArea.setEditable(false);
        prediksiTextArea.setLineWrap(true);
        prediksiTextArea.setWrapStyleWord(true);
        prediksiTextArea.setBorder(new EmptyBorder(5,5,5,5));

        JScrollPane scrollPrediksi = new JScrollPane(prediksiTextArea);
        scrollPrediksi.setOpaque(false);
        scrollPrediksi.getViewport().setOpaque(false);
        scrollPrediksi.setBorder(BorderFactory.createEmptyBorder());
        prediksiPanel.add(scrollPrediksi, BorderLayout.CENTER);
        contentPanel.add(prediksiPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10,0,0,0));

        backButton = new JButton("BACK");
        styleTextLikeButton(backButton);
        backButton.addActionListener(e -> dispose());
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleInfoLabel(JLabel label) { /* ... sama kayak sebelumnya ... */
        label.setFont(labelFont);
        label.setForeground(TEXT_COLOR_LIGHT);
    }
    private void styleValueLabel(JLabel label) { /* ... sama kayak sebelumnya ... */
        label.setFont(valueFont);
        label.setForeground(new Color(230,230,240));
    }
    private void styleTextLikeButton(JButton button) { /* ... sama kayak sebelumnya ... */
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


//    ModelPrediksi model = new ModelPrediksi();
//    DAOData dao = new DAOData();
//    model = dao.getData(id_data);
//        System.out.println(model.getZodiac());
//        System.out.println(model.getHuruf());

    public static void main(String[] args) {
        // ... (main method buat testing PrediksiView sama kayak sebelumnya)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        String nama = "Udin Petot";
        String tgl = "1990-04-25";
        String prediksi = "Hari ini adalah hari yang penuh tantangan namun juga penuh peluang..."; // Teks panjang
        ImageIcon zodiakImg = null;
    }
}