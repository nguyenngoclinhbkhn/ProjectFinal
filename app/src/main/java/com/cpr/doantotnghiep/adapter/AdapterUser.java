package com.cpr.doantotnghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cpr.doantotnghiep.R;
import com.cpr.doantotnghiep.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserHolder> {
    private List<User> userList;
    private Context context;
    private OnItemUserListener listener;
    private LayoutInflater inflater;
    public AdapterUser(Context context, OnItemUserListener listener){
        this.context = context;
        this.listener = listener;
        userList = new ArrayList<>();
    }

    public void setUserList(List<User> list){
        this.userList = list;
        notifyDataSetChanged();
    }
    public User getUserAtPosition(int position){
        return userList.get(position);
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        User user = userList.get(position);
        holder.txtPermission.setText(String.valueOf(user.getPermission()));
        holder.txtUserName.setText(user.getUserName());
        holder.txtPass.setText(user.getPass());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemUserClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView txtUserName;
        private TextView txtPass;
        private TextView txtPermission;
        private LinearLayout linearLayout;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewItemUser);
            txtUserName = itemView.findViewById(R.id.txtItemUserName);
            txtPass = itemView.findViewById(R.id.txtItemPass);
            txtPermission = itemView.findViewById(R.id.txtItemPermission);
            linearLayout = itemView.findViewById(R.id.linearItemUser);
        }
    }

    public interface OnItemUserListener{
        void onItemUserClicked(int position);
    }
}
