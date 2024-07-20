package com.example.test.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;

import java.util.List;

public class RequestAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private LayoutInflater inflater;

    public RequestAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.request_item_list, parent, false);
        }

        ImageView profileImage = convertView.findViewById(R.id.profile_image);
        TextView nameText = convertView.findViewById(R.id.nameText);
        Button acceptButton = convertView.findViewById(R.id.accept_button);
        Button rejectButton = convertView.findViewById(R.id.reject_button);

        User user = userList.get(position);

        // Set onClickListeners for accept and reject buttons
        acceptButton.setOnClickListener(v -> {
            // Handle accept action
        });

        rejectButton.setOnClickListener(v -> {
            // Handle reject action
        });

        return convertView;
    }
}
