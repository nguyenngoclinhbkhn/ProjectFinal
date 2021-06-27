package com.bkhn.doantotnghiep.uis.activities.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bkhn.doantotnghiep.utils.HomeConfig;
import com.bkhn.doantotnghiep.R;
import com.bkhn.doantotnghiep.uis.BaseActivity;
import com.bkhn.doantotnghiep.uis.activities.home.HomeActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Button btnCancel;
    private Button btnOk;
    private ImageView imgBack;
    private EditText edTemperatureMax;
    private int gasMax;
    private SharedPreferences sharedPreferences;

    @Override
    public int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        btnCancel = findViewById(R.id.btnCancelSetting);
        btnOk = findViewById(R.id.btnOkSetting);
        edTemperatureMax = findViewById(R.id.edTemperatureMax);
        imgBack = findViewById(R.id.imgViewBackSetting);

    }

    @Override
    public void initVariable() {
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        gasMax = sharedPreferences.getInt(HomeConfig.GAS, HomeConfig.GAS_MAX);
        setupdata();
        btnOk.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setupdata(){
        if (gasMax != 0) {
            edTemperatureMax.setText(String.valueOf(gasMax));
        }else{
            edTemperatureMax.setText(String.valueOf(HomeConfig.GAS_MAX));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancelSetting : {
                startActivity(new Intent(this, HomeActivity.class));
                finish();

            }break;
            case R.id.btnOkSetting : {
                int temMax = Integer.parseInt(edTemperatureMax.getText().toString().trim());
                commitData(temMax);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } break;
            case R.id.imgViewBackSetting : {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } break;
        }
    }
    private void commitData(int temMax){
        sharedPreferences.edit().putInt(HomeConfig.GAS, temMax).apply();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
