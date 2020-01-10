package com.cpr.doantotnghiep.uis.activities.people;

import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.adapter.AdapterPeople;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityPeople extends BaseActivity {
    private ImageView imgBack;
    private AdapterPeople adapterPeople;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DatabaseReference databaseReference;

    @Override
    public int initLayout() {
        return R.layout.activity_people;
    }

    @Override
    public void initView() {
        imgBack = findViewById(R.id.imgBackPeople);
        recyclerView = findViewById(R.id.recyclerViewPeople);
        toolbar = findViewById(R.id.toolbarPeople);

    }

    @Override
    public void initVariable() {
        setSupportActionBar(toolbar);
        setupRecyclerView();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin");
    }
    private void setupRecyclerView(){
        adapterPeople = new AdapterPeople(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapterPeople);
    }
}
