package com.cpr.doantotnghiep.uis.activities.register;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.presenter.register.PresenterRegister;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.manager_user.ManagerUserActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    private PresenterRegister presenterRegister;
    private DatabaseReference firebaseDatabase;
    private EditText edUserName;
    private EditText edPass;
    private TextView txtAdd;
    private NumberPicker pickerPermission;
    private ImageView imgBack;
    private String key;
    private String userName;
    private String pass;
    private int permission;
    private List<User> list;


    @Override
    public int initLayout() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {
        edUserName = findViewById(R.id.edUserName);
        edPass = findViewById(R.id.edPassword);
        pickerPermission = findViewById(R.id.pickerPermission);
        txtAdd = findViewById(R.id.txtAddNewUser);
        imgBack = findViewById(R.id.imgViewBackAddUser);

    }

    @Override
    public void initVariable() {
        presenterRegister = new PresenterRegister(this);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("admin");
        pickerPermission.setMinValue(0);
        pickerPermission.setMaxValue(3);
        list = new ArrayList<>();
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    list.add(d.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgBack.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }


    @Override
    public void registerSucces() {
        firebaseDatabase.child(key).setValue(new User(key, userName, pass, permission));
        Toasty.success(this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ManagerUserActivity.class));
        finish();
    }

    @Override
    public void registerFail() {
        Toasty.error(this, "Tài khoản hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerDataNotValid() {
        Toasty.error(this, "Tài khoản này đã tồn tại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerPassFail() {
        Toasty.error(this, "Độ dài mật khẩu phải lớn hơn 4 ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewBackAddUser: {
                startActivity(new Intent(this, ManagerUserActivity.class));
                finish();
            }
            break;
            case R.id.txtAddNewUser: {
                key = firebaseDatabase.push().getKey();
                userName = edUserName.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                permission = pickerPermission.getValue();
                presenterRegister.register(userName, pass, permission, list);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManagerUserActivity.class));
        finish();
    }
}
