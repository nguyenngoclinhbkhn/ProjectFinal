package com.bkhn.doantotnghiep.uis.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bkhn.doantotnghiep.R;
import com.bkhn.doantotnghiep.adapter.AdapterDeviceLivingRoom;
import com.bkhn.doantotnghiep.model.DeviceInHome;
import com.bkhn.doantotnghiep.model.IDevice;
import com.bkhn.doantotnghiep.model.TypeDevice;
import com.bkhn.doantotnghiep.service.WarningService;
import com.bkhn.doantotnghiep.uis.BaseActivity;
import com.bkhn.doantotnghiep.uis.activities.home.HomeActivity;
import com.bkhn.doantotnghiep.uis.fragment.DialogInformation;
import com.bkhn.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class LivingRoomActivity extends BaseActivity implements AdapterDeviceLivingRoom.DeviceListener, View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgBack;
    private ImageView imgVoice;
    private TextView txtTemperature;
    private TextView txtGas;
    private TextView txtHumidity;
    private DatabaseReference referenceDevice;
    private Handler handler;
    private DeviceInHome deviceInHome;
    private RecyclerView rcvDevice;
    private AdapterDeviceLivingRoom adapterDeviceLivingRoom;
    private ProgressBar progressBar;


    @Override
    public int initLayout() {
        return R.layout.activity_living_room;
    }


    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbarLivingRoom);
        imgBack = findViewById(R.id.imgViewBackLivingRoom);
        txtTemperature = findViewById(R.id.txtTemperatureLivingRoom);
        txtHumidity = findViewById(R.id.txtHumidityLivingRoom);
        txtGas = findViewById(R.id.txtGas);
        rcvDevice = findViewById(R.id.rcvDevice);
        imgVoice = findViewById(R.id.imgViewMicrophoneLivingRoom);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void initVariable() {
        setSupportActionBar(toolbar);
        handler = new Handler(Looper.myLooper());
        setupDatabase();
        setupListDevice();
        referenceDevice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                rcvDevice.setVisibility(View.VISIBLE);
                deviceInHome = dataSnapshot.getValue(DeviceInHome.class);
                txtHumidity.setText(String.valueOf(deviceInHome.getHUMIDITY()) + " %");
                txtTemperature.setText(String.valueOf(deviceInHome.getTEMPERATURE()) + " °C");
                txtGas.setText(String.valueOf(deviceInHome.getGAS()));
                adapterDeviceLivingRoom.setList(DeviceInHome.getListDevice(deviceInHome));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imgBack.setOnClickListener(this);
        imgVoice.setOnClickListener(this);
    }

    public void setupListDevice() {
        adapterDeviceLivingRoom = new AdapterDeviceLivingRoom(this, this);
        rcvDevice.setAdapter(adapterDeviceLivingRoom);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, WarningService.class);
        stopService(intent);
    }

    private void setupDatabase() {
        referenceDevice = FirebaseDatabase.getInstance().getReference("DATN");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    private void startVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, HomeConfig.REQUEST_CODE_SPEED_TO_TEXT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeConfig.REQUEST_CODE_SPEED_TO_TEXT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String text = result.get(0);
                executeSpeed(text);
            }
        }
    }

    private void executeSpeed(String text) {
        if (text.toLowerCase().contains(HomeConfig.TURN_ON_LAMP_1)) {
            referenceDevice.child("LIGHT1").setValue("1");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_OFF_LAMP_1)) {
            referenceDevice.child("LIGHT1").setValue("0");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_ON_LAMP_2) || text.toLowerCase().contains(HomeConfig.TURN_ON_LAMP_2_2)) {
            referenceDevice.child("LIGHT2").setValue("1");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_OFF_LAMP_2) || text.toLowerCase().contains(HomeConfig.TURN_OFF_LAMP_2_2)) {
            referenceDevice.child("LIGHT2").setValue("0");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_ON_LAMP_3) || text.toLowerCase().contains(HomeConfig.TURN_ON_LAMP_3_2)) {
            referenceDevice.child("LIGHT3").setValue("1");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_OFF_LAMP_3) || text.toLowerCase().contains(HomeConfig.TURN_OFF_LAMP_3_2)) {
            referenceDevice.child("LIGHT3").setValue("0");
        } else if (text.toLowerCase().contains(HomeConfig.OPEN_DOOR)) {
            referenceDevice.child("DOOR").setValue("Unlock");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    referenceDevice.child("DOOR").setValue("Lock");
                }
            }, 8000);

        } else if (text.toLowerCase().contains(HomeConfig.CLOSE_DOOR)) {
            referenceDevice.child("DOOR").setValue("Lock");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_ON_FAN)) {
            referenceDevice.child("FAN").setValue("1");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_OFF_FAN)) {
            referenceDevice.child("FAN").setValue("0");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_ON_CURTAIN)) {
            referenceDevice.child("CURTAIN").setValue("1");
        } else if (text.toLowerCase().contains(HomeConfig.TURN_OFF_CURTAIN)) {
            referenceDevice.child("CURTAIN").setValue("2");
        }
    }

    @Override
    public void onDeviceControlClicked(final IDevice device) {
        if (device.getTypeDevice() == TypeDevice.DOOR) {
            referenceDevice.child(device.getKeyDevice()).setValue(device.getStatusDevice());
            if (device.isDeviceOn()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        referenceDevice.child(device.getKeyDevice()).setValue("Lock");
                    }
                }, 8000);
            }
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    referenceDevice.child(device.getKeyDevice()).setValue(device.getStatusDevice());
                }
            }, 500);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgViewMicrophoneLivingRoom: {
                startVoice();
            }
            break;
            case R.id.imgViewBackLivingRoom: {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
            break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemInfor: {
                DialogInformation.getInstance().show(getSupportFragmentManager(), "");
            }
            break;
        }
        return true;
    }
}
