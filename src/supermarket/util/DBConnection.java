package supermarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL for database
    private static final String URL = 
        "jdbc:mysql://localhost:3306/supermarket_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    
    // XAMPP default user
    private static final String USER = "root";
    
    // XAMPP default password (usually empty)
    private static final String PASSWORD = "";

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Return connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found: " + e.getMessage());
        }
    }

    // Test the connection
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Database Connected Successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}