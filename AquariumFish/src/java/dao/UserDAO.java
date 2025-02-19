/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import ultis.DBultis;

/**
 *
 * @author PC
 */
public class UserDAO implements IDAO<UserDTO, String> {

    @Override
    public boolean create(UserDTO entity) {
        String sql = "INSERT INTO [tblUsers] ([userId], [fullName], [roleId], [password])"
                + "VALUES(?,?,?,?)";
        try {
            Connection conn = DBultis.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getUserId());
            ps.setString(2, entity.getFullName());
            ps.setString(3, entity.getRoldId());
            ps.setString(4, entity.getPassword());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<UserDTO> readAll() {
        List<UserDTO> list = new ArrayList<UserDTO>();
        String sql = "SELECT * FROM [tblUsers]";
        try {
            Connection conn = DBultis.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getString("userId"),
                        rs.getString("fullName"),
                        rs.getString("roleId"),
                        rs.getString("password"));
                list.add(user);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    @Override
    public UserDTO readbyID(String id) {
        String sql = "SELECT * FROM [tblUsers] WHERE [userId] = ?";
        try {
            Connection conn = DBultis.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getString("userId"),
                        rs.getString("fullName"),
                        rs.getString("roleId"),
                        rs.getString("password"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    @Override
    public boolean update(UserDTO entity) {
        String sql = "UPDATE [tblUsers] SET "
                + "[fullName] = ?, "
                + "[roleId] = ?, "
                + "[password] = ?"
                + "WHERE [userId] = ?, ";
        try {
            Connection conn = DBultis.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getRoldId());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getUserId());
            int n = ps.executeUpdate();
            return n > 0;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM [tblUsers] WHERE [userId] = ?";
        try {
            Connection conn = DBultis.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<UserDTO> search(String searchTerm) {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT [userId], [fulName], [roleId], [password] FROM [tblUsers]"
                + "WHERE [userId] LIKE N'%" + searchTerm + "%'"
                + "OR [fullName] LIKE N'%" + searchTerm + "%'"
                + "OR [roleId] LIKE N'%" + searchTerm + "%'";
        try {
            Connection conn = DBultis.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getString("userId"),
                        rs.getString("fullName"),
                        rs.getString("roleId"),
                        rs.getString("password")
                );
                list.add(user);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
