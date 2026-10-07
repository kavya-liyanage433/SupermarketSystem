package supermarket.view;

import javax.swing.*;
import java.awt.*;
import supermarket.util.DBConnection;
import java.sql.*;

public class LoginForm extends javax.swing.JFrame {

    private final Color BG_DARK    = new Color(0x3B, 0x55, 0x70); // Navy
    private final Color BG_PANEL   = new Color(0x49, 0x67, 0x6C); // Teal
    private final Color BG_HEADER  = new Color(0x7A, 0x3A, 0x65); // Plum
    private final Color CLR_GREEN  = new Color(0x88, 0xA4, 0xA7); // Sage
    private final Color CLR_BLUE   = new Color(0x8F, 0x84, 0xAE); // Lavender
    private final Color CLR_RED    = new Color(0xE7, 0x9D, 0xB0); // Pink
    private final Color TEXT_WHITE = new Color(255, 255, 255);
    private final Color TEXT_LIGHT = new Color(0xE7, 0x9D, 0xB0); // Pink
    private final Color INPUT_BG   = new Color(0x3B, 0x55, 0x70); // Navy

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear;
    private JLabel lblStatus;

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        // ── Header ──
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel iconLabel = new JLabel("🛒", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        headerPanel.add(iconLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("SUPERMARKET SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel subLabel = new JLabel("Please login to continue", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT);
        headerPanel.add(subLabel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ── Login Form Panel ──
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_PANEL);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BLUE, 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(TEXT_LIGHT);
        formPanel.add(lblUser, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        txtUsername = new JTextField();
        styleField(txtUsername);
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(TEXT_LIGHT);
        formPanel.add(lblPass, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        txtPassword = new JPasswordField();
        styleField(txtPassword);
        formPanel.add(txtPassword, gbc);

        // Status Label
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        lblStatus = new JLabel(" ", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(CLR_RED);
        formPanel.add(lblStatus, gbc);

        // Buttons
        gbc.gridy = 3;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(BG_PANEL);

        btnLogin = makeButton("Login", CLR_GREEN);
        btnClear = makeButton("Clear", new Color(0xE7, 0x9D, 0xB0)); // Pink

        btnLogin.addActionListener(e -> doLogin());
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            lblStatus.setText(" ");
        });

        // Enter key triggers login
        txtPassword.addActionListener(e -> doLogin());

        btnPanel.add(btnLogin);
        btnPanel.add(btnClear);
        formPanel.add(btnPanel, gbc);

        // Center the form panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(BG_DARK);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        centerWrapper.add(formPanel);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BG_HEADER);
        JLabel footerLabel = new JLabel("© 2026 Supermarket Management System");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(TEXT_LIGHT);
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Login - Supermarket System");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("⚠️ Please enter username and password!");
            lblStatus.setForeground(new Color(0x8F, 0x84, 0xAE)); // Lavender
            return;
        }

        // Check credentials from database
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblStatus.setText("✅ Login successful!");
                lblStatus.setForeground(new Color(0x88, 0xA4, 0xA7)); // Sage
                Timer timer = new Timer(800, e -> {
                    new DashboardForm().setVisible(true);
                    dispose();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                lblStatus.setText("❌ Invalid username or password!");
                lblStatus.setForeground(CLR_RED);
                txtPassword.setText("");
            }
        } catch (Exception e) {
            lblStatus.setText("❌ Error: " + e.getMessage());
            lblStatus.setForeground(CLR_RED);
        }
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(TEXT_WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x8F, 0x84, 0xAE), 1), // Lavender
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(220, 36));
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
        java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }
}