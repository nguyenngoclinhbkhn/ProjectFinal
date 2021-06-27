package com.bkhn.doantotnghiep.uis.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.bkhn.doantotnghiep.R;

public class DialogInformation extends DialogFragment {
    private Button btnOk;
    private CardView cardView;

    public static DialogInformation getInstance(){
        DialogInformation dialogInformation = new DialogInformation();
        return dialogInformation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_information, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnOk = view.findViewById(R.id.btnOkInfor);
        cardView = view.findViewById(R.id.cardViewInfor);


        cardView.post(new Runnable() {
            @Override
            public void run() {
                setupScreen();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setupScreen(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        cardView.getLayoutParams().width = point.x - 50;
//        cardView.getLayoutParams().height = point.y / 2;
        cardView.requestLayout();
    }
}
