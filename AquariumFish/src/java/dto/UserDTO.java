/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author PC
 */
public class UserDTO {
    private int userId;
    private String userName;
    private String account;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;   
    private double balance;

    public UserDTO() {
    }

    public UserDTO(int userId, String userName, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
    }
    
    public UserDTO(int userId, String userName, String account, String password, String email, String phone, String address, String role) {
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public UserDTO(int userId, String userName, String account, String password, String email, String phone, String address, String role, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "userId=" + userId + ", userName=" + userName + ", account=" + account + ", password=" + password + ", email=" + email + ", phone=" + phone + ", address=" + address + ", role=" + role + '}';
    }
    
    
    
}

