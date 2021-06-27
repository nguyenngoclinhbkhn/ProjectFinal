package com.bkhn.doantotnghiep.uis.activities.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkhn.doantotnghiep.R;
import com.bkhn.doantotnghiep.adapter.AdapterRoom;
import com.bkhn.doantotnghiep.model.Room;
import com.bkhn.doantotnghiep.service.WarningService;
import com.bkhn.doantotnghiep.uis.BaseActivity;
import com.bkhn.doantotnghiep.uis.activities.LivingRoomActivity;
import com.bkhn.doantotnghiep.uis.activities.login.LoginActivity;
import com.bkhn.doantotnghiep.uis.activities.setting.SettingActivity;
import com.bkhn.doantotnghiep.utils.HomeConfig;
import com.bkhn.doantotnghiep.utils.KindRoom;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements AdapterRoom.OnRoomListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private ImageView imgMenu;
    private RecyclerView recyclerView;
    private AdapterRoom adapterRoom;

    @Override
    public int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        drawerLayout = findViewById(R.id.drawerLayoutHome);
        toolbar = findViewById(R.id.toolbarHome);
        navigationView = findViewById(R.id.navigationView);
        imgMenu = findViewById(R.id.imgViewMenu);
        recyclerView = findViewById(R.id.recyclerViewRoom);
    }

    @Override
    public void initVariable() {
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        Intent intent = new Intent(this, WarningService.class);
        startService(intent);
        setupRecyclerView();

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemSetting: {
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        finish();
                    }
                    break;
//                    case R.id.itemEditAccount: {
//                        startActivity(new Intent(HomeActivity.this, EditAccountActivity.class));
//                        finish();
//                    }
//                    break;
                    case R.id.itemMenuLogout: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                        builder.setTitle("Warning");
                        builder.setMessage("Do you want to logout ?");
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(HomeActivity.this, WarningService.class);
                                stopService(intent);
                                sharedPreferences.edit().putInt(HomeConfig.STATE_LOGIN, HomeConfig.LOGIN_FALSE).commit();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                        builder.show();

                    }
                    break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapterRoom = new AdapterRoom(this, this);
        recyclerView.setAdapter(adapterRoom);
        adapterRoom.setList(getList());
    }

    private List<Room> getList() {
        List<Room> list = new ArrayList<>();
        list.add(new Room("Phòng khách", R.drawable.sofa, KindRoom.LIVINGROOM));
        return list;
    }

    @Override
    public void onRoomClicked(Room room) {
        switch (room.getKindRoom()) {
            case LIVINGROOM: {
                startActivity(new Intent(HomeActivity.this, LivingRoomActivity.class));
                finish();
            }
            break;
            default: {

            }
            break;
        }
    }
}
