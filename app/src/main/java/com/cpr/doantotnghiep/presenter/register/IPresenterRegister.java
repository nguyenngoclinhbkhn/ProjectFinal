package com.cpr.doantotnghiep.presenter.register;

import com.cpr.doantotnghiep.model.User;

import java.util.List;

public interface IPresenterRegister {
    void register(String userName, String pass, int permission, List<User> list);
}
