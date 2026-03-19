package supermarket.util;

import javax.swing.*;
import java.awt.*;

public class Validator {

    // Check if field is empty
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Check if value is a valid number
    public static boolean isDouble(String value) {
        try { Double.parseDouble(value); return true; }
        catch (NumberFormatException e) { return false; }
    }

    // Check if value is a valid integer
    public static boolean isInteger(String value) {
        try { Integer.parseInt(value); return true; }
        catch (NumberFormatException e) { return false; }
    }

    // Check if number is positive
    public static boolean isPositive(String value) {
        try { return Double.parseDouble(value) > 0; }
        catch (NumberFormatException e) { return false; }
    }

    // Highlight field red if invalid
    public static void markError(JTextField field) {
        field.setBackground(new Color(255, 100, 100));
        field.setForeground(Color.WHITE);
    }

    // Reset field to normal
    public static void markNormal(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        field.setForeground(fg);
    }

    // Show error message
    public static void showError(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Validation Error",
            JOptionPane.ERROR_MESSAGE);
    }

    // Show success message
    public static void showSuccess(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // Validate Product Form fields
    public static boolean validateProduct(JTextField txtName,
                                          JTextField txtPrice,
                                          JTextField txtStock,
                                          Color inputBg, Color textWhite) {
        boolean valid = true;

        markNormal(txtName, inputBg, textWhite);
        markNormal(txtPrice, inputBg, textWhite);
        markNormal(txtStock, inputBg, textWhite);

        if (isEmpty(txtName.getText())) {
            markError(txtName);
            showError(txtName, "Product name cannot be empty!");
            valid = false;
        } else if (txtName.getText().trim().length() < 2) {
            markError(txtName);
            showError(txtName, "Product name must be at least 2 characters!");
            valid = false;
        } else if (isEmpty(txtPrice.getText()) || !isDouble(txtPrice.getText())) {
            markError(txtPrice);
            showError(txtPrice, "Price must be a valid number!");
            valid = false;
        } else if (!isPositive(txtPrice.getText())) {
            markError(txtPrice);
            showError(txtPrice, "Price must be greater than 0!");
            valid = false;
        } else if (isEmpty(txtStock.getText()) || !isInteger(txtStock.getText())) {
            markError(txtStock);
            showError(txtStock, "Stock quantity must be a whole number!");
            valid = false;
        } else if (Integer.parseInt(txtStock.getText()) < 0) {
            markError(txtStock);
            showError(txtStock, "Stock quantity cannot be negative!");
            valid = false;
        }
        return valid;
    }

    // Validate Sale Form fields
    public static boolean validateSale(JTextField txtCashier,
                                        JTextField txtProductId,
                                        JTextField txtQty,
                                        Color inputBg, Color textWhite) {
        boolean valid = true;

        markNormal(txtCashier, inputBg, textWhite);
        markNormal(txtProductId, inputBg, textWhite);
        markNormal(txtQty, inputBg, textWhite);

        if (isEmpty(txtCashier.getText())) {
            markError(txtCashier);
            showError(txtCashier, "Cashier name cannot be empty!");
            valid = false;
        } else if (isEmpty(txtProductId.getText()) || !isInteger(txtProductId.getText())) {
            markError(txtProductId);
            showError(txtProductId, "Product ID must be a valid number!");
            valid = false;
        } else if (isEmpty(txtQty.getText()) || !isInteger(txtQty.getText())) {
            markError(txtQty);
            showError(txtQty, "Quantity must be a whole number!");
            valid = false;
        } else if (Integer.parseInt(txtQty.getText()) <= 0) {
            markError(txtQty);
            showError(txtQty, "Quantity must be greater than 0!");
            valid = false;
        }
        return valid;
    }
}