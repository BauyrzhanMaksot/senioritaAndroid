package com.example.user.senioritaandroid.WebSocket;

import com.example.user.senioritaandroid.WebSocket.StompMessage;

public interface StompMessageListener {
    void onMessage(StompMessage message);
}
