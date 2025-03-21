/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.DiscountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DButils;

/**
 *
 * @author PC
 */
public class DiscountDAO implements IDAO<DiscountDTO, String> {

    @Override
    public boolean create(DiscountDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DiscountDTO> readAll() {
        List<DiscountDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblDiscount";

        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DiscountDTO discount = new DiscountDTO(
                        rs.getInt("discountID"),
                        rs.getString("code"),
                        rs.getDouble("discount_percentage"),
                        rs.getDouble("discount_amount"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("status")
                );
                list.add(discount);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DiscountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    @Override
    public boolean update(DiscountDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DiscountDTO> search(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DiscountDTO getDiscountByCode(String code) {
        String sql = "SELECT discountID, code, discount_percentage, discount_amount, start_date, end_date, status "
                + "FROM tblDiscount WHERE code = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DiscountDTO(
                            rs.getInt("discountID"),
                            rs.getString("code"),
                            rs.getDouble("discount_percentage"),
                            rs.getDouble("discount_amount"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getDiscountByCode: " + e.toString());
        }
        return null;
    }

    public DiscountDTO getDiscountById(int discountID) {
        String sql = "SELECT discountID, code, discount_percentage, discount_amount, start_date, end_date, status "
                + "FROM tblDiscount WHERE discountID = ?";
        try (Connection conn = DButils.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, discountID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DiscountDTO(
                            rs.getInt("discountID"),
                            rs.getString("code"),
                            rs.getDouble("discount_percentage"),
                            rs.getDouble("discount_amount"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getDiscountById: " + e.toString());
        }
        return null;
    }
}
