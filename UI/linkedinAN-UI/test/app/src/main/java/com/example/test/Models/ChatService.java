package com.example.test.Models;

import android.os.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ChatService {
    @GET("messages")
    Call<List<Message>> getMessages();

    @POST("messages")
    Call<Void> sendMessage(@Body Message message);
}
