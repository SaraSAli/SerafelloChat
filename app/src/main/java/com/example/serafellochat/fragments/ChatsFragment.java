package com.example.serafellochat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serafellochat.R;
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
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Users> mUsers;
    private UserAdapter userAdapter;

    FirebaseUser muser;
    DatabaseReference reference;

    List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        muser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Messages chat = dataSnapshot.getValue(Messages.class);

                    assert chat != null;
                    if (chat.getSender().equals(muser.getUid()))
                        usersList.add(chat.getReceiver());

                    if (chat.getReceiver().equals(muser.getUid()))
                        usersList.add(chat.getSender());
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot dataSnapShot : snapshot.getChildren()) {
                    Users user = dataSnapShot.getValue(Users.class);

                    for (String id : usersList) {
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
                                int flag = 0;
                                for (Users u : mUsers) {
                                    if (user.getId().equals(u.getId())) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 0)
                                    mUsers.add(user);
                            } else {

                                mUsers.add(user);
                            }
                        }

                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}