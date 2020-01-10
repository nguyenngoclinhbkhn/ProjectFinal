package com.cpr.doantotnghiep.uis.activities.login;

import com.cpr.doantotnghiep.model.User;

public interface LoginView {
    void loginSuccess(User user);
    void loginFail(String message);

    void loginTrue();
    void loginFail();
    void getUser(User user);
}

