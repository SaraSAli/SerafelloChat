package com.example.serafellochat.adapter;

import android.content.Context;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Messages message = messages.get(position);
        String timeStamp = message.getTime();
        String messageType = message.getType();

//        System.out.println("type " + messageType + " position " + position);

        if ("text".equals(messageType)) {
            holder.showMessage.setVisibility(View.VISIBLE);
            holder.imageMessageView.setVisibility(View.GONE);
            holder.showMessage.setText(message.getMessage());
        } else {
            holder.showMessage.setVisibility(View.GONE);
            holder.imageMessageView.setVisibility(View.VISIBLE);
            Picasso.get().load(message.getMessage()).placeholder(R.drawable.backround_right).into(holder.imageMessageView);
        }

      /*  Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();*/

        if (imageurl.equals("default")) {
            holder.profilePicture.setImageResource(R.drawable.profile_picture);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profilePicture);
        }

        if (position == messages.size() - 1) {
            if (message.isIsseen()) {
                holder.textSeen.setText("Seen");
            } else {
                holder.textSeen.setText("Delivered");
            }
        } else {
            holder.textSeen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView showMessage;
        public ImageView profilePicture, imageMessageView;
        public TextView textSeen;

        public ViewHolder(View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.show_message);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            imageMessageView = itemView.findViewById(R.id.image_message);
            textSeen = itemView.findViewById(R.id.text_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getReceiver().equals(firebaseUser.getUid())) {
            return MSG_TYPE_LEFT;
        } else {
            return MSG_TYPE_RIGHT;
        }
    }
}
