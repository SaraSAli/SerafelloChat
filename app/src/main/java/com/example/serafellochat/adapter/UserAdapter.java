package com.example.serafellochat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.serafellochat.MessageActivity;
import com.example.serafellochat.R;
import com.example.serafellochat.model.Users;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<Users> users;
    private boolean isChat;

    public UserAdapter(Context mContext, List<Users> users, boolean isChat) {
        this.mContext = mContext;
        this.users = users;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Users user = users.get(position);
        holder.username.setText(user.getUsername());

        if (user.getImage().equals("default")) {
            holder.profilePicture.setImageResource(R.drawable.profile_picture);
        } else {
            Glide.with(mContext).load(user.getImage()).into(holder.profilePicture);
        }

        /*if (isChat) {
            if (user.getStatus().equals("online")) {
                holder.imageOn.setVisibility(View.VISIBLE);
            } else {
                holder.imageOn.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.imageOn.setVisibility(View.INVISIBLE);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profilePicture;
        private ImageView imageOn;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profilePicture = itemView.findViewById(R.id.profile_picture);

            imageOn = itemView.findViewById(R.id.img_on);
        }
    }
}
