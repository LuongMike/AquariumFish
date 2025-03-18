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
        return false;
    }

    @Override
    public List<FishDTO> readAll() {
        List<FishDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [tblFish]";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
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
            Logger.getLogger(FishDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    @Override
    public boolean update(FishDTO entity) {
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
        String sql = "SELECT * FROM [tblFish] WHERE [fishType] LIKE ?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
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


}
