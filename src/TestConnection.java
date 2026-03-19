package supermarket.test; 

import supermarket.util.DBConnection; 
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
