/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.UserDAO;
import dto.UserDTO;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class AuthenUtils {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^[0-9]{10,11}$";
    private static final String NAME_REGEX = "^[A-Za-zÀ-Ỹà-ỹ\\s]{3,50}$"; // Chỉ chấp nhận chữ cái, từ 3-50 ký tự
    public static final String Admin_ROLE = "admin";
    public static final String Customer_ROLE = "customer";

    
    public static UserDTO getUser(String account) {
        UserDAO udao = new UserDAO();
        return udao.readbyAccount(account);
    }

    public static boolean isValidLogin(String account, String password) {
        UserDTO user = getUser(account);
        if (user != null && PasswordUtils.checkPassword(password, user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public static boolean isAdmin(HttpSession session) {
        if (!isLoggedIn(session)) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        return user.getRole().equals(Admin_ROLE);
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
