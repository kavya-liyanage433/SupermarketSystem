package supermarket.view;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import supermarket.util.DBConnection;
import java.sql.Connection;

public class ReportViewer {

    public static void showSalesReport() {
        try {
            Connection conn = DBConnection.getConnection();
             String reportPath = "C:\\NetBeansProjects\\SupermarketSystem\\src\\supermarket\\view\\SalesReports_1.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
            conn.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Report Error: " + e.getMessage());
        }
    }
}