package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class callInfoFragment extends Fragment {

    private ImageView profileImage;
    private TextView nameText;
    private TextView email;
    private EditText phone;
    private RadioGroup phoneTypeGroup;
    private RadioButton mobilePhone;
    private RadioButton homePhone;
    private RadioButton companyPhone;
    private EditText address;
    private EditText quickAccess;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_info, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        nameText = view.findViewById(R.id.nameText);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        phoneTypeGroup = view.findViewById(R.id.phone_type_group);
        mobilePhone = view.findViewById(R.id.mobile_phone);
        homePhone = view.findViewById(R.id.home_phone);
        companyPhone = view.findViewById(R.id.company_phone);
        address = view.findViewById(R.id.address);
        quickAccess = view.findViewById(R.id.quickAccess);
        saveButton = view.findViewById(R.id.SaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContactInfo();
            }
        });

        return view;
    }

    private void saveContactInfo() {
        // Retrieve the entered data
        String name = nameText.getText().toString();
        String emailText = email.getText().toString();
        String phoneText = phone.getText().toString();
        String addressText = address.getText().toString();
        String quickAccessText = quickAccess.getText().toString();

        // Determine the selected phone type
        String phoneType = "";
        int selectedId = phoneTypeGroup.getCheckedRadioButtonId();
        if (selectedId == mobilePhone.getId()) {
            phoneType = "Mobile Phone";
        } else if (selectedId == homePhone.getId()) {
            phoneType = "Home Phone";
        } else if (selectedId == companyPhone.getId()) {
            phoneType = "Company Phone";
        }


    }
}
