package supermarket.dao;

import supermarket.model.Product;
import supermarket.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO product (name, price, stock_qty, category_id, supplier_id) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt   (3, p.getStockQty());
            ps.setInt   (4, p.getCategoryId());
            ps.setInt   (5, p.getSupplierId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateProduct(Product p) throws SQLException {
        String sql = "UPDATE product SET name=?, price=?, stock_qty=?, "
                   + "category_id=?, supplier_id=? WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt   (3, p.getStockQty());
            ps.setInt   (4, p.getCategoryId());
            ps.setInt   (5, p.getSupplierId());
            ps.setInt   (6, p.getProductId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("product_id"), rs.getString("name"),
                    rs.getDouble("price"),   rs.getInt("stock_qty"),
                    rs.getInt("category_id"),rs.getInt("supplier_id")
                ));
            }
        }
        return list;
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getInt("product_id"), rs.getString("name"),
                    rs.getDouble("price"),   rs.getInt("stock_qty"),
                    rs.getInt("category_id"),rs.getInt("supplier_id")
                );
            }
        }
        return null;
    }
}
