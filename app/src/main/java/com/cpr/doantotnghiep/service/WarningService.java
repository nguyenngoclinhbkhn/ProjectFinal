package com.cpr.doantotnghiep.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.cpr.doantotnghiep.model.Air;
import com.cpr.doantotnghiep.uis.activities.LivingRoomActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class WarningService extends Service {
    private DatabaseReference reference;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private int temperatureMax;
    private int temperatureMin;
    private int humidityMax;
    private int humidityMin;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        reference = FirebaseDatabase.getInstance().getReference("air");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, "channelId");
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupData();
        builder.setSmallIcon(android.R.drawable.stat_sys_warning);
        builder.setContentTitle("Cảnh báo nguy hiểm");
        builder.setContentText("Nhiệt độ vượt quá giới hạn");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setAutoCancel(true);
        builder.setVisibility(VISIBILITY_PUBLIC);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Air air = dataSnapshot.getValue(Air.class);
                if ((air.getTemperature() > temperatureMax) || (air.getTemperature() < temperatureMin) ||
                        (air.getHumidity() > humidityMax) || (air.getHumidity() < humidityMin)){
                    startForeground(1357, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent intentNotification = new Intent(this, LivingRoomActivity.class);
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0,
                intentNotification,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        manager.cancel(1357);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupData(){
        temperatureMax = sharedPreferences.getInt(HomeConfig.TEMPERATURE_MAX, HomeConfig.TEMP_MAX);
        temperatureMin = sharedPreferences.getInt(HomeConfig.TEMPERATURE_MIN, HomeConfig.TEMP_MIN);
        humidityMax = sharedPreferences.getInt(HomeConfig.HUMIDITY_MAX, HomeConfig.HUM_MAX);
        humidityMin = sharedPreferences.getInt(HomeConfig.HUMIDITY_MIN, HomeConfig.HUM_MIN);
    }
}
