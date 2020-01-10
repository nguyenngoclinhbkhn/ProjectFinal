package com.cpr.doantotnghiep.uis.activities.manager_user;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.adapter.AdapterUser;
import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.LivingRoomActivity;
import com.cpr.doantotnghiep.uis.activities.edit_user.EditUserActivity;
import com.cpr.doantotnghiep.uis.activities.home.HomeActivity;
import com.cpr.doantotnghiep.uis.activities.register.RegisterActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerUserActivity extends BaseActivity implements AdapterUser.OnItemUserListener, View.OnClickListener {
    private ImageView imgAdd;
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private AdapterUser adapterUser;
    private DatabaseReference reference;
    private List<User> list;
    @Override
    public int initLayout() {
        return R.layout.activity_manager_user;
    }

    @Override
    public void initView() {
        imgAdd = findViewById(R.id.imgViewAdd);
        imgBack = findViewById(R.id.imgViewBack);
        recyclerView = findViewById(R.id.recyclerViewUser);
        toolbar = findViewById(R.id.toolbarManagerUser);

    }

    @Override
    public void initVariable() {
        setupRecyclerView();
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("admin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    list.add(dataSnapshot1.getValue(User.class));
                }

                adapterUser.setUserList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgBack.setOnClickListener(this);
        imgAdd.setOnClickListener(this);

    }
    private void setupRecyclerView(){
        adapterUser = new AdapterUser(this, this);
        recyclerView.setAdapter(adapterUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapterUser.notifyDataSetChanged();
    }

    @Override
    public void onItemUserClicked(final int position) {
        final User user = adapterUser.getUserAtPosition(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManagerUserActivity.this, EditUserActivity.class);
                intent.putExtra(HomeConfig.KEY, user.getId());
                startActivity(intent);
                finish();
            }
        }, 150);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewAdd : {
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
            } break;
            case R.id.imgViewBack : {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
