// ProfileFragment.java
package com.example.test;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.test.Models.ResponseSpecial;
import com.example.test.Models.User;
import com.example.test.utils.GetIp;
import com.example.test.utils.GetToken;
import com.example.test.utils.GsonBuilderRun;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    private Button educationButton;
    private Button callInfoButton;
    private Button saveButton;
    private RadioButton lookingForJob;
    private RadioButton lookingForHire;
    private RadioButton none;
    private RadioGroup radioGroup;
    private TextView email;
    private EditText firstName;
    private EditText lastName;
    private EditText country;
    private EditText city;
    private EditText profession;
    private EditText job;



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find views by ID
        educationButton = view.findViewById(R.id.educationButton);
        callInfoButton = view.findViewById(R.id.callInfoButton);
        saveButton = view.findViewById(R.id.SaveButton);
        lookingForJob = view.findViewById(R.id.lookingForjob);
        lookingForHire = view.findViewById(R.id.lookingForHire);
        none = view.findViewById(R.id.none);
        radioGroup = view.findViewById(R.id.radioGroup);
        email = view.findViewById(R.id.emailField);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        country = view.findViewById(R.id.country);
        city = view.findViewById(R.id.city);
        profession = view.findViewById(R.id.profession);
        job = view.findViewById(R.id.job);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonBody = new JsonObject();
                RequestBody requestBody = RequestBody.create(
                        jsonBody.toString(), MediaType.parse("application/json"));
                Request request = new Request.Builder()
                        .url("http://" + GetIp.get() + ":8000/user")
                        .header("Content-Type", "application/json")
                        .header("User-Agent", "insomnia/9.2.0")
                        .header("Authorization", "Bearer " + GetToken.get(getContext()))
                        .get()
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    int statusCode = response.code();
                    Log.d(TAG, "Status Code: " + statusCode);
                    if (statusCode == 200) {
                        String responseBody = response.body().string();
                        Log.d(TAG, "Response Body: " + responseBody);
                        ResponseSpecial responseSpecial = GsonBuilderRun.getGson().fromJson(responseBody, ResponseSpecial.class);
                        if (responseSpecial.isResult()) {
                            User user = responseSpecial.getUser();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    email.setText(user.getEmail());
                                    firstName.setText(user.getName());
                                    lastName.setText(user.getFamilyName());
                                    country.setText(user.getCountry());
                                    city.setText(user.getCity());
                                    profession.setText(user.getProfession());
                                    job.setText(user.getJob());
                                }
                            });
                        } else {
                            Log.e(TAG, "Response special indicates an error: " + responseSpecial.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Network request failed with status code: " + statusCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Network request failed", e);
                }
            }
        }).start();

        // Set click listeners
        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment educationFragment = new SelfEducationFragment();
                mainScreenActivity2.changeFragment(educationFragment);
            }
        });

        callInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreenActivity2.changeFragment(new callInfoFragment());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        JsonObject jsonBody = new JsonObject();
                        jsonBody.addProperty("email", email.getText().toString());
                        jsonBody.addProperty("name", firstName.getText().toString());
                        jsonBody.addProperty("familyName", lastName.getText().toString());
                        jsonBody.addProperty("country", country.getText().toString());
                        jsonBody.addProperty("city", city.getText().toString());
                        jsonBody.addProperty("job", job.getText().toString());
                        jsonBody.addProperty("profession", profession.getText().toString());
                        RequestBody requestBody = RequestBody.create(
                                jsonBody.toString(), MediaType.parse("application/json"));

                        Request request = new Request.Builder()
                                .url("http://" + GetIp.get() + ":8000/user")
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "insomnia/9.2.0")
                                .header("Authorization", "Bearer " + GetToken.get(getContext()))
                                .patch(requestBody)
                                .build();
                        try (Response response = client.newCall(request).execute()) {
                            int statusCode = response.code();
                            Log.d(TAG, "Status Code: " + statusCode);
                            if (statusCode == 200) {
                                String responseBody = response.body().string();
                                Log.d(TAG, "Response Body: " + responseBody);
                                ResponseSpecial responseSpecial = GsonBuilderRun.getGson().fromJson(responseBody, ResponseSpecial.class);
                                if (responseSpecial.isResult()) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"changes", Toast.LENGTH_SHORT);
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "Response special indicates an error: " + responseSpecial.getMessage());
                                }
                            } else {
                                Log.e(TAG, "Network request failed with status code: " + statusCode);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Network request failed", e);
                        }
                    }
                }).start();
            }
        });

        lookingForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lookingForHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your logic here
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Add your logic here
            }
        });

        return view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
