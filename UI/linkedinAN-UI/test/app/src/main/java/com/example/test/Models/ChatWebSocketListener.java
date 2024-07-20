package com.example.test.Models;

import android.os.Handler;
import android.os.Looper;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import okio.ByteString;

public class ChatWebSocketListener extends WebSocketListener {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // Handle onOpen
    }

    @Override
    public void onMessage(WebSocket webSocket, final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Handle incoming message
            }
        });
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        onMessage(webSocket, bytes.toString());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // Handle failure
    }
}
