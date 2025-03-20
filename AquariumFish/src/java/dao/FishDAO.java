/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.FishDTO;
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
public class FishDAO implements IDAO<FishDTO, String> {

    @Override
    public boolean create(FishDTO entity) {
        String sql = "INSERT INTO tblFish"
                + " (fishType,fishName,fishPrice,fishQuantity,fishDescription,fishImg,categoryID) "
                + "  VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFishType());
            ps.setString(2, entity.getFishName());
            ps.setDouble(3, entity.getFishPrice());
            ps.setInt(4, entity.getFishQuantity());
            ps.setString(5, entity.getFishDescription());
            ps.setString(6, entity.getFishImg());
            ps.setInt(7, entity.getCategoryID());
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
//    public List<FishDTO> readAll() {
//        List<FishDTO> list = new ArrayList<>();
//        String sql = "SELECT * FROM tblFish WHERE fishQuantity > 0";
//        try {
//            Connection conn = DButils.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                FishDTO fish = new FishDTO(
//                        rs.getInt("fishID"),
//                        rs.getString("fishType"),
//                        rs.getString("fishName"),
//                        rs.getDouble("fishPrice"),
//                        rs.getInt("fishQuantity"),
//                        rs.getString("fishDescription"),
//                        rs.getString("fishImg"),
//                        rs.getInt("categoryID")
//                );
//                list.add(fish);
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(FishDAO.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return list;
//    }
    public List<FishDTO> readAll() {
        List<FishDTO> list = new ArrayList<>();
        String sql = "SELECT f.fishID, f.fishType, f.fishName, f.fishPrice, f.fishQuantity, f.fishDescription, f.fishImg, f.categoryID, c.categoryName " +
                     "FROM tblFish f JOIN tblCategory c ON f.categoryID = c.categoryID WHERE f.fishQuantity > 0";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FishDTO fish = new FishDTO(
                        rs.getInt("fishID"),
                        rs.getString("fishType"),
                        rs.getString("fishName"),
                        rs.getDouble("fishPrice"),
                        rs.getInt("fishQuantity"),
                        rs.getString("fishDescription"),
                        rs.getString("fishImg"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName")
                );
                list.add(fish);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FishDAO.class.getName()).log(Level.SEVERE, "Error at readAll: " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(FishDAO.class.getName()).log(Level.SEVERE, "Error closing resources in readAll: " + ex.getMessage(), ex);
            }
        }
        return list;
    }

    @Override
    public boolean update(FishDTO entity) {
        String sql = "UPDATE tblFish SET "
                + " fishType=?, fishName=?, fishPrice=?, fishQuantity=?, fishDescription=?, fishImg=?, categoryID=? "
                + "  WHERE fishID=?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFishType());
            ps.setString(2, entity.getFishName());
            ps.setDouble(3, entity.getFishPrice());
            ps.setInt(4, entity.getFishQuantity());
            ps.setString(5, entity.getFishDescription());
            ps.setString(6, entity.getFishImg());
            ps.setInt(7, entity.getCategoryID());
            ps.setInt(8, entity.getFishID());
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<FishDTO> search(String searchTerm) {
        return null;
    }

    public List<FishDTO> searchByType(String searchTerm) {
        List<FishDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblFish WHERE (fishType LIKE ? OR fishName LIKE ?) AND fishQuantity > 0";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            String param = "%" + searchTerm + "%";
            ps.setString(1, param);
            ps.setString(2, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FishDTO fish = new FishDTO(
                        rs.getInt("fishID"),
                        rs.getString("fishType"),
                        rs.getString("fishName"),
                        rs.getDouble("fishPrice"),
                        rs.getInt("fishQuantity"),
                        rs.getString("fishDescription"),
                        rs.getString("fishImg"),
                        rs.getInt("categoryID")
                );
                list.add(fish);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public boolean updateQuantityToZero(String id) {
        String sql = "UPDATE tblFish SET fishQuantity=0 WHERE fishID=?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public FishDTO readbyID(String id) {
        String sql = "SELECT * FROM tblFish WHERE fishID = ?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FishDTO b = new FishDTO(
                        rs.getInt("fishID"),
                        rs.getString("fishType"),
                        rs.getString("fishName"),
                        rs.getDouble("fishPrice"),
                        rs.getInt("fishQuantity"),
                        rs.getString("fishDescription"),
                        rs.getString("fishImg"),
                        rs.getInt("categoryID")
                );
                return b;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    
    public List<FishDTO> getFishByCategory(int categoryID) {
        List<FishDTO> fishList = new ArrayList<>();
        String sql = "SELECT f.fishID, f.fishType, f.fishName, f.fishPrice, f.fishQuantity, f.fishDescription, f.fishImg, f.categoryID, c.categoryName " +
                    "FROM tblFish f JOIN tblCategory c ON f.categoryID = c.categoryID WHERE f.categoryID = ?";

        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryID);
                        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FishDTO fish = new FishDTO(
                        rs.getInt("fishID"),
                        rs.getString("fishType"),
                        rs.getString("fishName"),
                        rs.getDouble("fishPrice"),
                        rs.getInt("fishQuantity"),
                        rs.getString("fishDescription"),
                        rs.getString("fishImg"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName")
                );
                fishList.add(fish);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return fishList;
    }

}
