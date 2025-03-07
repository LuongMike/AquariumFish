/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.UserDAO;
import dto.UserDTO;
import java.util.regex.Pattern;

/**
 *
 * @author PC
 */
public class AuthenUtils {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^[0-9]{10,11}$";
    private static final String NAME_REGEX = "^[A-Za-zÀ-Ỹà-ỹ\\s]{3,50}$"; // Chỉ chấp nhận chữ cái, từ 3-50 ký tự

    public static UserDTO getUser(String account) {
        UserDAO udao = new UserDAO();
        return udao.readbyAccount(account);
    }

    public static boolean isValidLogin(String account, String password) {
        UserDTO user = getUser(account);
        return user != null && user.getPassword().equals(password);
    }

    //validate User Update
    public static boolean isValidUserName(String userName) {
        return userName != null && userName.matches(NAME_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.length() >= 5 && address.length() <= 100;
    }

    public static boolean isValidRole(String role) {
        return role != null && (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("customer"));
    }
}
