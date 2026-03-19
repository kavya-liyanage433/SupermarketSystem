import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {

    // Database URL, user, and password
    private static final String URL =
        "jdbc:mysql://localhost:3306/supermarket_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static void main(String[] args) {

        Connection conn = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");
            System.out.println("Connection test passed!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();

        } finally {
            // Close connection if open
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}