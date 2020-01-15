package com.cpr.doantotnghiep.uis.activities.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.adapter.AdapterRoom;
import com.cpr.doantotnghiep.model.Room;
import com.cpr.doantotnghiep.service.WarningService;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.LivingRoomActivity;
import com.cpr.doantotnghiep.uis.activities.edit_account.EditAccountActivity;
import com.cpr.doantotnghiep.uis.activities.login.LoginActivity;
import com.cpr.doantotnghiep.uis.activities.manager_user.ManagerUserActivity;
import com.cpr.doantotnghiep.uis.activities.setting.SettingActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.cpr.doantotnghiep.utils.KindRoom;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends BaseActivity implements AdapterRoom.OnRoomListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private ImageView imgMenu;
    private int permission;
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
        permission = sharedPreferences.getInt(HomeConfig.PERMISSION, 0);
        Intent intent = new Intent(this, WarningService.class);
        startService(intent);
        setupRecyclerView();

        Log.e("TAG", "permission" + permission);
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
                    case R.id.itemMenuManagerUser: {
                        if (permission == 3) {
                            startActivity(new Intent(HomeActivity.this, ManagerUserActivity.class));
                            finish();
                        } else {
                            Toast.makeText(HomeActivity.this, "You can not access", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case R.id.itemSetting: {
                        if (permission == 3) {
                            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                            finish();
                        } else {
                            Toast.makeText(HomeActivity.this, "You can not access", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case R.id.itemEditAccount: {
                        startActivity(new Intent(HomeActivity.this, EditAccountActivity.class));
                        finish();
                    }
                    break;
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
        if (permission == 3) {
            list.add(new Room("Phòng khách", R.drawable.sofa, KindRoom.LIVINGROOM, false));
            list.add(new Room("Phòng ngủ", R.drawable.bed, KindRoom.BEDROOM, false));
            list.add(new Room("Phòng tắm", R.drawable.bathtub, KindRoom.BATHROOM, false));
            list.add(new Room("Nhà bếp", R.drawable.kitchen, KindRoom.KITCHEN, false));
        } else {
            list.add(new Room("Phòng khách", R.drawable.sofa, KindRoom.LIVINGROOM, false));
            list.add(new Room("Phòng ngủ", R.drawable.bed, KindRoom.BEDROOM, true));
            list.add(new Room("Phòng tắm", R.drawable.bathtub, KindRoom.BATHROOM, true));
            list.add(new Room("Nhà bếp", R.drawable.kitchen, KindRoom.KITCHEN, true));
        }
        return list;
    }

    @Override
    public void onRoomClicked(Room room) {

        switch (room.getKindRoom()) {
            case BATHROOM: {
                if (room.isLock()) {
                    Toasty.error(this, "You can't access this room", Toasty.LENGTH_SHORT).show();
                }else{
                    Toasty.success(this, "See you soon", Toasty.LENGTH_SHORT).show();
                }
            }
            break;
            case LIVINGROOM: {
                Log.e("TAG", "living");

                if (room.isLock()) {

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, LivingRoomActivity.class));
                            finish();
                        }
                    }, 150);
                }

            }
            break;
            case KITCHEN: {
                if (room.isLock()) {
                    Toasty.error(this, "You can't access this room", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.success(this, "See you soon", Toasty.LENGTH_SHORT).show();
                }
            }
            break;
            case BEDROOM: {
                if (room.isLock()) {
                    Toasty.error(this, "You can't access this room", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.success(this, "See you soon", Toasty.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
