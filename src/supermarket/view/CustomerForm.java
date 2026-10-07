package supermarket.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import supermarket.util.DBConnection;

public class CustomerForm extends javax.swing.JFrame {

    private final Color BG_DARK   = new Color(0x3B, 0x55, 0x70); 
    private final Color BG_PANEL  = new Color(0x49, 0x67, 0x6C); 
    private final Color BG_HEADER = new Color(0x7A, 0x3A, 0x65); 
    private final Color CLR_GREEN = new Color(0x88, 0xA4, 0xA7); 
    private final Color CLR_BLUE  = new Color(0x8F, 0x84, 0xAE); 
    private final Color CLR_RED   = new Color(0x7A, 0x3A, 0x65); 
    private final Color CLR_GRAY  = new Color(0xE7, 0x9D, 0xB0); 
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(0xE7, 0x9D, 0xB0); 
    private final Color INPUT_BG  = new Color(0x3B, 0x55, 0x70); 

    private JTextField txtId, txtName, txtPhone, txtEmail;
    private JTable tblCustomers;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public CustomerForm() {
        initComponents();
        loadCustomers();
    }

    private void loadCustomers() {
        DefaultTableModel model = (DefaultTableModel) tblCustomers.getModel();
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customer")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtId.setText(""); txtName.setText("");
        txtPhone.setText(""); txtEmail.setText("");
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subLabel = new JLabel("Add / Edit / Delete Customers");
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
            BorderFactory.createLineBorder(new Color(0xE7, 0x9D, 0xB0), 1), // Pink
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Customer ID:", "Name:", "Phone:", "Email:"};
        JTextField[] fields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
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
                BorderFactory.createLineBorder(new Color(0x8F, 0x84, 0xAE), 1), // Lavender
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            fields[i].setPreferredSize(new Dimension(250, 32));
            formPanel.add(fields[i], gbc);
        }
        txtId = fields[0]; txtId.setEditable(false);
        txtId.setBackground(new Color(0x7A, 0x3A, 0x65)); // Plum
        txtName = fields[1]; txtPhone = fields[2]; txtEmail = fields[3];

        centerPanel.add(formPanel, BorderLayout.NORTH);

        // Table
        tblCustomers = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Name", "Phone", "Email"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } });
        tblCustomers.setRowHeight(28);
        tblCustomers.setBackground(BG_PANEL);
        tblCustomers.setForeground(TEXT_WHITE);
        tblCustomers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblCustomers.setSelectionBackground(new Color(0xE7, 0x9D, 0xB0)); // Pink
        tblCustomers.setSelectionForeground(Color.WHITE);
        tblCustomers.setGridColor(new Color(0x3B, 0x55, 0x70)); // Navy
        tblCustomers.getTableHeader().setBackground(BG_HEADER);
        tblCustomers.getTableHeader().setForeground(TEXT_LIGHT);
        tblCustomers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblCustomers.getSelectedRow();
                txtId.setText(tblCustomers.getValueAt(row, 0).toString());
                txtName.setText(tblCustomers.getValueAt(row, 1).toString());
                txtPhone.setText(tblCustomers.getValueAt(row, 2) != null ? tblCustomers.getValueAt(row, 2).toString() : "");
                txtEmail.setText(tblCustomers.getValueAt(row, 3) != null ? tblCustomers.getValueAt(row, 3).toString() : "");
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblCustomers);
        scrollPane.getViewport().setBackground(BG_PANEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xE7, 0x9D, 0xB0), 1)); // Pink
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
                     "INSERT INTO customer (name, phone, email) VALUES (?,?,?)")) {
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtPhone.getText().trim());
                ps.setString(3, txtEmail.getText().trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Customer added!");
                clearForm(); loadCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a customer first!"); return;
            }
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "UPDATE customer SET name=?, phone=?, email=? WHERE customer_id=?")) {
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtPhone.getText().trim());
                ps.setString(3, txtEmail.getText().trim());
                ps.setInt(4, Integer.parseInt(txtId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Customer updated!");
                clearForm(); loadCustomers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a customer first!"); return;
            }
            if (JOptionPane.showConfirmDialog(this, "Delete this customer?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM customer WHERE customer_id=?")) {
                    ps.setInt(1, Integer.parseInt(txtId.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "🗑️ Customer deleted!");
                    clearForm(); loadCustomers();
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
        setTitle("Customer Management");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(700, 530);
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
        java.awt.EventQueue.invokeLater(() -> new CustomerForm().setVisible(true));
    }
}