package dao;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import dto.OrderDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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

    public List<OrderDetailDTO> getOrderDetailsByOrderId(int orderId) throws ClassNotFoundException {
        List<OrderDetailDTO> details = new ArrayList<>();
        String sql = "SELECT orderDetailID, orderID, fishID, quantity, price FROM [tblOrder_Details] WHERE orderID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetailDTO detail = new OrderDetailDTO(
                            rs.getInt("orderDetailID"),
                            rs.getInt("orderID"),
                            rs.getInt("fishID"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    details.add(detail);
                }
                return details;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving order details for order " + orderId + ": " + e.getMessage(), e);
            return null;
        }
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
