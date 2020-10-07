package com.example.serafellochat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.serafellochat.R;
import com.example.serafellochat.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private List<Messages> messages;
    public String imageurl;

    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Messages> messages, String imageURL) {
        this.mContext = mContext;
        this.messages = messages;
        this.imageurl = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Messages message=messages.get(position);
        holder.showMessage.setText(message.getMessage());

        if(imageurl.equals("default")){
            holder.profilePicture.setImageResource(R.drawable.profile_picture);
        }else{
            Glide.with(mContext).load(imageurl).into(holder.profilePicture);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView showMessage;
        public ImageView profilePicture;

        public ViewHolder(View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.show_message);
            profilePicture = itemView.findViewById(R.id.profile_picture);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getReceiver().equals(firebaseUser.getUid())) {
            return MSG_TYPE_LEFT;
        }
        else{
            return MSG_TYPE_RIGHT;
        }
    }
}
