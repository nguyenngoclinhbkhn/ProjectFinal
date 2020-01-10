package com.cpr.doantotnghiep.uis.activities.edit_account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;
import com.cpr.doantotnghiep.presenter.edit_account.PresenterEditAccount;
import com.cpr.doantotnghiep.uis.BaseActivity;
import com.cpr.doantotnghiep.uis.activities.home.HomeActivity;
import com.cpr.doantotnghiep.utils.HomeConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class EditAccountActivity extends BaseActivity implements EditAccountView {
    private EditText edUser;
    private EditText edPass;
    private TextView txtOk;
    private NumberPicker numberPicker;
    private ImageView imgViewAccount;
    private PresenterEditAccount presenterEditAccount;
    private DatabaseReference firebaseDatabase;
    private SharedPreferences sharedPreferences;
    private String idUser;

    @Override
    public int initLayout() {
        return R.layout.activity_edit_account;
    }

    @Override
    public void initView() {
        edUser = findViewById(R.id.edEditUserNameAccount);
        edPass = findViewById(R.id.edEditPasswordAccount);
        numberPicker = findViewById(R.id.pickerEditPermissionAccount);
        txtOk = findViewById(R.id.txtEditNewUserAccount);
    }

    @Override
    public void initVariable() {
        numberPicker.setEnabled(false);
        edUser.setEnabled(false);
        presenterEditAccount = new PresenterEditAccount(this);
        sharedPreferences = getSharedPreferences(HomeConfig.LOGIN, Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString(HomeConfig.ID_USER, "");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("admin");
        numberPicker.setMaxValue(3);
        numberPicker.setMinValue(0);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterEditAccount.onAccountEdit(edPass.getText().toString().trim());

            }
        });

        firebaseDatabase.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                edPass.setText(user.getPass());
                edUser.setText(user.getUserName());
                numberPicker.setValue(user.getPermission());
                edPass.setSelection(user.getPass().length());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void editSuccess(String pass) {
        firebaseDatabase.child(idUser).child("pass").setValue(pass);
        Toasty.success(this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void editFail() {
        Toasty.error(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editPassFail() {
        Toasty.error(this, "Mật khẩu phải có độ dài hơn 4 kí tự", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
