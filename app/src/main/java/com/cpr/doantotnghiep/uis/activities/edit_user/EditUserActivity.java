package com.cpr.doantotnghiep.uis.activities.edit_user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.presenter.edit_user.PresenterEditUser;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.manager_user.ManagerUserActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class EditUserActivity extends BaseActivity implements View.OnClickListener, EditUserView {
    private EditText edUserName;
    private EditText edPass;
    private NumberPicker pickerPermission;
    private ImageView imgBack;
    private TextView txtOk;
    private DatabaseReference reference;
    private PresenterEditUser presenterEditUser;
    private int permission;
    private User userToEdit;
    private String idUser;
    @Override
    public int initLayout() {
        return R.layout.activity_edit_user;
    }

    @Override
    public void initView() {
        edUserName = findViewById(R.id.edEditUserName);
        edPass = findViewById(R.id.edEditPassword);
        txtOk = findViewById(R.id.txtEditNewUser);
        imgBack = findViewById(R.id.imgViewBackEditUser);
        pickerPermission = findViewById(R.id.pickerEditPermission);


    }

    @Override
    public void initVariable() {
        Intent intent = getIntent();
        idUser = intent.getStringExtra(HomeConfig.KEY);
        presenterEditUser = new PresenterEditUser(this);
        pickerPermission.setMinValue(0);
        pickerPermission.setMaxValue(3);
        reference = FirebaseDatabase.getInstance().getReference("admin");
        reference.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userToEdit = dataSnapshot.getValue(User.class);
                edPass.setText(userToEdit.getPass());
                edUserName.setText(userToEdit.getUserName());
                pickerPermission.setValue(userToEdit.getPermission());
                permission = userToEdit.getPermission();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtOk.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtEditNewUser: {
                if (permission == 3) {
                    Toasty.error(this, "Bạn không thể chỉnh sửa tài khoản này", Toast.LENGTH_SHORT).show();
                }else{
                    permission = pickerPermission.getValue();
                    presenterEditUser.editUser(permission);
                }
            }
            break;
            case R.id.imgViewBackEditUser: {
                startActivity(new Intent(this, ManagerUserActivity.class));
                finish();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManagerUserActivity.class));
        finish();
    }

    @Override
    public void editSuccess() {
        userToEdit.setPermission(permission);
        reference.child(idUser).setValue(userToEdit);
        Toasty.success(this, "Chỉnh sửa thành công ", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ManagerUserActivity.class));
        finish();
    }

    @Override
    public void editFail() {
        Toasty.error(this, "Chỉnh sửa thông tin không thành công", Toast.LENGTH_SHORT).show();
    }
}
