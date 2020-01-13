package com.cpr.doantotnghiep.uis.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.Air;
import com.cpr.doantotnghiep.model.Curtains;
import com.cpr.doantotnghiep.model.Door;
import com.cpr.doantotnghiep.model.Stairs;
import com.cpr.doantotnghiep.service.WarningService;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.home.HomeActivity;
import com.cpr.doantotnghiep.uis.fragment.DialogInformation;
import com.cpr.doantotnghiep.uis.fragment.DialogSetup;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

import dmax.dialog.SpotsDialog;

public class LivingRoomActivity extends BaseActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private ImageView imgBack;
    private int permission;
    private ImageView imgViewCeilingFans;
    private ImageView imgControlCeilingFan;
    private ImageView imgControlLamp;
    private ImageView imgCurtains;
    private ImageView imgControlCurtain;
    private ImageView imgMicro;
    private TextView txtTemperature;
    private TextView txtHumidity;
    private DatabaseReference referenceLamp;
    private DatabaseReference referenceAir;
    private DatabaseReference referenceDoor;
    private DatabaseReference referenceCurtain;
    private int stateLamp1;
    private int stateLamp2;
    private int stateDoor;
    private int stateCurtain;
    private int count = 0;
    private boolean isOn;
    private ImageView imgSetupLamp;
    private PopupMenu popupMenu;
    private PopupMenu popupMenuCurtain;
    private int time;

    @Override
    public int initLayout() {
        return R.layout.activity_living_room;
    }


    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbarLivingRoom);
        imgBack = findViewById(R.id.imgViewBackLivingRoom);
        imgViewCeilingFans = findViewById(R.id.imgViewStateCeilingFansLivingRoom);
        imgControlCeilingFan = findViewById(R.id.imgViewControlCeilingFanLivingRoom);
        imgControlCurtain = findViewById(R.id.imgViewControlCurtainLivingRoom);
        imgCurtains = findViewById(R.id.imgViewCurtainLivingRoom);
        imgControlLamp = findViewById(R.id.imgViewLampStairsLivingRoom);
        imgMicro = findViewById(R.id.imgViewMicrophoneLivingRoom);
        txtTemperature = findViewById(R.id.txtTemperatureLivingRoom);
        txtHumidity = findViewById(R.id.txtHumidityLivingRoom);
        imgSetupLamp = findViewById(R.id.setupLamp);
    }


    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initVariable() {
        setSupportActionBar(toolbar);
        isOn = false;
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        setupDatabase();
        permission = sharedPreferences.getInt(HomeConfig.PERMISSION, 0);
        imgControlLamp.setOnClickListener(this);
        imgControlCurtain.setOnClickListener(this);
        imgControlCeilingFan.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgMicro.setOnClickListener(this);
        referenceLamp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Stairs stairs = dataSnapshot.getValue(Stairs.class);
                stateLamp1 = stairs.getState1();
                if (stairs.getState1() == 1) {
                    imgControlLamp.setImageResource(R.drawable.lamp_on);
                } else {
                    imgControlLamp.setImageResource(R.drawable.lamp_off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        referenceCurtain.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Curtains curtains = dataSnapshot.getValue(Curtains.class);
                stateCurtain = curtains.getState();
                time = curtains.getTime();
                if (curtains.getState() >= 1) {
                    setImage(imgCurtains, R.drawable.curtain_open);
                } else {
                    setImage(imgCurtains, R.drawable.curtain_close);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        referenceDoor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Door fan = dataSnapshot.getValue(Door.class);
                stateDoor = fan.getState();
                if (fan.getState() == 1) {
                    setImage(imgControlCeilingFan, R.drawable.off);
                    isOn = true;
                    setImage(imgViewCeilingFans, R.drawable.open_door);
                } else {
                    setImage(imgControlCeilingFan, R.drawable.on);
                    setImage(imgViewCeilingFans, R.drawable.closed_door);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        referenceAir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Air air = dataSnapshot.getValue(Air.class);
                txtHumidity.setText(String.valueOf(air.getHumidity()) + " %");
                txtTemperature.setText(String.valueOf(air.getTemperature()) + " \u2103");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imgSetupLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuSetupLamp();
            }
        });
        imgCurtains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuCurtains();
            }
        });
    }


    private void showMenuSetupLamp() {
        popupMenu = new PopupMenu(this, imgSetupLamp);
        popupMenu.getMenuInflater().inflate(R.menu.menu_setup_lamp, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    private void showMenuCurtains() {
        popupMenuCurtain = new PopupMenu(this, imgCurtains);
        popupMenuCurtain.getMenuInflater().inflate(R.menu.menu_setup_curtain, popupMenuCurtain.getMenu());
        popupMenuCurtain.setOnMenuItemClickListener(this);
        popupMenuCurtain.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, WarningService.class);
        stopService(intent);
    }

    private void setImage(ImageView img, int resource) {
        Glide.with(getApplicationContext()).load(resource).into(img);
    }

    private void setupDatabase() {
        referenceLamp = FirebaseDatabase.getInstance().getReference("lamp");
        referenceAir = FirebaseDatabase.getInstance().getReference("air");
        referenceDoor = FirebaseDatabase.getInstance().getReference("door");
        referenceCurtain = FirebaseDatabase.getInstance().getReference("Curtains");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    private void startVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, HomeConfig.REQUEST_CODE_SPEED_TO_TEXT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isOn = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewControlCeilingFanLivingRoom: {
                // dieu khien qua tran
                if (stateDoor == 1) {
                    referenceDoor.child("state").setValue(0);
                } else {
                    referenceDoor.child("state").setValue(1);
                }
                referenceDoor.child("automatic").setValue(0);
            }
            break;
            case R.id.imgViewControlCurtainLivingRoom: {
                // dieu khien rem cua
                if (stateCurtain >= 1) {
                    referenceCurtain.child("state").setValue(0);
                    referenceCurtain.child("stop").setValue(1);
                } else {
                    referenceCurtain.child("state").setValue(1);
                    referenceCurtain.child("stop").setValue(1);
                }
                referenceCurtain.child("automatic").setValue(0);
                new SetValueCurtainAsync().execute(time);
            }
            break;
            case R.id.imgViewLampStairsLivingRoom: {
                // dieu khien den cau thang
                if (stateLamp1 == 1) {
                    referenceLamp.child("state1").setValue(0);
                } else {
                    referenceLamp.child("state1").setValue(1);
                }
                referenceLamp.child("automatic").setValue(0);
            }
            break;
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
        switch (text) {
            case HomeConfig.TURN_ON_LAMP: {
                referenceLamp.child("state1").setValue(1);
                referenceLamp.child("automatic").setValue(0);
            }
            break;
            case HomeConfig.TURN_OFF_LAMP: {
                referenceLamp.child("state1").setValue(0);
                referenceLamp.child("automatic").setValue(0);
            }
            break;
            case HomeConfig.OPEN_DOOR: {
                referenceDoor.child("state").setValue(1);
                referenceDoor.child("automatic").setValue(0);
            }
            break;
            case HomeConfig.CLOSE_DOOR: {
                referenceDoor.child("state").setValue(0);
                referenceDoor.child("automatic").setValue(0);

            }
            break;
            case HomeConfig.TURN_ON_CURTAIN: {
                referenceCurtain.child("state").setValue(1);
                referenceCurtain.child("stop").setValue(1);
                referenceCurtain.child("automatic").setValue(0);
                new SetValueCurtainAsync().execute(time);

            }
            break;
            case HomeConfig.TURN_OFF_CURTAIN: {
                referenceCurtain.child("state").setValue(0);
                referenceCurtain.child("stop").setValue(1);
                referenceCurtain.child("automatic").setValue(0);
                new SetValueCurtainAsync().execute(time);
            }
            break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.itemSetup: {
                // todo lamp
                DialogSetup dialogSetup = DialogSetup.getInstance(HomeConfig.KIND_LAMP);
                dialogSetup.show(getSupportFragmentManager(), "");
            }
            break;
            case R.id.itemSetupCurtain: {
                // todo curtains
                DialogSetup dialogSetup = DialogSetup.getInstance(HomeConfig.KIND_CURTAIN);
                dialogSetup.show(getSupportFragmentManager(), "");
            }
            break;
        }
        return false;
    }

    private class SetValueCurtainAsync extends AsyncTask<Integer, Void, Void> {
        private SpotsDialog spotsDialog;


        @Override
        protected void onPreExecute() {
            spotsDialog = new SpotsDialog(LivingRoomActivity.this, R.style.Wait);
            spotsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            spotsDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int value = integers[0];
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            spotsDialog.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemInfor : {
                DialogInformation.getInstance().show(getSupportFragmentManager(), "");
            }break;
        }
        return true;
    }
}
