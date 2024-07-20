package com.example.test.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.ChatActivity;
import com.example.test.CommentFragment;
import com.example.test.R;
import com.example.test.mainScreenActivity2;
import com.example.test.otherProfileFragment;

import java.util.List;

public class MessageUserAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;


    public MessageUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
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
        UserViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.massage_list_user, parent, false);
            holder = new UserViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UserViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        holder.nameText.setText(user.getName());

        if (user.getProfileImage() != null) {
            holder.profileImage.setImageDrawable(user.getProfileImage().getDrawable());
        } else {
            holder.profileImage.setImageResource(R.drawable.baseline_person_24); // Default image
        }
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId", user.getId());
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    private static class UserViewHolder {
        public ImageButton profileImage;
        public TextView nameText;

        public UserViewHolder(View itemView) {
            profileImage = itemView.findViewById(R.id.profile_image);
            nameText = itemView.findViewById(R.id.nameText);
        }


    }

}