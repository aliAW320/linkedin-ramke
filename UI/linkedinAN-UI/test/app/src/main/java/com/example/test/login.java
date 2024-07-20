package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Models.ResponseSpecial;
import com.example.test.utils.GetIp;
import com.example.test.utils.GsonBuilderRun;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    Button signUpButton;
    TextView errorText;
    TextView errorConnectionText;
    private static final String TAG = "LoginActivity";
    private static final String PREF_NAME = "localData";
    int PRIVATE_MODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        errorText = findViewById(R.id.errorText);
        errorConnectionText = findViewById(R.id.errorConnectionText);
        SharedPreferences pref = getApplicationContext()
                .getSharedPreferences("tokenData", 0);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pref.getString("token", "").equals("")){
                    Intent intent = new Intent(login.this, mainScreenActivity2.class);
                    startActivity(intent);
                    finish();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        JsonObject jsonBody = new JsonObject();
                        jsonBody.addProperty("email", email.getText().toString());
                        jsonBody.addProperty("password", password.getText().toString());

                        RequestBody requestBody = RequestBody.create(
                                jsonBody.toString(), MediaType.parse("application/json"));

                        Request request = new Request.Builder()
                                .url("http://"+ GetIp.get()+ ":8000/login")
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
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("token", responseSpecial.getToken());
                                editor.apply();
                                Intent intent = new Intent(login.this, mainScreenActivity2.class);
                                startActivity(intent);
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorText.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Network request failed", e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    errorConnectionText.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }
}
