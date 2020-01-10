package com.cpr.doantotnghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterPeople  extends RecyclerView.Adapter<AdapterPeople.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<User> list;
    public AdapterPeople(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setList(List<User> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.txtPermission.setText(String.valueOf(user.getPermission()));
        holder.txtUserName.setText(user.getUserName());
        holder.txtPass.setText(user.getPass());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserName;
        private TextView txtPass;
        private TextView txtPermission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtItemUserName);
            txtPass = itemView.findViewById(R.id.txtItemPass);
            txtPermission = itemView.findViewById(R.id.txtItemPermission);
        }
    }
}
