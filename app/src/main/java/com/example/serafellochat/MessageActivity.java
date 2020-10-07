package com.example.serafellochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.serafellochat.adapter.MessageAdapter;
import com.example.serafellochat.adapter.UserAdapter;
import com.example.serafellochat.model.Messages;
import com.example.serafellochat.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profilePicture;
    TextView username;
    ImageButton sendButton;
    EditText textSend;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;
    List<Messages> messages;

    RecyclerView recyclerView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolBar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profilePicture = findViewById(R.id.image);
        username = findViewById(R.id.username);
        textSend = findViewById(R.id.textSend);
        sendButton = findViewById(R.id.sendBtn);

        intent = getIntent();
        final String userID = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert userID != null;
        reference = FirebaseDatabase.getInstance().getReference("users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                username.setText(user.getUsername());
                if (user.getImage().equals("default")) {
                    profilePicture.setImageResource(R.drawable.profile_picture);
                } else {
                    Glide.with(MessageActivity.this).load(user.getImage()).into(profilePicture);
                }

                readMessage(firebaseUser.getUid(),userID,user.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = textSend.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), userID, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                textSend.setText("");
            }
        });
    }

    public void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void readMessage(final String myID, final String userID, final String imageurl) {
        messages = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Messages message = dataSnapshot.getValue(Messages.class);
                    if (message.getReceiver().equals(myID) && message.getSender().equals(userID) ||
                            message.getReceiver().equals(userID) && message.getSender().equals(myID)) {
                        messages.add(message);
                    }
                }
                messageAdapter = new MessageAdapter(MessageActivity.this, messages, imageurl);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("UserState").child(firebaseUser.getUid());

        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("status",status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}