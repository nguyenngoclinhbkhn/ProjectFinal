package com.bkhn.doantotnghiep.uis.activities.login;

import com.bkhn.doantotnghiep.model.User;

public interface LoginView {
    void loginSuccess(User user);
    void loginFail(String message);

    void loginTrue();
    void loginFail();
    void getUser(User user);
}

