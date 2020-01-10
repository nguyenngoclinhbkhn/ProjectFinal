package com.cpr.doantotnghiep.model;

public class User {
    private String id;
    private String userName;
    private String pass;
    private int permission;

    public User(){}

    public User(String id, String userName, String pass, int permission) {
        this.id = id;
        this.userName = userName;
        this.pass = pass;
        this.permission = permission;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
