package dao;

import dto.InvoiceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DButils;

public class InvoiceDAO {

    public int createInvoice(int orderId, double totalPrice) {
        String sql = "INSERT INTO tblInvoice (orderID, total_price, final_price) VALUES (?, ?, ?)";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orderId);
            ps.setDouble(2, totalPrice);
            ps.setDouble(3, totalPrice); // final_price = total_price (chưa tính thuế)
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về invoiceID
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error at createInvoice: " + e.toString());
        }
        return -1;
    }

    public InvoiceDTO getInvoiceByOrderId(int orderId) {
        String sql = "SELECT * FROM tblInvoice WHERE orderID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new InvoiceDTO(
                            rs.getInt("invoiceID"),
                            rs.getInt("orderID"),
                            rs.getDouble("total_price"),
                            rs.getDouble("tax"),
                            rs.getDouble("final_price"),
                            rs.getTimestamp("issued_at")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getInvoiceByOrderId: " + e.toString());
        }
        return null;
    }

    // Thêm phương thức getInvoicesByUserId
    public List<InvoiceDTO> getInvoicesByUserId(int userId) {
        List<InvoiceDTO> list = new ArrayList<>();
        String sql = "SELECT i.* FROM tblInvoice i JOIN tblOrders o ON i.orderID = o.orderID WHERE o.userID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new InvoiceDTO(
                            rs.getInt("invoiceID"),
                            rs.getInt("orderID"),
                            rs.getDouble("total_price"),
                            rs.getDouble("tax"),
                            rs.getDouble("final_price"),
                            rs.getTimestamp("issued_at")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getInvoicesByUserId: " + e.toString());
        }
        return list;
    }

    public InvoiceDTO getInvoiceById(int invoiceId) {
        String sql = "SELECT * FROM tblInvoice WHERE invoiceID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new InvoiceDTO(
                            rs.getInt("invoiceID"),
                            rs.getInt("orderID"),
                            rs.getDouble("total_price"),
                            rs.getDouble("tax"),
                            rs.getDouble("final_price"),
                            rs.getTimestamp("issued_at")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getInvoiceById: " + e.toString());
        }
        return null;
    }

    public List<InvoiceDTO> getPaidInvoicesByUserId(int userId) {
        List<InvoiceDTO> list = new ArrayList<>();
        String sql = "SELECT i.* FROM tblInvoice i "
                + "JOIN tblOrders o ON i.orderID = o.orderID "
                + "JOIN tblPayment p ON i.invoiceID = p.invoiceID "
                + "WHERE o.userID = ? AND p.status = 'completed'";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new InvoiceDTO(
                            rs.getInt("invoiceID"),
                            rs.getInt("orderID"),
                            rs.getDouble("total_price"),
                            rs.getDouble("tax"),
                            rs.getDouble("final_price"),
                            rs.getTimestamp("issued_at")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getPaidInvoicesByUserId: " + e.toString());
        }
        return list;
    }

    public boolean deleteInvoice(int invoiceId) {
        String sql = "DELETE FROM tblInvoice WHERE invoiceID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at deleteInvoice: " + e.toString());
        }
        return false;
    }
}
