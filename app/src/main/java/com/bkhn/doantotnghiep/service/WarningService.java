package com.bkhn.doantotnghiep.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bkhn.doantotnghiep.uis.activities.LivingRoomActivity;
import com.bkhn.doantotnghiep.utils.HomeConfig;
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
    private int gas;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        reference = FirebaseDatabase.getInstance().getReference("DATN").child("GAS");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, "channelId");
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupData();
        builder.setSmallIcon(android.R.drawable.stat_sys_warning);
        builder.setContentTitle("Cảnh báo nguy hiểm");
        builder.setContentText("Khí gas vượt quá mức cho phép ");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setAutoCancel(true);
        builder.setVisibility(VISIBILITY_PUBLIC);

        final NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("channelId", "BKHN", NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int gasCurrent = dataSnapshot.getValue(Integer.class);
                if (gasCurrent > gas) {
                    manager.notify(1357, builder.build());
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
        gas = sharedPreferences.getInt(HomeConfig.GAS, HomeConfig.GAS_MAX);
    }
}
