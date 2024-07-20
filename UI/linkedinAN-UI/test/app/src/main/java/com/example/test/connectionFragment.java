package com.example.test;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.test.Models.ConnectionAdapter;
import com.example.test.Models.User;

import java.util.ArrayList;
import java.util.List;

public class connectionFragment extends Fragment {

    private ListView listView;
    private ConnectionAdapter connectionAdapter;
    private Button requestButton;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connections, container, false);

        // Initialize the views
        listView = view.findViewById(R.id.list_view);
        requestButton = view.findViewById(R.id.requests);

        // Initialize user list and adapter
        userList = new ArrayList<>();
        // Add some sample users to the list
        userList.add(new User("1", null, "user1@example.com", "User One"));
        userList.add(new User("2", null, "user2@example.com", "User Two"));
        userList.add(new User("3", null, "user3@example.com", "User Three"));

        connectionAdapter = new ConnectionAdapter(getContext(), userList);
        listView.setAdapter(connectionAdapter);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainScreenActivity2.changeFragment(new RequestFragment());

            }});

        return view;
    }
}
