package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.test.Models.MessageUserAdapter;
import com.example.test.Models.User;

import java.util.ArrayList;
import java.util.List;

public class MessageUserListFragment extends Fragment {

    private ListView listView;
    private MessageUserAdapter messageUserAdapter;
    private Button newChatButton;
    private List<User> userList;
    private ImageButton profile_image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_massage_user_list, container, false);

        // Initialize the views
        listView = view.findViewById(R.id.list_view);
        newChatButton = view.findViewById(R.id.NewChatButton);


        // Initialize user list and adapter
        userList = new ArrayList<>();
        // Add some sample users to the list
        userList.add(new User("1", null, "user1@example.com", "User One"));
        userList.add(new User("2", null, "user2@example.com", "User Two"));
        userList.add(new User("3", null, "user3@example.com", "User Three"));

        messageUserAdapter = new MessageUserAdapter(getContext(), userList);
        listView.setAdapter(messageUserAdapter);
        newChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle new chat button click
            }
        });

        return view;
    }
}
