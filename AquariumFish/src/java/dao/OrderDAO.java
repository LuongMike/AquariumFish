package dao;

import dto.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DButils;

public class OrderDAO {
    //lấy đơn hàng bằng userID
    public OrderDTO getPendingOrderByUserId(int userId) {
        String sql = "SELECT * FROM tblOrders WHERE userID = ? AND status = 'pending'";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OrderDTO(
                            rs.getInt("orderID"),
                            rs.getInt("userID"),
                            rs.getDouble("totalPrice"),
                            rs.getString("status"),
                            rs.getString("payment_method"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getPendingOrderByUserId: " + e.toString());
        }
        return null;
    }
//tạo đơn hàng
    public int createPendingOrder(int userId) {
        String sql = "INSERT INTO tblOrders (userID, totalPrice, status) VALUES (?, 0.0, 'pending')";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về orderID vừa tạo
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error at createPendingOrder: " + e.toString());
        }
        return -1;
    }
//cập nhật lại tổng tiền 
    public boolean updateTotalPrice(int orderId, double totalPrice) {
        String sql = "UPDATE tblOrders SET totalPrice = ? WHERE orderID = ?";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, totalPrice);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at updateTotalPrice: " + e.toString());
        }
        return false;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE tblOrders SET status = ? WHERE orderID = ?";
        try (Connection conn = utils.DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at updateOrderStatus: " + e.toString());
        }
        return false;
    }
}