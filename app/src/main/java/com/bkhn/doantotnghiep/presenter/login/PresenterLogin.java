package com.bkhn.doantotnghiep.presenter.login;

import com.bkhn.doantotnghiep.model.User;
import com.bkhn.doantotnghiep.uis.activities.login.LoginView;

import java.util.List;

public class PresenterLogin implements IPresenterLogin {
    private LoginView loginView;
    public PresenterLogin(LoginView loginView){
        this.loginView = loginView;
    }


    @Override
    public void login(String user, String pass, List<User> list) {
        boolean isLogin = false;
        User user1 = null;
        for (User u : list){
            if (u.getUserName().equals(user) && u.getPass().equals(pass)){
                isLogin = true;
                loginView.getUser(u);
                user1 = u;
                break;
            }else{
                isLogin = false;
            }
        }
        if (isLogin){
            loginView.loginSuccess(user1);
        }else{
            loginView.loginFail("Fail");
        }
    }

    @Override
    public void checkLogin(boolean isLogin) {
        if (isLogin){
            loginView.loginTrue();
        }else{
            loginView.loginFail();
        }
    }
}
