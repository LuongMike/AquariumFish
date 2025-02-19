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
    private String userId;
    private String fullName;
    private String roldId;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String userId, String fullName, String roldId, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.roldId = roldId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoldId() {
        return roldId;
    }

    public void setRoldId(String roldId) {
        this.roldId = roldId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "userId=" + userId + ", fullName=" + fullName + ", roldId=" + roldId + ", password=" + password + '}';
    }
    
    
}
