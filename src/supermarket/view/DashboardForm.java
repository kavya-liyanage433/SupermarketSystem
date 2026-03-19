package supermarket.view;

import supermarket.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.*;

public class DashboardForm extends javax.swing.JFrame {

    private final Color BG_DARK   = new Color(23, 32, 56);
    private final Color BG_PANEL  = new Color(30, 42, 74);
    private final Color BG_HEADER = new Color(15, 20, 40);
    private final Color CLR_GREEN = new Color(46, 204, 113);
    private final Color CLR_BLUE  = new Color(52, 152, 219);
    private final Color CLR_RED   = new Color(231, 76, 60);
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(180, 200, 230);

    private JLabel lblTotalSales, lblTotalProducts, lblTotalCustomers, lblRevenue;

    public DashboardForm() {
        initComponents();
        loadDashboard();
    }

    private void loadDashboard() {
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM sale");
            if (rs.next()) lblTotalSales.setText(String.valueOf(rs.getInt(1)));

            rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM product");
            if (rs.next()) lblTotalProducts.setText(String.valueOf(rs.getInt(1)));

            rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM customer");
            if (rs.next()) lblTotalCustomers.setText(String.valueOf(rs.getInt(1)));

            rs = conn.createStatement().executeQuery(
                "SELECT COALESCE(SUM(total_amount),0) FROM sale WHERE DATE(sale_date)=CURDATE()");
            if (rs.next()) lblRevenue.setText("Rs. " + String.format("%.2f", rs.getDouble(1)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JLabel titleLabel = new JLabel("🛒  SUPERMARKET MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subTitle = new JLabel("Dashboard Overview");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subTitle.setForeground(TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subTitle, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Stats Cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsPanel.setBackground(BG_DARK);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));

        JPanel card1 = createCard("Total Sales", "0", new Color(52, 152, 219), "📊");
        lblTotalSales = findValueLabel(card1);
        cardsPanel.add(card1);

        JPanel card2 = createCard("Total Products", "0", new Color(46, 204, 113), "📦");
        lblTotalProducts = findValueLabel(card2);
        cardsPanel.add(card2);

        JPanel card3 = createCard("Total Customers", "0", new Color(155, 89, 182), "👥");
        lblTotalCustomers = findValueLabel(card3);
        cardsPanel.add(card3);

        JPanel card4 = createCard("Today's Revenue", "Rs. 0.00", new Color(231, 76, 60), "💰");
        lblRevenue = findValueLabel(card4);
        cardsPanel.add(card4);

        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        // Buttons Panel - 2 rows
        JPanel allButtonsPanel = new JPanel(new GridLayout(2, 3, 15, 10));
        allButtonsPanel.setBackground(BG_DARK);
        allButtonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));

        JButton btnProducts  = makeButton("📦  Manage Products",  CLR_BLUE);
        JButton btnSales     = makeButton("🛒  New Sale",         CLR_GREEN);
        JButton btnReport    = makeButton("📄  Reports",          CLR_RED);
        JButton btnSuppliers = makeButton("🏭  Suppliers",        new Color(52, 73, 94));
        JButton btnCustomers = makeButton("👥  Customers",        new Color(155, 89, 182));
        JButton btnLogout    = makeButton("🚪  Logout",           new Color(100, 110, 130));

        btnProducts.addActionListener(e -> new ProductForm().setVisible(true));
        btnSales.addActionListener(e -> new SalesTransactionForm().setVisible(true));
        btnReport.addActionListener(e -> new ReportGenerator().setVisible(true));
        btnSuppliers.addActionListener(e -> new SupplierForm().setVisible(true));
        btnCustomers.addActionListener(e -> new CustomerForm().setVisible(true));
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        allButtonsPanel.add(btnProducts);
        allButtonsPanel.add(btnSales);
        allButtonsPanel.add(btnReport);
        allButtonsPanel.add(btnSuppliers);
        allButtonsPanel.add(btnCustomers);
        allButtonsPanel.add(btnLogout);

        mainPanel.add(allButtonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Supermarket Management System - Dashboard");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 480);
        setLocationRelativeTo(null);
    }

    private JPanel createCard(String title, String value, Color color, String icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(30, 42, 74));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel iconLabel = new JLabel(icon + "  " + title);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        iconLabel.setForeground(TEXT_LIGHT);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setName("valueLabel");
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JLabel findValueLabel(JPanel card) {
        for (Component c : card.getComponents()) {
            if (c instanceof JLabel && "valueLabel".equals(c.getName()))
                return (JLabel) c;
        }
        return new JLabel();
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 45));
        return btn;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DashboardForm().setVisible(true));
    }
}