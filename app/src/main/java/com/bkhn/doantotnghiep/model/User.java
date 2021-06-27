package com.bkhn.doantotnghiep.model;

public class User {
    private String userName;
    private String pass;
    private String id;

    public User(){}

    public User(String id, String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
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
}
