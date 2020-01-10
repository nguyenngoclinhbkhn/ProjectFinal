package com.cpr.doantotnghiep.uis.activities.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.home.HomeActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Button btnCancel;
    private Button btnOk;
    private ImageView imgBack;
    private EditText edTemperatureMax;
    private EditText edTemperatureMin;
    private EditText edHumidityMax;
    private EditText edHumidityMin;
    private SharedPreferences sharedPreferences;
    private int temperatureMax;
    private int temperatureMin;
    private int humidityMax;
    private int humidityMin;

    @Override
    public int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        btnCancel = findViewById(R.id.btnCancelSetting);
        btnOk = findViewById(R.id.btnOkSetting);
        edTemperatureMax = findViewById(R.id.edTemperatureMax);
        edTemperatureMin = findViewById(R.id.edTemperatureMin);
        edHumidityMax = findViewById(R.id.edHumidityMax);
        edHumidityMin = findViewById(R.id.edHumidityMin);
        imgBack = findViewById(R.id.imgViewBackSetting);

    }

    @Override
    public void initVariable() {
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        temperatureMax = sharedPreferences.getInt(HomeConfig.TEMPERATURE_MAX, 0);
        temperatureMin = sharedPreferences.getInt(HomeConfig.TEMPERATURE_MIN, 0);
        humidityMax = sharedPreferences.getInt(HomeConfig.HUMIDITY_MAX, 0);
        humidityMin = sharedPreferences.getInt(HomeConfig.HUMIDITY_MIN, 0);

        setupdata();

        btnOk.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setupdata(){
        if (humidityMin != 0) {
            edHumidityMin.setText(String.valueOf(humidityMin));
        }else{
            edHumidityMin.setText(String.valueOf(HomeConfig.HUM_MIN));
        }
        if (humidityMax != 0) {
            edHumidityMax.setText(String.valueOf(humidityMax));
        }else{
            edHumidityMax.setText(String.valueOf(HomeConfig.HUM_MAX));
        }
        if (temperatureMax != 0) {
            edTemperatureMax.setText(String.valueOf(temperatureMax));
        }else{
            edTemperatureMax.setText(String.valueOf(HomeConfig.TEMP_MAX));
        }
        if (temperatureMin != 0) {
            edTemperatureMin.setText(String.valueOf(temperatureMin));
        }else{
            edTemperatureMin.setText(String.valueOf(HomeConfig.TEMP_MIN));
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
                int temMin = Integer.parseInt(edTemperatureMin.getText().toString().trim());
                int humMax = Integer.parseInt(edHumidityMax.getText().toString().trim());
                int humMin = Integer.parseInt(edHumidityMin.getText().toString().trim());
                commitData(temMax, temMin, humMax, humMin);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } break;
            case R.id.imgViewBackSetting : {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } break;
        }
    }
    private void commitData(int temMax, int temMin, int humMax, int humMin){
        sharedPreferences.edit().putInt(HomeConfig.TEMPERATURE_MAX, temMax).commit();
        sharedPreferences.edit().putInt(HomeConfig.TEMPERATURE_MIN, temMin).commit();
        sharedPreferences.edit().putInt(HomeConfig.HUMIDITY_MAX, humMax).commit();
        sharedPreferences.edit().putInt(HomeConfig.HUMIDITY_MIN, humMin).commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
