package com.bkhn.doantotnghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bkhn.doantotnghiep.R;
import com.bkhn.doantotnghiep.model.IDevice;
import com.bkhn.doantotnghiep.model.TypeDevice;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterDeviceLivingRoom extends RecyclerView.Adapter<AdapterDeviceLivingRoom.DeviceHolder> {
    private Context context;
    private ArrayList<IDevice> list;
    private LayoutInflater layoutInflater;
    private DeviceListener deviceListener;

    public AdapterDeviceLivingRoom(Context context, DeviceListener deviceListener) {
        this.context = context;
        this.deviceListener = deviceListener;
        list = new ArrayList<>();
    }


    public void setList(ArrayList<IDevice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        return new DeviceHolder(layoutInflater.inflate(R.layout.item_device_living_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceHolder holder, int position) {
        final IDevice device = list.get(position);
        holder.progressBar.setVisibility(View.GONE);
        holder.switchControlDevice.setVisibility(View.GONE);

        if (device.isDeviceOn() && device.getTypeDevice() == TypeDevice.FANS) {
            Glide.with(context).load(device.getResourceDevice()).into(holder.imgDevice);
        } else {
            holder.imgDevice.setImageResource(device.getResourceDevice());
        }
        holder.switchControlDevice.setChecked(device.isDeviceOn());
        holder.switchControlDevice.setVisibility(View.VISIBLE);
        holder.txtDevice.setText(device.getKeyDevice());
        holder.switchControlDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device.setStatusDevice();
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.switchControlDevice.setVisibility(View.GONE);
                deviceListener.onDeviceControlClicked(device);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder {
        ImageView imgDevice;
        SwitchCompat switchControlDevice;
        ProgressBar progressBar;
        TextView txtDevice;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);
            imgDevice = itemView.findViewById(R.id.imgDevice);
            switchControlDevice = itemView.findViewById(R.id.controlDevice);
            progressBar = itemView.findViewById(R.id.progressControlDevice);
            txtDevice = itemView.findViewById(R.id.txtNameDevice);
        }
    }

    public interface DeviceListener {
        void onDeviceControlClicked(IDevice device);
    }
}

