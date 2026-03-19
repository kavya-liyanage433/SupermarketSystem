package supermarket.view;

import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import supermarket.util.DBConnection;
import javax.swing.*;
import java.awt.*;

public class ReportGenerator extends javax.swing.JFrame {

    private final Color BG_DARK    = new Color(23, 32, 56);
    private final Color BG_PANEL   = new Color(30, 42, 74);
    private final Color BG_HEADER  = new Color(15, 20, 40);
    private final Color CLR_GREEN  = new Color(46, 204, 113);
    private final Color CLR_BLUE   = new Color(52, 152, 219);
    private final Color CLR_RED    = new Color(231, 76, 60);
    private final Color CLR_ORANGE = new Color(230, 126, 34);
    private final Color CLR_PURPLE = new Color(155, 89, 182);
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(180, 200, 230);

    private JLabel lblStatus;
    private final String BASE_PATH = "C:\\NetBeansProjects\\SupermarketSystem\\src\\supermarket\\view\\";

    public ReportGenerator() {
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel titleLabel = new JLabel("📄  Report Generator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_WHITE);
        JLabel subLabel = new JLabel("Management Reports");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Report Cards Grid
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        cardsPanel.setBackground(BG_DARK);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        cardsPanel.add(createReportCard("📊", "SalesReports_1",
            "View all sales transactions\nwith product, cashier & date details.",
            CLR_BLUE, "SalesReports_1.jrxml"));

        cardsPanel.add(createReportCard("📦", "StockReport",
            "View current inventory levels\nwith category & supplier info.",
            CLR_GREEN, "StockReport.jrxml"));

        cardsPanel.add(createReportCard("💰", "DailyRevenueReport",
            "View daily revenue summary\nper cashier and transaction count.",
            CLR_ORANGE, "DailyRevenueReport.jrxml"));

        cardsPanel.add(createReportCard("🏆", "TopProductsReport",
            "View best selling products\nwith total sold & revenue.",
            CLR_PURPLE, "TopProductsReport.jrxml"));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        // Status + Close
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_DARK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 15, 25));

        lblStatus = new JLabel("Select a report to generate", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(TEXT_LIGHT);

        JButton btnClose = makeButton("✖  Close", CLR_RED);
        btnClose.addActionListener(e -> dispose());

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        closePanel.setBackground(BG_DARK);
        closePanel.add(btnClose);

        bottomPanel.add(lblStatus, BorderLayout.NORTH);
        bottomPanel.add(closePanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Report Generator");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(700, 520);
        setLocationRelativeTo(null);
    }

    private JPanel createReportCard(String icon, String title, String desc, Color color, String fileName) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(icon + "  " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);

        JLabel descLabel = new JLabel("<html><p style='color:#b4c8e6;'>" +
            desc.replace("\n", "<br>") + "</p></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JButton btnGenerate = makeButton("Generate", color);
        btnGenerate.setPreferredSize(new Dimension(120, 35));
        btnGenerate.addActionListener(e -> generateReport(fileName, title));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        card.add(btnGenerate, BorderLayout.SOUTH);
        return card;
    }

    private void generateReport(String fileName, String reportName) {
        Connection conn = null;
        try {
            lblStatus.setText("⏳ Generating " + reportName + "...");
            lblStatus.setForeground(new Color(241, 196, 15));

            conn = DBConnection.getConnection();
            String reportPath = BASE_PATH + fileName;

            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperViewer.viewReport(jasperPrint, false);

            lblStatus.setText("✅ " + reportName + " generated successfully!");
            lblStatus.setForeground(new Color(46, 204, 113));
        } catch (Exception e) {
            lblStatus.setText("❌ Error: " + e.getMessage());
            lblStatus.setForeground(CLR_RED);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ex) {}
        }
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

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ReportGenerator().setVisible(true));
    }
}