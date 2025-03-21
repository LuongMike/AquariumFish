package dao;

import dto.OrderDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DButils;

public class OrderDetailDAO {
    //thêm chi tiết đặt hàng
    public boolean addOrderDetail(int orderId, int fishId, int quantity, double price) {
        String sql = "INSERT INTO tblOrder_Details (orderID, fishID, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, fishId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at addOrderDetail: " + e.toString());
        }
        return false;
    }
//lấy ra danh sách thông tin sản phẩm
    public List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetailDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblOrder_Details WHERE orderID = ?";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new OrderDetailDTO(
                            rs.getInt("orderDetailID"),
                            rs.getInt("orderID"),
                            rs.getInt("fishID"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getOrderDetailsByOrderId: " + e.toString());
        }
        return list;
    }
    
    public boolean removeOrderDetail(int orderDetailId) {
        String sql = "DELETE FROM tblOrder_Details WHERE orderDetailID = ?";
        try (Connection conn = utils.DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderDetailId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error at removeOrderDetail: " + e.toString());
        }
        return false;
    }
}