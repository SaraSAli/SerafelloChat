package com.example.serafellochat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.serafellochat.model.Messages;
import com.example.serafellochat.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<Users> users;
    private boolean isChat;
    String theLastMessage;

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

        if (isChat) {
            lastMessage(user.getId(), holder.lastMessage);
        } else {
            holder.lastMessage.setVisibility(View.GONE);
        }

        if (isChat) {
            System.out.println("Info: " + user.getStatus());
            if ((user.getStatus() != null) && (user.getStatus().equals("online"))) {
                holder.imageOn.setVisibility(View.VISIBLE);
            } else {
                holder.imageOn.setVisibility(View.GONE);
            }
        } else {
            holder.imageOn.setVisibility(View.GONE);
        }

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
        private TextView lastMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profilePicture = itemView.findViewById(R.id.profile_picture);

            imageOn = itemView.findViewById(R.id.img_on);
            lastMessage = itemView.findViewById(R.id.last_message);
        }
    }

    private void lastMessage(final String userID, final TextView lastMsg) {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Messages message = dataSnapshot.getValue(Messages.class);
                    assert message != null;
                    if (message.getReceiver().equals(firebaseUser.getUid()) && message.getSender().equals(userID)) {
                        if (message.getType().equals("image"))
                            theLastMessage = "Sent a photo...";
                        else
                            theLastMessage = message.getMessage();
                        if (!message.isIsseen())
                            lastMsg.setTypeface(null, Typeface.BOLD);
                        else
                            lastMsg.setTypeface(null, Typeface.NORMAL);
                    } else if (message.getReceiver().equals(userID) && message.getSender().equals(firebaseUser.getUid())) {
                        if ("image".equals(message.getType()))
                            theLastMessage = "Sent a photo...";
                        else
                            theLastMessage = message.getMessage();
                        lastMsg.setTypeface(null, Typeface.NORMAL);
                    }

                }
                if (theLastMessage.equals("default"))
                    lastMsg.setText("No message");
                else
                    lastMsg.setText(theLastMessage);
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
