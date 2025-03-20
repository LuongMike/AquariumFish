/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CategoryDTO;
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
public class categoryDAO implements IDAO<CategoryDTO, String> {

    @Override
    public boolean create(CategoryDTO entity) {
        return false;
    }

    @Override
    public List<CategoryDTO> readAll() {
        List<CategoryDTO> list = new ArrayList<CategoryDTO>();
        String sql = "SELECT * FROM tblCategory";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryDTO user = new CategoryDTO(
                        rs.getString("categoryID"),
                        rs.getString("categoryName")
                );
                list.add(user);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        } 
        return list;
    }

    @Override
    public boolean update(CategoryDTO entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<CategoryDTO> search(String searchTerm) {
        return null;
    }
}
