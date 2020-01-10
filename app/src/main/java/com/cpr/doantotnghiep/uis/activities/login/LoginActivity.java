package com.cpr.doantotnghiep.uis.activities.login;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.presenter.login.PresenterLogin;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.LivingRoomActivity;
import com.cpr.doantotnghiep.uis.activities.home.HomeActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements LoginView {
    private EditText edUser;
    private EditText edPass;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private List<User> userList;
    private PresenterLogin presenterLogin;

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        edUser = findViewById(R.id.editTextIdAccount);
        edPass = findViewById(R.id.editTextPassAccount);
        btnLogin = findViewById(R.id.buttonLogin);

    }

    @Override
    public void initVariable() {
        presenterLogin = new PresenterLogin(this);
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        int state = sharedPreferences.getInt(HomeConfig.STATE_LOGIN, HomeConfig.LOGIN_FALSE);
        if (state == HomeConfig.LOGIN_TRUE) {
            presenterLogin.checkLogin(true);
        } else {
            presenterLogin.checkLogin(false);
        }
        userList = new ArrayList<>();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("admin");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    userList.add(user);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edUser.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass)) {
                    Toasty.error(LoginActivity.this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    presenterLogin.login(userName, pass, userList);
                }
            }
        });
    }

    @Override
    public void loginSuccess(User user) {
        // user login success
        sharedPreferences.edit().putInt(HomeConfig.PERMISSION, user.getPermission()).commit();
        sharedPreferences.edit().putInt(HomeConfig.STATE_LOGIN, HomeConfig.LOGIN_TRUE).commit();
        sharedPreferences.edit().putString(HomeConfig.ID_USER, user.getId()).commit();
        startActivity(new Intent(this, HomeActivity.class));
        Toasty.success(this, "Login success", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void loginFail(String message) {
        // user login fail
        Toasty.error(this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginTrue() {
        // user is not sign out
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void loginFail() {
        // user is sign out
    }

    @Override
    public void getUser(User user) {
        sharedPreferences.edit().putInt(HomeConfig.PERMISSION, user.getPermission()).commit();
    }
}
