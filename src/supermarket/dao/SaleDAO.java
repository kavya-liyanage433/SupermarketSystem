package supermarket.dao;

import supermarket.model.Sale;
import supermarket.model.SaleItem;
import supermarket.util.DBConnection;
import java.sql.*;

public class SaleDAO {

    public boolean saveSale(Sale sale) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            // 1. Insert into sale table
            String saleSql = "INSERT INTO sale (customer_id, total_amount, cashier_name) "
                           + "VALUES (?, ?, ?)";
            int saleId;
            try (PreparedStatement ps = conn.prepareStatement(saleSql,
                                         Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt   (1, sale.getCustomerId());
                ps.setDouble(2, sale.getTotalAmount());
                ps.setString(3, sale.getCashierName());
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                saleId = keys.getInt(1);
            }

            // 2. Insert each sale_item
            String itemSql = "INSERT INTO sale_item (sale_id, product_id, qty, unit_price, subtotal) "
                           + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (SaleItem item : sale.getItems()) {
                    ps.setInt   (1, saleId);
                    ps.setInt   (2, item.getProductId());
                    ps.setInt   (3, item.getQty());
                    ps.setDouble(4, item.getUnitPrice());
                    ps.setDouble(5, item.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 3. Update stock
            String stockSql = "UPDATE product SET stock_qty = stock_qty - ? "
                            + "WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(stockSql)) {
                for (SaleItem item : sale.getItems()) {
                    ps.setInt(1, item.getQty());
                    ps.setInt(2, item.getProductId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
