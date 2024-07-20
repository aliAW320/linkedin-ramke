package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class onePostFragment extends Fragment {
    private ImageView postProfileImage;
    private TextView postUserName;
    private ImageView postImage;
    private VideoView postVideo;
    private TextView postText;
    private ImageButton postLikeButton;
    private ImageButton commentButton;
    private boolean isLiked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_post, container, false);

        postProfileImage = view.findViewById(R.id.postProfileImage);
        postUserName = view.findViewById(R.id.postUserName);
        postImage = view.findViewById(R.id.postImage);
        postVideo = view.findViewById(R.id.postVideo);
        postText = view.findViewById(R.id.postText);
        postLikeButton = view.findViewById(R.id.postLikeButton);
        commentButton = view.findViewById(R.id.commentButton);

        postLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike();
            }
        });

        return view;
    }

    private void toggleLike() {
        if (isLiked) {
            postLikeButton.setBackgroundResource(R.drawable.heart_off);
        } else {
            postLikeButton.setBackgroundResource(R.drawable.heart_on);
        }
        isLiked = !isLiked;
    }
}
