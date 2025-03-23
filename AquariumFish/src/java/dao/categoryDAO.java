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

public class categoryDAO implements IDAO<CategoryDTO, String> {

    @Override
    public boolean create(CategoryDTO entity) {
        return false;
    }

    @Override
    public List<CategoryDTO> readAll() {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT categoryID, categoryName FROM tblCategory";
        try (Connection conn = DButils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoryDTO category = new CategoryDTO(
                        rs.getString("categoryID"),
                        rs.getString("categoryName")
                );
                list.add(category);
            }
            Logger.getLogger(categoryDAO.class.getName()).log(Level.INFO, "Successfully retrieved {0} categories", list.size());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(categoryDAO.class.getName()).log(Level.SEVERE, "Error retrieving categories", ex);
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