package com.jundat95.tinhngo.sharelocation.repository.local.model;

/**
 * Created by tinhngo on 10/26/17.
 */

public class UserModel {

    private String id;
    private String userName;
    private String password;
    private String fullName;
    private String employeeCode;

    public UserModel(String id, String userName, String password, String fullName, String employeeCode) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.employeeCode = employeeCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }


}
