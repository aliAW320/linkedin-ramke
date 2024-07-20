package com.example.test;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.test.Models.ResponseSpecial;
import com.example.test.R;
import com.example.test.utils.GetIp;
import com.example.test.utils.GsonBuilderRun;
import com.example.test.utils.Validation;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class signup extends AppCompatActivity {

    private TextView loginText, errorText;
    private EditText email, firstName, lastName, password, confirmPassword, country, city, phoneNumber;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginText = findViewById(R.id.loginText);
        errorText = findViewById(R.id.error);
        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        phoneNumber = findViewById(R.id.phoneNumber);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                errorText.setText("");
                if (email.getText().toString().isEmpty() || firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || country.getText().toString().isEmpty() || city.getText().toString().isEmpty()) {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("all fileds should fill");
                } else if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("password and confirm password should match");
                }else if(!Validation.emailIsValid(email.getText().toString())){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("email is not valid");
                }else if(!Validation.passIsValid(password.getText().toString())){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("password is too weak");
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();

                            JsonObject jsonBody = new JsonObject();
                            jsonBody.addProperty("email", email.getText().toString());
                            jsonBody.addProperty("password", password.getText().toString());
                            jsonBody.addProperty("name", firstName.getText().toString());
                            jsonBody.addProperty("familyName", lastName.getText().toString());
                            jsonBody.addProperty("country", country.getText().toString());
                            jsonBody.addProperty("city", city.getText().toString());

                            RequestBody requestBody = RequestBody.create(
                                    jsonBody.toString(), MediaType.parse("application/json"));

                            Request request = new Request.Builder()
                                    .url("http://" + GetIp.get() + ":8000/user")
                                    .header("Content-Type", "application/json")
                                    .header("User-Agent", "insomnia/9.2.0")
                                    .post(requestBody)
                                    .build();

                            try (Response response = client.newCall(request).execute()) {
                                int statusCode = response.code();
                                Log.d(TAG, "Status Code: " + statusCode);
                                String responseBody = response.body().string();
                                Log.d(TAG, "Response Body: " + responseBody);
                                ResponseSpecial responseSpecial = GsonBuilderRun.getGson().fromJson(responseBody, ResponseSpecial.class);
                                if (statusCode == 200) {
                                    Intent intent = new Intent(signup.this, login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            errorText.setVisibility(View.VISIBLE);
                                            errorText.setText(responseSpecial.getMessage());
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Network request failed", e);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorText.setVisibility(View.VISIBLE);
                                        errorText.setText("connection failed");
                                    }
                                });
                            }

                        }
                    }).start();
                }
            }
        });
    }
}
