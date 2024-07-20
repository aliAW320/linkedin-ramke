package com.example.test.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GetToken {
    public static String get(Context context){
        SharedPreferences pref = context.getSharedPreferences("tokenData", 0);
        return pref.getString("token", "");
    }
}
