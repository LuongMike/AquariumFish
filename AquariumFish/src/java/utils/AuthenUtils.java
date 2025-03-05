/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.UserDAO;
import dto.UserDTO;

/**
 *
 * @author PC
 */
public class AuthenUtils {

    public static UserDTO getUser(String account) {
        UserDAO udao = new UserDAO();
        return udao.readbyAccount(account);
    }

    public static boolean isValidLogin(String account, String password) {
        UserDTO user = getUser(account);
        return user != null && user.getPassword().equals(password);
    }
}
