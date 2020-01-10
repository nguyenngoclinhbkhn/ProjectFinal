package com.cpr.doantotnghiep.presenter.login;

import com.cpr.doantotnghiep.model.User;

import java.util.List;

public interface IPresenterLogin {
    void login(String user, String pass, List<User> list);
    void checkLogin(boolean isLogin);
}
