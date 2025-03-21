/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DButils;

/**
 *
 * @author PC
 */
public class PaymentDAO {

    public boolean createPayment(int invoiceId, double amount) {
        String sql = "INSERT INTO tblPayment (invoiceID, transaction_id, amount, status, payment_method) "
                + "VALUES (?, ?, ?, 'completed', 'balance')";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ps.setString(2, "TXN" + System.currentTimeMillis()); // Tạo transaction_id đơn giản
            ps.setDouble(3, amount);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at createPayment: " + e.toString());
        }
        return false;
    }

    public boolean isInvoicePaid(int invoiceId) {
        String sql = "SELECT COUNT(*) FROM tblPayment WHERE invoiceID = ? AND status = 'completed'";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error at isInvoicePaid: " + e.toString());
        }
        return false;
    }

    // Thêm phương thức để xóa bản ghi thanh toán nếu cần hoàn tác
    public boolean deletePayment(int invoiceId) {
        String sql = "DELETE FROM tblPayment WHERE invoiceID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deletePayment: " + e.toString());
        }
        return false;
    }
}
