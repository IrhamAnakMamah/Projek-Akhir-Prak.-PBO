package View.Menu;

import Controller.ControllerData;
import Model.User.ModelUser; // PERLU IMPORT INI
import View.Form.LoginView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;

public class MenuView extends JFrame {

    ControllerData controller;
    private ModelUser loggedInUser; // Buat nyimpen info user yang login

    private JLabel logoLabel;
    private JLabel welcomeLabel;
    private JTable dataTable;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton logoutButton;

    private Font helveticaFont;
    private Font helveticaFontTable;
    private Font helveticaFontLogo;
    private Font helveticaFontWelcome;

    // Constructor diubah buat nerima ModelUser
    public MenuView(ModelUser user) {
        this.loggedInUser = user; // Simpen user yang login

        setTitle("Menu Aplikasi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanelGradasi = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(48, 52, 59);
                Color color2 = new Color(56, 60, 67);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanelGradasi.setLayout(new GridBagLayout());
        getContentPane().add(mainPanelGradasi);

        GridBagConstraints gbc = new GridBagConstraints();

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14);
        helveticaFontTable = new Font("Helvetica", Font.PLAIN, 12);
        helveticaFontLogo = new Font("Helvetica", Font.BOLD, 22);
        helveticaFontWelcome = new Font("Helvetica", Font.PLAIN, 13); // Font welcome disesuaikan

        // === Top Bar (Logo & Welcome Text) ===
        JPanel topBarPanel = new JPanel(new BorderLayout(10,0));
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(new EmptyBorder(15, 25, 10, 25));

        logoLabel = new JLabel("PersonaPrediction");
        logoLabel.setFont(helveticaFontLogo);
        logoLabel.setForeground(new Color(220, 220, 220));
        topBarPanel.add(logoLabel, BorderLayout.WEST);

        // Update welcome label dengan nama user
        String userName = "Guest"; // Default kalo user null
        if (this.loggedInUser != null && this.loggedInUser.getNama() != null && !this.loggedInUser.getNama().isEmpty()) {
            userName = this.loggedInUser.getNama();
        }
        welcomeLabel = new JLabel("Selamat Datang, " + userName);
        welcomeLabel.setFont(helveticaFontWelcome);
        welcomeLabel.setForeground(new Color(180, 190, 200));
        topBarPanel.add(welcomeLabel, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanelGradasi.add(topBarPanel, gbc);

        // === Button Bar (CRUD & Logout) ===
        JPanel buttonBarPanel = new JPanel(new BorderLayout(10,0));
        buttonBarPanel.setOpaque(false);
        buttonBarPanel.setBorder(new EmptyBorder(10, 20, 15, 20));

        JPanel crudButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        crudButtonsPanel.setOpaque(false);

        // Warna tombol diubah
        addButton = new JButton("Add Data");
        styleButton(addButton, new Color(0, 123, 255), new Color(0, 100, 200)); // Biru terang
        crudButtonsPanel.add(addButton);

        editButton = new JButton("Edit Data");
        styleButton(editButton, new Color(255, 193, 7), new Color(230, 170, 0)); // Kuning/Orange terang
        crudButtonsPanel.add(editButton);

        deleteButton = new JButton("Delete Data");
        styleButton(deleteButton, new Color(220, 53, 69), new Color(200, 30, 45)); // Merah terang
        crudButtonsPanel.add(deleteButton);

        buttonBarPanel.add(crudButtonsPanel, BorderLayout.WEST);

        logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(108, 117, 125), new Color(80, 90, 100)); // Abu-abu standar
        logoutButton.setPreferredSize(new Dimension(100, 32));
        buttonBarPanel.add(logoutButton, BorderLayout.EAST);

        gbc.gridy = 1;
        mainPanelGradasi.add(buttonBarPanel, gbc);

        // === Content Area dengan Rounded Corners ===
        RoundedPanel roundedContentHolder = new RoundedPanel(20, new Color(220, 225, 230));
        roundedContentHolder.setLayout(new BorderLayout());
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 20, 20);
        mainPanelGradasi.add(roundedContentHolder, gbc);

        // Tabel Data
        String[] columnNames = {"Nama", "Tanggal", "Prediksi"};
//        Object[][] dummyData = {
//                {"Budi Santoso", "2024-05-10", "Lihat"},
//                {"Siti Aminah", "2024-05-11", "Lihat"},
//                {"Charlie", "2024-05-12", "Lihat"}
//        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        dataTable = new JTable(tableModel);

        // Style Tabel Minimalis
        dataTable.setFont(helveticaFontTable);
        dataTable.setRowHeight(28);
        dataTable.setBackground(roundedContentHolder.getBackground());
        dataTable.setForeground(new Color(50, 50, 50));
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setShowGrid(false);
        dataTable.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader tableHeader = dataTable.getTableHeader();
        tableHeader.setFont(helveticaFont.deriveFont(Font.BOLD, 13));
        tableHeader.setBackground(roundedContentHolder.getBackground());
        tableHeader.setForeground(new Color(80, 80, 80));
        tableHeader.setBorder(BorderFactory.createEmptyBorder(8,5,8,5));
        tableHeader.setOpaque(false);

        controller = new ControllerData(this);
        controller.showAllData();

        // Font tombol di tabel diubah jadi BOLD
        dataTable.getColumn("Prediksi").setCellRenderer(new ButtonRenderer("Lihat"));
        dataTable.getColumn("Prediksi").setCellEditor(new ButtonEditor(new JCheckBox(), "Lihat"));

        scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(roundedContentHolder.getBackground());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        roundedContentHolder.setBorder(new EmptyBorder(5,5,5,5));
        roundedContentHolder.add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        // WARNING : ACTION LISTENERS
        addButton.addActionListener(e -> {
            new AddView(user.getId());
            dispose();
        });
        editButton.addActionListener(e -> { /* ... */ });
        deleteButton.addActionListener(e -> { /* ... */ });
        logoutButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Yakin mau logout?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(new RoundedBorder(10));
        button.setFocusPainted(false);
        button.setFont(helveticaFont.deriveFont(Font.BOLD, 11));
        button.setPreferredSize(new Dimension(110, 32));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(hoverColor); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
    }

    static class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius = 15;
        public RoundedPanel(LayoutManager layout, int radius, Color bgColor) { /*...*/ super(layout); this.cornerRadius=radius; this.backgroundColor=bgColor; setOpaque(false); }
        public RoundedPanel(int radius, Color bgColor) { /*...*/ super(new BorderLayout()); this.cornerRadius=radius; this.backgroundColor=bgColor; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) { /*...*/ super.paintComponent(g); Dimension arcs=new Dimension(cornerRadius,cornerRadius); int width=getWidth(); int height=getHeight(); Graphics2D graphics=(Graphics2D)g; graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); if(backgroundColor!=null){graphics.setColor(backgroundColor);}else{graphics.setColor(getBackground());} graphics.fillRoundRect(0,0,width-1,height-1,arcs.width,arcs.height); }
    }

    private static class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) { this.radius = radius; }
        @Override public Insets getBorderInsets(Component c) { return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius); }
        @Override public boolean isBorderOpaque() { return false; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) { /* Kosong */ }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setOpaque(true); setText(text);
            setForeground(new Color(240, 240, 240));
            setBackground(new Color(100, 110, 120)); // Warna tombol "Lihat" sedikit digelapin biar teks lebih kontras
            setBorder(new RoundedBorder(8));
            setFont(helveticaFont.deriveFont(Font.BOLD, 10)); // Font BOLD buat tombol di tabel
        }
        @Override public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean focus, int r, int c) { return this; }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button; boolean pushed; int r;
        public ButtonEditor(JCheckBox chk, String txt) {
            super(chk); button = new JButton(); button.setOpaque(true); button.setText(txt);
            button.setForeground(new Color(240, 240, 240)); button.setBackground(new Color(100, 110, 120));
            button.setBorder(new RoundedBorder(8));
            button.setFont(helveticaFont.deriveFont(Font.BOLD, 10)); // Font BOLD buat tombol di tabel
            button.addActionListener(e -> fireEditingStopped());
        }
        @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.r = row; pushed = true; return button;
        }
        @Override public Object getCellEditorValue() {
            if (pushed) {
                String namaDiRow = dataTable.getValueAt(r, 0).toString();
                JOptionPane.showMessageDialog(button, "Lihat detail untuk: " + namaDiRow);
            }
            pushed = false; return "";
        }
        @Override public boolean stopCellEditing() { pushed = false; return super.stopCellEditing(); }
        @Override protected void fireEditingStopped() { super.fireEditingStopped(); }
    }

    public int getIdUser(){
        return loggedInUser.getId();
    }

    public JTable getTableData(){
        return dataTable;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        // Contoh pass ModelUser dummy, nanti ini dari ControllerUser
        ModelUser dummyUser = new ModelUser();
        dummyUser.setNama("Budi Testing");
        SwingUtilities.invokeLater(() -> new MenuView(dummyUser).setVisible(true));
    }
}