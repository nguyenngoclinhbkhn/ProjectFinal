package com.cpr.doantotnghiep.presenter.edit_account;

import android.text.TextUtils;

import com.cpr.doantotnghiep.uis.activities.edit_account.EditAccountView;

public class PresenterEditAccount implements IPresenterEditAccount{
    private EditAccountView editAccountView;


    public PresenterEditAccount(EditAccountView editAccountView){
        this.editAccountView = editAccountView;
    }

    @Override
    public void onAccountEdit(String pass) {
        if (!TextUtils.isEmpty(pass)){
            if (pass.length() > 4) {
                editAccountView.editSuccess(pass);
            }else{
                editAccountView.editPassFail();
            }
        }else{
            editAccountView.editFail();
        }

    }
}
