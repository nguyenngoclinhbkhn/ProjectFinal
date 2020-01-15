package com.cpr.doantotnghiep.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.Room;
import com.cpr.doantotnghiep.utils.KindRoom;

import java.util.ArrayList;
import java.util.List;

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.RoomHolder> {
    private Context context;
    private List<Room> list;
    private LayoutInflater layoutInflater;
    private OnRoomListener listener;

    public AdapterRoom(Context context, OnRoomListener listener) {
        this.context = context;
        this.list = new ArrayList<>();
        this.listener = listener;
    }


    public void setList(List<Room> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_room, parent, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        final Room room = list.get(position);
        Glide.with(context).load(room.getImage()).into(holder.img);
        holder.txt.setText(room.getRoom());
        Log.e("TAG", "isLock " + room.isLock());
        if (room.isLock() == false){
            holder.imgLock.setVisibility(View.GONE);
        }else{
            holder.imgLock.setVisibility(View.VISIBLE);
        }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRoomClicked(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RoomHolder extends RecyclerView.ViewHolder {
        private LinearLayout linear;
        private ImageView img;
        private TextView txt;
        private ImageView imgLock;
        public RoomHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linearItemRoom);
            img = itemView.findViewById(R.id.imgRoom);
            txt = itemView.findViewById(R.id.txtRoom);
            imgLock = itemView.findViewById(R.id.imgLock);
        }
    }

    public interface OnRoomListener{
        void onRoomClicked(Room room);
    }
}
