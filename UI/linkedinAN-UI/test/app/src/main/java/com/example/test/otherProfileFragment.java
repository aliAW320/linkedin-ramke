package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class otherProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView description, firstName, lastName, country, city, profession, job, jobSituation;
    private Button followButton, messageButton, addFriendButton, educationButton, callInfoButton;
    private ImageButton backButton;
    private boolean hasFollowed = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        description = view.findViewById(R.id.description);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        country = view.findViewById(R.id.country);
        city = view.findViewById(R.id.city);
        profession = view.findViewById(R.id.profession);
        job = view.findViewById(R.id.job);
        jobSituation = view.findViewById(R.id.jobSituation);

        followButton = view.findViewById(R.id.followButton);
        messageButton = view.findViewById(R.id.messageButton);
        addFriendButton = view.findViewById(R.id.addFriendButton);
        educationButton = view.findViewById(R.id.educationButton);
        callInfoButton = view.findViewById(R.id.callInfoButton);
        backButton = view.findViewById(R.id.backButton);

        // Set up any necessary functionality for the buttons
        followButton.setOnClickListener(v -> {
            if (!hasFollowed) {
                followButton.setText("Unfollow");
                followButton.setBackgroundColor(getResources().getColor(R.color.white));
                followButton.setTextColor(getResources().getColor(R.color.black));
                hasFollowed = true;
            } else {
                followButton.setText("Follow");
                followButton.setBackgroundColor(getResources().getColor(R.color.purple));
                followButton.setTextColor(getResources().getColor(R.color.white));
                hasFollowed = false;
            }

            });

        messageButton.setOnClickListener(v -> {
            // Handle message button click
        });

        addFriendButton.setOnClickListener(v -> {
            // Handle add friend button click
        });

        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment educationFragment = new otherEducationFragment();
                mainScreenActivity2.changeFragment(educationFragment);
            }
        });

        callInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment callInfoFragment = new otherCallInfoFragment();
                mainScreenActivity2.changeFragment(callInfoFragment);
            }
        });
        backButton.setOnClickListener(v -> {
            // Handle back button click to navigate to the previous fragment
            mainScreenActivity2.changeFragment(new HomeFragment());
        });

        // Other initialization code here

        return view;
    }
}
