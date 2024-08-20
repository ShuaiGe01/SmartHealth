package com.example.travelassistant.utils;

public interface AsyncResponse {
    void processFinish(String response);
    void processFailure();
}
