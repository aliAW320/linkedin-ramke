package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.utils.GetToken;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 2000;
    private static final String PREF_NAME = "localData";
    int PRIVATE_MODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = GetToken.get(getApplicationContext());
                if(token.length() > 3){
                    Intent intent = new Intent(MainActivity.this, mainScreenActivity2.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
