package com.cpr.doantotnghiep.uis.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.Curtains;
import com.cpr.doantotnghiep.model.Stairs;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogSetup extends DialogFragment implements View.OnClickListener {
    private Button btnCancel;
    private Button btnOk;
    private EditText edTime;
    private Switch aSwitchLamp;
    private Switch aSwitchCurtain;
    private int kind;
    private LinearLayout linearCurtain;
    private LinearLayout linearLamp;
    private DatabaseReference databaseReference;
    private Button btnCancelLamp;
    private Button btnOkLamp;


    public static DialogSetup getInstance(int kind) {
        DialogSetup dialogSetup = new DialogSetup();
        Bundle bundle = new Bundle();
        bundle.putInt("Kind", kind);
        dialogSetup.setArguments(bundle);
        return dialogSetup;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_setup, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearCurtain = view.findViewById(R.id.linearCurtains);
        linearLamp = view.findViewById(R.id.linearLamp);
        btnCancelLamp = view.findViewById(R.id.btnCancelLamp);
        btnOkLamp = view.findViewById(R.id.btnOkLamp);
        aSwitchCurtain = view.findViewById(R.id.switchCurtains);
        aSwitchLamp = view.findViewById(R.id.switchLamp);
        edTime = view.findViewById(R.id.edTimeCurtain);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);

        Bundle bundle = getArguments();
        kind = bundle.getInt("Kind");
        if (kind == HomeConfig.KIND_CURTAIN ){
            linearCurtain.setVisibility(View.VISIBLE);
            linearLamp.setVisibility(View.GONE);
            databaseReference = FirebaseDatabase.getInstance().getReference("Curtains");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Curtains curtains = dataSnapshot.getValue(Curtains.class);
                    edTime.setText(String.valueOf(curtains.getTime()));
                    edTime.setSelection(String.valueOf(curtains.getTime()).trim().length());
                    if (curtains.getAutomatic() == 0){
                        aSwitchCurtain.setChecked(false);
                    }else{
                        aSwitchCurtain.setChecked(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            linearCurtain.setVisibility(View.GONE);
            linearLamp.setVisibility(View.VISIBLE);
            databaseReference = FirebaseDatabase.getInstance().getReference("lamp");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Stairs stairs = dataSnapshot.getValue(Stairs.class);
                    if (stairs.getAutomatic() == 0){
                        aSwitchLamp.setChecked(false);
                    }else{
                        aSwitchLamp.setChecked(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancelLamp.setOnClickListener(this);
        btnOkLamp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancel : {
                dismiss();
            }break;
            case R.id.btnOk : {
                if (kind == HomeConfig.KIND_CURTAIN){
                    int time = Integer.parseInt(edTime.getText().toString().trim());
                    databaseReference.child("time").setValue(time);
                    if (aSwitchCurtain.isChecked()){
                        databaseReference.child("automatic").setValue(1);
                    }else{
                        databaseReference.child("automatic").setValue(0);
                    }
                }else{

                }
                dismiss();
            }break;
            case R.id.btnOkLamp : {
                if (aSwitchLamp.isChecked()){
                    databaseReference.child("automatic").setValue(1);
                }else{
                    databaseReference.child("automatic").setValue(0);
                }
                dismiss();
            }break;
            case R.id.btnCancelLamp : {
                dismiss();
            }break;
        }
    }
}
