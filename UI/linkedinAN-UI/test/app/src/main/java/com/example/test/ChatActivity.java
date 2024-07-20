package com.example.test;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayAdapter<String> adapter;
    private List<String> messageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.listView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Initialize the message list
        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        // Add some dummy messages for testing
        messageList.add("User1: Hello!");
        messageList.add("User2: Hi there!");

        // Notify the adapter of the new data
        adapter.notifyDataSetChanged();

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                // Add message to the list and update the adapter
                messageList.add("Me: " + message);
                adapter.notifyDataSetChanged();
                messageEditText.setText("");
            }
        });
    }
}
