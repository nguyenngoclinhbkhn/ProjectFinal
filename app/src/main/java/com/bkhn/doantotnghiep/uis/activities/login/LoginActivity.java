package com.bkhn.doantotnghiep.uis.activities.login;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bkhn.doantotnghiep.model.User;
import com.bkhn.doantotnghiep.presenter.login.PresenterLogin;
import com.bkhn.doantotnghiep.uis.activities.home.HomeActivity;
import com.bkhn.doantotnghiep.utils.HomeConfig;
import com.bkhn.doantotnghiep.R;
import com.bkhn.doantotnghiep.uis.BaseActivity;
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
    private ProgressBar progressBar;

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        edUser = findViewById(R.id.editTextIdAccount);
        edPass = findViewById(R.id.editTextPassAccount);
        btnLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progress);

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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupLayoutBtnLogin(true);
                final String userName = edUser.getText().toString().trim();
                final String pass = edPass.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass)) {
                    setupLayoutBtnLogin(false);
                    Toasty.error(LoginActivity.this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
                            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    userList.clear();
                                    User user = dataSnapshot.getValue(User.class);
                                    userList.add(user);
                                    presenterLogin.login(userName, pass, userList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }, 500);
                }
            }
        });
    }

    private void setupLayoutBtnLogin(Boolean isLogin) {
        if (isLogin) {
            btnLogin.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void loginSuccess(User user) {
        // user login success
        setupLayoutBtnLogin(false);
        sharedPreferences.edit().putInt(HomeConfig.STATE_LOGIN, HomeConfig.LOGIN_TRUE).apply();
        sharedPreferences.edit().putString(HomeConfig.ID_USER, user.getId()).apply();
        startActivity(new Intent(this, HomeActivity.class));
        Toasty.success(this, "Login success", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void loginFail(String message) {
        // user login fail
        setupLayoutBtnLogin(false);
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
    }
}
