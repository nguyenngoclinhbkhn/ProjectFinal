package com.bkhn.doantotnghiep.presenter.login;

import com.bkhn.doantotnghiep.model.User;

import java.util.List;

public interface IPresenterLogin {
    void login(String user, String pass, List<User> list);
    void checkLogin(boolean isLogin);
}
