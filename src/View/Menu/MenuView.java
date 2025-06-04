package View.Menu; // Pastikan package view menu lu bener

import Controller.ControllerData;
import Model.User.ModelUser; //
import View.Form.LoginView;  //
// Kalo mau pake CustomDialogView, import di sini. Contoh:
// import View.Util.CustomDialogView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; //
import javax.swing.table.TableCellRenderer; //
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuView extends JFrame {

    ControllerData controller; //
    private ModelUser loggedInUser; //

    private JLabel logoLabel;
    private JLabel welcomeLabel;
    private JTable dataTable;
    private JScrollPane scrollPane;
    private JButton addButton, editButton, deleteButton, logoutButton;

    private Font helveticaFont;
    private Font customHeaderTableFont;

    private ImageIcon backgroundImage;
    private ImageIcon personaPredictionLogo;

    private final Color DATA_AREA_BORDER_COLOR = new Color(200, 160, 80, 230);
    private final int DATA_AREA_CORNER_RADIUS = 35;
    private final Color TEXT_COLOR_LIGHT = new Color(225, 230, 235);
    private final Color TEXT_COLOR_BUTTON_HOVER = new Color(170, 200, 255);
    private final Color TABLE_CELL_FONT_COLOR = new Color(210, 215, 220);
    private final Color TABLE_SELECTION_BG_COLOR = new Color(DATA_AREA_BORDER_COLOR.getRed(), DATA_AREA_BORDER_COLOR.getGreen(), DATA_AREA_BORDER_COLOR.getBlue(), 70);


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
            else System.err.println("Background image GAK KETEMU di MenuView! Path: /resources/BackgroundMainView.png");

            java.net.URL logoUrl = getClass().getResource("/resources/logoMainView.png");
            if (logoUrl != null) personaPredictionLogo = new ImageIcon(logoUrl);
            else System.err.println("Logo image GAK KETEMU di MenuView! Path: /resources/logoMainView.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error ngeload image di MenuView: " + e.getMessage());
        }

        helveticaFont = new Font("Helvetica", Font.PLAIN, 14); //
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

        // === TOP BAR ===
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

        // === CENTER AREA: DATA DISPLAY ===
        JPanel centerContentPanel = new JPanel(new BorderLayout(0, 0));
        centerContentPanel.setOpaque(false);
        centerContentPanel.setBorder(new EmptyBorder(35, 5, 0, 5));

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

        String[] columnNames = {"Nama", "Tanggal Lahir", "Prediksi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return column == 2; }
        };
        dataTable = new JTable(tableModel);
        styleDataTable();

        scrollPane = new JScrollPane(dataTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        dataAreaWithRoundedBorder.add(dataTable.getTableHeader(), BorderLayout.NORTH); // Header di atas tabel
        dataAreaWithRoundedBorder.add(scrollPane, BorderLayout.CENTER);


        centerContentPanel.add(dataAreaWithRoundedBorder, BorderLayout.CENTER);
        mainPanel.add(centerContentPanel, BorderLayout.CENTER);

        // === BOTTOM BUTTONS ===
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

        controller = new ControllerData(this); //
        controller.showAllData(); //

        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Tambah Data Belum Tersedia", "Info", JOptionPane.INFORMATION_MESSAGE));
        editButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Edit Data Belum Tersedia", "Info", JOptionPane.INFORMATION_MESSAGE));
        deleteButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Delete Data Belum Tersedia", "Info", JOptionPane.INFORMATION_MESSAGE));
        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Yakin mau logout, bro?", "Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    private void styleDataTable() {
        dataTable.setFont(customHeaderTableFont.deriveFont(Font.PLAIN, 15f));
        dataTable.setForeground(TABLE_CELL_FONT_COLOR);
        dataTable.setOpaque(false);
        dataTable.setSelectionForeground(Color.WHITE);
        dataTable.setSelectionBackground(TABLE_SELECTION_BG_COLOR);
        dataTable.setRowHeight(35);
        dataTable.setShowGrid(false);
        dataTable.setIntercellSpacing(new Dimension(0, 0)); //

        JTableHeader tableHeader = dataTable.getTableHeader();
        tableHeader.setFont(customHeaderTableFont);
        tableHeader.setForeground(TEXT_COLOR_LIGHT);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(0,0,0,0));
        tableHeader.setDefaultRenderer(new StyledHeaderRenderer(customHeaderTableFont, TEXT_COLOR_LIGHT));
        tableHeader.setReorderingAllowed(false);

        dataTable.setDefaultRenderer(Object.class, new TransparentTableCellRenderer(customHeaderTableFont.deriveFont(Font.PLAIN, 15f), TABLE_CELL_FONT_COLOR));
        dataTable.getColumn("Prediksi").setCellRenderer(new ButtonTableRenderer("LIHAT"));
        dataTable.getColumn("Prediksi").setCellEditor(new ButtonTableEditor(new JCheckBox(), "LIHAT", this));
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

    // Inner class buat renderer header tabel yang di-style
    static class StyledHeaderRenderer extends DefaultTableCellRenderer {
        private Font headerFont;
        private Color headerColor;
        public StyledHeaderRenderer(Font font, Color color) {
            this.headerFont = font;
            this.headerColor = color;
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(BorderFactory.createEmptyBorder(12, 5, 12, 5));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setFont(headerFont);
            label.setForeground(headerColor);
            label.setOpaque(false); // KUNCI biar transparan
            label.setBackground(new Color(0,0,0,0)); // Paksa background transparan
            label.setBorder(this.getBorder()); // Re-apply border padding kita
            return label;
        }
    }

    // Inner class buat renderer sel tabel transparan
    static class TransparentTableCellRenderer extends DefaultTableCellRenderer {
        private Font cellFont;
        private Color cellColor;
        public TransparentTableCellRenderer(Font font, Color color) {
            this.cellFont = font;
            this.cellColor = color;
            // setOpaque(false); // Udah di-handle di getTableCellRendererComponent
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(cellFont);
            setForeground(cellColor);
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(new Color(0,0,0,0));
            }
            if (value != null) setText(value.toString()); else setText("");
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false); // Pastikan JLabel (this) dari DefaultTableCellRenderer jadi transparan
            return this;
        }
    }

    // Inner class buat renderer tombol "LIHAT" di tabel
    class ButtonTableRenderer extends JButton implements TableCellRenderer {
        public ButtonTableRenderer(String text) {
            super(text);
            setFont(helveticaFont.deriveFont(Font.BOLD, 12f));
            setForeground(TEXT_COLOR_LIGHT);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(4, 8, 4, 8));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        @Override public Component getTableCellRendererComponent(JTable tbl, Object val, boolean isSelected, boolean hasFocus, int r, int c) {
            if (isSelected) {
                setForeground(tbl.getSelectionForeground());
            } else {
                setForeground(TEXT_COLOR_LIGHT);
            }
            return this;
        }
    }

    // Inner class buat editor tombol "LIHAT" di tabel
    class ButtonTableEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean pushed;
        private int editingRow;
        private JFrame parentFrame;
        private String labelButton; // Buat nyimpen teks tombol

        public ButtonTableEditor(JCheckBox checkBox, String text, JFrame parent) {
            super(checkBox);
            this.parentFrame = parent;
            this.labelButton = text;
            button = new JButton(this.labelButton);

            button.setFont(helveticaFont.deriveFont(Font.BOLD, 12f));
            button.setForeground(TEXT_COLOR_LIGHT);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(new EmptyBorder(4, 8, 4, 8));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setBackground(new Color(0, 0, 0, 0));

            Color originalBtnColor = button.getForeground();
            button.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { button.setForeground(TEXT_COLOR_BUTTON_HOVER); }
                @Override public void mouseExited(MouseEvent e) { button.setForeground(originalBtnColor); }
            });
            button.addActionListener(e -> fireEditingStopped());
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.editingRow = row;
            pushed = true;
            return button;
        }
        @Override
        public Object getCellEditorValue() {
            if (pushed) {
                String namaDiRow = "";
                if (dataTable.getRowCount() > editingRow && dataTable.getColumnCount() > 0) {
                    Object namaValue = dataTable.getValueAt(editingRow, 0);
                    if (namaValue != null) namaDiRow = namaValue.toString();
                }
                System.out.println("Tombol 'Lihat' diklik untuk: " + namaDiRow);
                JOptionPane.showMessageDialog(button, "Menampilkan detail prediksi untuk: " + namaDiRow, "Info Prediksi", JOptionPane.INFORMATION_MESSAGE);
            }
            pushed = false;
            return this.labelButton;
        }
        @Override public boolean stopCellEditing() { pushed = false; return super.stopCellEditing(); }
        @Override protected void fireEditingStopped() { super.fireEditingStopped(); }
    }

    public int getIdUser(){ return loggedInUser.getId(); } //
    public JTable getTableData(){ return dataTable; } //

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        ModelUser dummyUser = new ModelUser(); //
        dummyUser.setId(1);
        dummyUser.setNama("IRHAM UHUY");
        SwingUtilities.invokeLater(() -> new MenuView(dummyUser).setVisible(true));
    }
}