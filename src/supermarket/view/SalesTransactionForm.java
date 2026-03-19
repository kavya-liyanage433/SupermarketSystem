package supermarket.view;

import supermarket.dao.ProductDAO;
import supermarket.dao.SaleDAO;
import supermarket.model.Product;
import supermarket.model.Sale;
import supermarket.model.SaleItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SalesTransactionForm extends javax.swing.JFrame {

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

    private JTextField txtCashierName, txtCustomerId, txtProductId, txtQty, txtTotal;
    private JTable tblCart;
    private JButton btnAddToCart, btnCompleteSale, btnClearCart;
    private JScrollPane jScrollPane1;
    private List<SaleItem> cartItems = new ArrayList<>();

    public SalesTransactionForm() {
        initComponents();
        setupCart();
    }

    private void setupCart() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Product ID", "Product Name", "Qty", "Unit Price", "Subtotal"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCart.setModel(model);
        tblCart.setRowHeight(28);
        tblCart.setBackground(BG_PANEL);
        tblCart.setForeground(TEXT_WHITE);
        tblCart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblCart.setSelectionBackground(CLR_BLUE);
        tblCart.setSelectionForeground(Color.WHITE);
        tblCart.setGridColor(new Color(50, 65, 100));
        tblCart.getTableHeader().setBackground(BG_HEADER);
        tblCart.getTableHeader().setForeground(TEXT_LIGHT);
        tblCart.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblCart.getTableHeader().setPreferredSize(new Dimension(0, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++)
            tblCart.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    private void refreshTotal() {
        double total = 0;
        for (SaleItem item : cartItems) total += item.getSubtotal();
        txtTotal.setText(String.format("%.2f", total));
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel titleLabel = new JLabel("🛒  Sales Transaction");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subLabel = new JLabel("Process Customer Sales");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BG_DARK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        // Top form - two columns
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        topPanel.setBackground(BG_DARK);

        // Left - Cashier & Customer
        JPanel leftForm = new JPanel(new GridBagLayout());
        leftForm.setBackground(BG_PANEL);
        leftForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BLUE, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        leftForm.add(makeLabel("Cashier Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        txtCashierName = makeField();
        leftForm.add(txtCashierName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        leftForm.add(makeLabel("Customer ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        txtCustomerId = makeField();
        leftForm.add(txtCustomerId, gbc);

        // Right - Product & Qty
        JPanel rightForm = new JPanel(new GridBagLayout());
        rightForm.setBackground(BG_PANEL);
        rightForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_GREEN, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(6, 8, 6, 8);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        gbc2.gridx = 0; gbc2.gridy = 0; gbc2.weightx = 0.3;
        rightForm.add(makeLabel("Product ID:"), gbc2);
        gbc2.gridx = 1; gbc2.weightx = 0.7;
        txtProductId = makeField();
        rightForm.add(txtProductId, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 1; gbc2.weightx = 0.3;
        rightForm.add(makeLabel("Quantity:"), gbc2);
        gbc2.gridx = 1; gbc2.weightx = 0.7;
        txtQty = makeField();
        rightForm.add(txtQty, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 2; gbc2.gridwidth = 2;
        btnAddToCart = makeButton("➕  Add to Cart", CLR_GREEN);
        btnAddToCart.addActionListener(e -> addToCart());
        rightForm.add(btnAddToCart, gbc2);

        topPanel.add(leftForm);
        topPanel.add(rightForm);
        centerPanel.add(topPanel, BorderLayout.NORTH);

        // Cart Table
        tblCart = new JTable();
        jScrollPane1 = new JScrollPane(tblCart);
        jScrollPane1.getViewport().setBackground(BG_PANEL);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(CLR_BLUE, 1));
        centerPanel.add(jScrollPane1, BorderLayout.CENTER);

        // Total Panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        totalPanel.setBackground(BG_DARK);
        JLabel totalLabel = new JLabel("Total (Rs.):");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setForeground(CLR_GREEN);
        txtTotal = makeField();
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTotal.setForeground(CLR_GREEN);
        txtTotal.setPreferredSize(new Dimension(150, 35));
        totalPanel.add(totalLabel);
        totalPanel.add(txtTotal);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonsPanel.setBackground(BG_DARK);

        btnCompleteSale = makeButton("✅  Complete Sale", CLR_GREEN);
        btnClearCart    = makeButton("🔄  Clear Cart",   CLR_GRAY);

        btnCompleteSale.setPreferredSize(new Dimension(180, 40));
        btnClearCart.setPreferredSize(new Dimension(150, 40));

        btnCompleteSale.addActionListener(e -> completeSale());
        btnClearCart.addActionListener(e -> clearCart());

        buttonsPanel.add(btnCompleteSale);
        buttonsPanel.add(btnClearCart);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Sales Transaction");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(750, 580);
        setLocationRelativeTo(null);
    }

    private void addToCart() {
    if (!supermarket.util.Validator.validateSale(
            txtCashierName, txtProductId, txtQty, INPUT_BG, TEXT_WHITE)) return;
    try {
        int productId = Integer.parseInt(txtProductId.getText().trim());
        int qty = Integer.parseInt(txtQty.getText().trim());
        ProductDAO productDAO = new ProductDAO();
        Product p = productDAO.getProductById(productId);
        if (p == null) {
            supermarket.util.Validator.showError(this, "Product ID not found!");
            return;
        }
        if (qty > p.getStockQty()) {
            supermarket.util.Validator.showError(this,
                "Not enough stock! Available: " + p.getStockQty());
            return;
        }
        SaleItem item = new SaleItem(p.getProductId(), p.getName(), qty, p.getPrice());
        cartItems.add(item);
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.addRow(new Object[]{
            item.getProductId(), item.getProductName(),
            item.getQty(), item.getUnitPrice(), item.getSubtotal()
        });
        refreshTotal();
        txtProductId.setText("");
        txtQty.setText("");
    } catch (Exception e) {
        supermarket.util.Validator.showError(this, "Error: " + e.getMessage());
    }
}

    private void completeSale() {
        try {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!"); return;
            }
            if (txtCashierName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Cashier Name!"); return;
            }
            int customerId = 0;
            if (!txtCustomerId.getText().trim().isEmpty())
                customerId = Integer.parseInt(txtCustomerId.getText().trim());

            double total = 0;
            for (SaleItem item : cartItems) total += item.getSubtotal();

            Sale sale = new Sale(customerId, total, txtCashierName.getText().trim());
            sale.setItems(cartItems);

            SaleDAO saleDAO = new SaleDAO();
            if (saleDAO.saveSale(sale)) {
                JOptionPane.showMessageDialog(this,
                    "✅ Sale completed!\nTotal: Rs. " + String.format("%.2f", total));
                clearCart();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearCart() {
        cartItems.clear();
        ((DefaultTableModel) tblCart.getModel()).setRowCount(0);
        txtTotal.setText("");
        txtProductId.setText("");
        txtQty.setText("");
        txtCashierName.setText("");
        txtCustomerId.setText("");
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_LIGHT);
        return lbl;
    }

    private JTextField makeField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(TEXT_WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 80, 120), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        field.setPreferredSize(new Dimension(200, 32));
        return field;
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
        return btn;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SalesTransactionForm().setVisible(true));
    }
}