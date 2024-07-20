package com.example.test;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test.utils.GetIp;

import java.util.List;

public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Post> postList;
    private LayoutInflater inflater;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_post, parent, false);
        }

        // Initialize your views
        ImageView postProfileImage = convertView.findViewById(R.id.postProfileImage);
        TextView postUserName = convertView.findViewById(R.id.postUserName);
        ImageView postImage = convertView.findViewById(R.id.postImage);
        VideoView postVideo = convertView.findViewById(R.id.postVideo);
        TextView postText = convertView.findViewById(R.id.postText);
        ImageButton postLikeButton = convertView.findViewById(R.id.postLikeButton);
        ImageButton commentButton = convertView.findViewById(R.id.commentButton);

        // Populate the views with the post data
        Post post = postList.get(position);
        postUserName.setText(post.getUser());
        String text = post.getText();

        SpannableString spannableString = new SpannableString(text);

        // Find all hashtags in the text
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.startsWith("#")) {
                int startIndex = text.indexOf(word);
                int endIndex = startIndex;
                while (endIndex < text.length() && text.charAt(endIndex) != ' ') {
                    endIndex++;
                }
                spannableString.setSpan(new ForegroundColorSpan(android.graphics.Color.BLUE), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        postText.setText(spannableString);

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            postImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(post.getImageUrl()).into(postImage);
        } else {
            postImage.setVisibility(View.GONE);
        }

        if (post.getVideoUrl() != null && !post.getVideoUrl().isEmpty()) {
            postVideo.setVisibility(View.VISIBLE);
            postVideo.setVideoPath(post.getVideoUrl());
            postVideo.start();
        } else {
            postVideo.setVisibility(View.GONE);
        }

        if (post.getImageUrl() == null && post.getVideoUrl() == null) {
            postImage.setVisibility(View.GONE);
            postVideo.setVisibility(View.GONE);
        }

        if (post.isLiked()) {
            postLikeButton.setBackgroundResource(R.drawable.heart_on);
        } else {
            postLikeButton.setBackgroundResource(R.drawable.heart_off);
        }

        postProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreenActivity2.changeFragment(new otherProfileFragment());
            }
        });

        postLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.isLiked()) {
                    post.setLiked(false);
                    postLikeButton.setBackgroundResource(R.drawable.heart_off);
                } else {
                    post.setLiked(true);
                    postLikeButton.setBackgroundResource(R.drawable.heart_on);
                }
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreenActivity2.changeFragment(new CommentFragment());
            }
        });
        // START FROM HERE
        postProfileImage.setImageAlpha(255);
        Glide.with(context)
                .load("http://" + GetIp.get() + ":8000/files/testProf.jpg")
                .into(postProfileImage);
        // END FROM HERE
        return convertView;
    }
}
