package com.example.test;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.Models.Education;
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

public class SelfEducationFragment extends Fragment {

    private EditText editTextNameOfEducationCenter;
    private EditText editTextFieldOfStudy;
    private DatePicker editTextStartDateOfEducation;
    private DatePicker editTextEndDateOfEducation;
    private EditText editTextGradeOfEducation;
    private EditText editTextDescriptionOfEducation;
    private EditText editTextOtherDescriptionOf;
    private EditText editTextSkill1;
    private EditText editTextSkill2;
    private EditText editTextSkill3;
    private EditText editTextSkill4;
    private EditText editTextSkill5;
    private Button saveButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_education, container, false);

        // Initialize EditText fields
        editTextNameOfEducationCenter = view.findViewById(R.id.editTextNameOfEducationCenter);
        editTextStartDateOfEducation = view.findViewById(R.id.editTextStartDateOfEducation);
        editTextEndDateOfEducation = view.findViewById(R.id.editTextEndDateOfEducation);
        editTextGradeOfEducation = view.findViewById(R.id.editTextGradeOfEducation);
        editTextDescriptionOfEducation = view.findViewById(R.id.editTextDescriptionOfEducation);
        editTextOtherDescriptionOf = view.findViewById(R.id.editTextOtherDescriptionOf);
        editTextSkill1 = view.findViewById(R.id.editTextSkill1);
        editTextSkill2 = view.findViewById(R.id.editTextSkill2);
        editTextSkill3 = view.findViewById(R.id.editTextSkill3);
        editTextSkill4 = view.findViewById(R.id.editTextSkill4);
        saveButton = view.findViewById(R.id.saveButton);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonBody = new JsonObject();
                RequestBody requestBody = RequestBody.create(
                        jsonBody.toString(), MediaType.parse("application/json"));
                Request request = new Request.Builder()
                        .url("http://" + GetIp.get() + ":8000/education")
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
                            Education education = responseSpecial.getEducation();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editTextNameOfEducationCenter.setText(education.getNameOfEducationCenter());
//                                    editTextStartDateOfEducation.setText(education.getStartDateOfEducation());
//                                    editTextEndDateOfEducation.setText(education.getEndDateOfEducation());
                                    editTextGradeOfEducation.setText(String.valueOf(education.getGradeOfEducation()));
                                    editTextDescriptionOfEducation.setText(education.getDescriptionOfEducation());
                                    editTextOtherDescriptionOf.setText(education.getOtherDescriptionOf());
                                    editTextSkill1.setText(education.getSkill1());
                                    editTextSkill2.setText(education.getSkill2());
                                    editTextSkill3.setText(education.getSkill3());
                                    editTextSkill4.setText(education.getSkill4());
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
        }).start();        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        JsonObject jsonBody = new JsonObject();
                        jsonBody.addProperty("nameOfEducationCenter", editTextNameOfEducationCenter.getText().toString());
//                        jsonBody.addProperty("startDateOfEducation", editTextStartDateOfEducation.getText().toString());
//                        jsonBody.addProperty("endDateOfEducation", editTextEndDateOfEducation.getText().toString());
                        jsonBody.addProperty("gradeOfEducation", editTextGradeOfEducation.getText().toString());
                        jsonBody.addProperty("descriptionOfEducation", editTextDescriptionOfEducation.getText().toString());
                        jsonBody.addProperty("job", editTextOtherDescriptionOf.getText().toString());
                        jsonBody.addProperty("skill1", editTextSkill1.getText().toString());
                        jsonBody.addProperty("skill2", editTextSkill2.getText().toString());
                        jsonBody.addProperty("skill3", editTextSkill3.getText().toString());
                        jsonBody.addProperty("skill4", editTextSkill4.getText().toString());
                        RequestBody requestBody = RequestBody.create(
                                jsonBody.toString(), MediaType.parse("application/json"));

                        Request request = new Request.Builder()
                                .url("http://" + GetIp.get() + ":8000/education")
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

        return view;
    }



}
