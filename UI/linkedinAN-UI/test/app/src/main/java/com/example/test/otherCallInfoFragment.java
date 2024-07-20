package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class otherCallInfoFragment extends Fragment {

    private ImageView profileImage;
    private TextView nameText, email, phone, phoneType, address, quickAccess;
    private ImageButton backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_call_info, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        nameText = view.findViewById(R.id.nameText);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        phoneType = view.findViewById(R.id.phone_type);
        address = view.findViewById(R.id.address);
        quickAccess = view.findViewById(R.id.quickAccess);
        backButton = view.findViewById(R.id.backButton);

        // Set up back button click listener
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            mainScreenActivity2.changeFragment(new HomeFragment());
        });

        // Other initialization code here

        return view;
    }
}
