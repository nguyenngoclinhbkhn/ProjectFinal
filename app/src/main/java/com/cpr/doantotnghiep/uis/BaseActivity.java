package com.cpr.doantotnghiep.uis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());

        initView();
        initVariable();
    }

    public abstract int initLayout();
    public abstract void initVariable();
    public abstract void initView();


}
