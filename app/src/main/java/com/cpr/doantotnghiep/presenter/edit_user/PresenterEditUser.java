package com.cpr.doantotnghiep.presenter.edit_user;

import com.cpr.doantotnghiep.presenter.register.PresenterRegister;
import com.cpr.doantotnghiep.uis.activities.edit_user.EditUserView;

public class PresenterEditUser implements IPresenterEditUser{
    private EditUserView editUserView;
    public PresenterEditUser(EditUserView editUserView){
        this.editUserView = editUserView;
    }
    @Override
    public void editUser(int permission) {
        editUserView.editSuccess();
    }
}
