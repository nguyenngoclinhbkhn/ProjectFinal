package com.cpr.doantotnghiep.presenter.register;

import android.text.TextUtils;

import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.uis.activities.register.RegisterView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PresenterRegister implements IPresenterRegister {
    private RegisterView registerView;
    public PresenterRegister(RegisterView registerView){
        this.registerView = registerView;
    }
    @Override
    public void register(String userName, String pass, int permission, List<User> list) {
        boolean isFalse = false;
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(pass)){
            if (pass.length() < 4){
                registerView.registerPassFail();
            }else {
                for (User user : list) {
                    if (user.getUserName().equals(userName)) {
                        isFalse = true;
                        break;
                    }
                }
                if (isFalse) {
                    registerView.registerDataNotValid();
                } else {
                    registerView.registerSucces();
                }
            }
        }else{
            registerView.registerFail();
        }

    }
}
