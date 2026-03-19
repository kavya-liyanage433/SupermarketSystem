package supermarket.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import supermarket.util.DBConnection;

public class SupplierForm extends javax.swing.JFrame {

    private final Color BG_DARK   = new Color(23, 32, 56);
    private final Color BG_PANEL  = new Color(30, 42, 74);
    private final Color BG_HEADER = new Color(15, 20, 40);
    private final Color CLR_GREEN = new Color(46, 204, 113);
    private final Color CLR_BLUE  = new Color(52, 152, 219);
    private final Color CLR_RED   = new Color(231, 76, 60);
    private final Color CLR_GRAY  = new Color(100, 110, 130);
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(180, 200, 230);
    private final Color INPUT_BG  = new Color(40, 55, 90);

    private JTextField txtId, txtName, txtPhone, txtEmail, txtAddress;
    private JTable tblSuppliers;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JScrollPane scrollPane;

    public SupplierForm() {
        initComponents();
        loadSuppliers();
    }

    private void loadSuppliers() {
        DefaultTableModel model = (DefaultTableModel) tblSuppliers.getModel();
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM supplier")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("supplier_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtId.setText(""); txtName.setText("");
        txtPhone.setText(""); txtEmail.setText("");
        txtAddress.setText("");
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel titleLabel = new JLabel("🏭  Supplier Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subLabel = new JLabel("Add / Edit / Delete Suppliers");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_PANEL);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BLUE, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Supplier ID:", "Name:", "Phone:", "Email:", "Address:"};
        JTextField[] fields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.2;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lbl.setForeground(TEXT_LIGHT);
            formPanel.add(lbl, gbc);

            gbc.gridx = 1; gbc.weightx = 0.8;
            fields[i] = new JTextField();
            fields[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            fields[i].setBackground(INPUT_BG);
            fields[i].setForeground(TEXT_WHITE);
            fields[i].setCaretColor(TEXT_WHITE);
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 120), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            fields[i].setPreferredSize(new Dimension(250, 32));
            formPanel.add(fields[i], gbc);
        }
        txtId = fields[0]; txtId.setEditable(false);
        txtId.setBackground(new Color(50, 65, 100));
        txtName = fields[1]; txtPhone = fields[2];
        txtEmail = fields[3]; txtAddress = fields[4];

        centerPanel.add(formPanel, BorderLayout.NORTH);

        // Table
        tblSuppliers = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Name", "Phone", "Email", "Address"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } });
        tblSuppliers.setRowHeight(28);
        tblSuppliers.setBackground(BG_PANEL);
        tblSuppliers.setForeground(TEXT_WHITE);
        tblSuppliers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSuppliers.setSelectionBackground(CLR_BLUE);
        tblSuppliers.setSelectionForeground(Color.WHITE);
        tblSuppliers.setGridColor(new Color(50, 65, 100));
        tblSuppliers.getTableHeader().setBackground(BG_HEADER);
        tblSuppliers.getTableHeader().setForeground(TEXT_LIGHT);
        tblSuppliers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblSuppliers.getSelectedRow();
                txtId.setText(tblSuppliers.getValueAt(row, 0).toString());
                txtName.setText(tblSuppliers.getValueAt(row, 1).toString());
                txtPhone.setText(tblSuppliers.getValueAt(row, 2) != null ? tblSuppliers.getValueAt(row, 2).toString() : "");
                txtEmail.setText(tblSuppliers.getValueAt(row, 3) != null ? tblSuppliers.getValueAt(row, 3).toString() : "");
                txtAddress.setText(tblSuppliers.getValueAt(row, 4) != null ? tblSuppliers.getValueAt(row, 4).toString() : "");
            }
        });

        scrollPane = new JScrollPane(tblSuppliers);
        scrollPane.getViewport().setBackground(BG_PANEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(CLR_BLUE, 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonsPanel.setBackground(BG_DARK);

        btnAdd    = makeButton("➕  Add",    CLR_GREEN);
        btnUpdate = makeButton("✏️  Update", CLR_BLUE);
        btnDelete = makeButton("🗑️  Delete", CLR_RED);
        btnClear  = makeButton("🔄  Clear",  CLR_GRAY);

        btnAdd.addActionListener(e -> {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required!"); return;
            }
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO supplier (name, phone, email, address) VALUES (?,?,?,?)")) {
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtPhone.getText().trim());
                ps.setString(3, txtEmail.getText().trim());
                ps.setString(4, txtAddress.getText().trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Supplier added!");
                clearForm(); loadSuppliers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a supplier first!"); return;
            }
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "UPDATE supplier SET name=?, phone=?, email=?, address=? WHERE supplier_id=?")) {
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtPhone.getText().trim());
                ps.setString(3, txtEmail.getText().trim());
                ps.setString(4, txtAddress.getText().trim());
                ps.setInt(5, Integer.parseInt(txtId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Supplier updated!");
                clearForm(); loadSuppliers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a supplier first!"); return;
            }
            if (JOptionPane.showConfirmDialog(this, "Delete this supplier?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM supplier WHERE supplier_id=?")) {
                    ps.setInt(1, Integer.parseInt(txtId.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "🗑️ Supplier deleted!");
                    clearForm(); loadSuppliers();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        btnClear.addActionListener(e -> clearForm());

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnClear);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Supplier Management");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(750, 560);
        setLocationRelativeTo(null);
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new SupplierForm().setVisible(true));
    }
}