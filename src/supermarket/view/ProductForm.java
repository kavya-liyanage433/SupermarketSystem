package supermarket.view;

import supermarket.dao.ProductDAO;
import supermarket.model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ProductForm extends javax.swing.JFrame {

    private ProductDAO productDAO = new ProductDAO();
    private final Color BG_DARK   = new Color(0x3B, 0x55, 0x70); // Navy
    private final Color BG_PANEL  = new Color(0x49, 0x67, 0x6C); // Teal
    private final Color BG_HEADER = new Color(0x7A, 0x3A, 0x65); // Plum
    private final Color CLR_GREEN = new Color(0x88, 0xA4, 0xA7); // Sage
    private final Color CLR_BLUE  = new Color(0x8F, 0x84, 0xAE); // Lavender
    private final Color CLR_RED   = new Color(0x7A, 0x3A, 0x65); // Plum
    private final Color CLR_GRAY  = new Color(0xE7, 0x9D, 0xB0); // Pink
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(0xE7, 0x9D, 0xB0); // Pink
    private final Color INPUT_BG  = new Color(0x3B, 0x55, 0x70); // Navy

    private JTextField txtProductId, txtName, txtPrice, txtStock;
    private JTable tblProducts;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JScrollPane jScrollPane1;

    public ProductForm() {
        initComponents();
        setupTable();
        loadProducts();
    }

    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Name", "Price (Rs.)", "Stock"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblProducts.setModel(model);
        tblProducts.setRowHeight(30);
        tblProducts.setBackground(BG_PANEL);
        tblProducts.setForeground(TEXT_WHITE);
        tblProducts.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblProducts.setSelectionBackground(CLR_BLUE);
        tblProducts.setSelectionForeground(Color.WHITE);
        tblProducts.setGridColor(new Color(0x3B, 0x55, 0x70)); // Navy
        tblProducts.getTableHeader().setBackground(BG_HEADER);
        tblProducts.getTableHeader().setForeground(TEXT_LIGHT);
        tblProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblProducts.getTableHeader().setPreferredSize(new Dimension(0, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 4; i++)
            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblProducts.getSelectedRow();
                txtProductId.setText(tblProducts.getValueAt(row, 0).toString());
                txtName.setText(tblProducts.getValueAt(row, 1).toString());
                txtPrice.setText(tblProducts.getValueAt(row, 2).toString());
                txtStock.setText(tblProducts.getValueAt(row, 3).toString());
            }
        });
    }

    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
        model.setRowCount(0);
        try {
            for (Product p : productDAO.getAllProducts()) {
                model.addRow(new Object[]{
                    p.getProductId(), p.getName(),
                    String.format("%.2f", p.getPrice()), p.getStockQty()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtProductId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel titleLabel = new JLabel("📦  Product Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subLabel = new JLabel("Add / Edit / Delete Products");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_PANEL);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BLUE, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Product ID:", "Name:", "Price (Rs.):", "Stock Qty:"};
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
        txtProductId = fields[0];
        txtProductId.setEditable(false);
        txtProductId.setBackground(new Color(0x7A, 0x3A, 0x65)); // Plum
        txtName  = fields[1];
        txtPrice = fields[2];
        txtStock = fields[3];

        centerPanel.add(formPanel, BorderLayout.NORTH);

        tblProducts = new JTable();
        jScrollPane1 = new JScrollPane(tblProducts);
        jScrollPane1.getViewport().setBackground(BG_PANEL);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(CLR_BLUE, 1));
        centerPanel.add(jScrollPane1, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonsPanel.setBackground(BG_DARK);

        btnAdd    = makeButton("Add",    CLR_GREEN);
        btnUpdate = makeButton("Update", CLR_BLUE);
        btnDelete = makeButton("🗑️  Delete", CLR_RED);
        btnClear  = makeButton("Clear",  CLR_GRAY);

        btnAdd.addActionListener(e -> {
    if (!supermarket.util.Validator.validateProduct(
            txtName, txtPrice, txtStock, INPUT_BG, TEXT_WHITE)) return;
    try {
        productDAO.addProduct(new Product(0, txtName.getText().trim(),
            Double.parseDouble(txtPrice.getText()),
            Integer.parseInt(txtStock.getText()), 1, 1));
        supermarket.util.Validator.showSuccess(this, "✅ Product added successfully!");
        clearForm(); loadProducts();
    } catch (Exception ex) {
        supermarket.util.Validator.showError(this, "Database Error: " + ex.getMessage());
    }
});

        btnUpdate.addActionListener(e -> {
            try {
                if (txtProductId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Select a product first!"); return;
                }
                productDAO.updateProduct(new Product(
                    Integer.parseInt(txtProductId.getText()),
                    txtName.getText().trim(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText()), 1, 1));
                JOptionPane.showMessageDialog(this, "✅ Product updated!");
                clearForm(); loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                if (txtProductId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Select a product first!"); return;
                }
                if (JOptionPane.showConfirmDialog(this, "Delete this product?",
                    "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    productDAO.deleteProduct(Integer.parseInt(txtProductId.getText()));
                    JOptionPane.showMessageDialog(this, "🗑️ Product deleted!");
                    clearForm(); loadProducts();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> clearForm());

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnClear);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Product Management");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(700, 560);
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

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ProductForm().setVisible(true));
    }
}