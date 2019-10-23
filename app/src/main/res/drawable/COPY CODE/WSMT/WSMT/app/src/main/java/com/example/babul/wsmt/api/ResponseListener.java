package com.example.babul.wsmt.api;

/**
 * Created by newton on 7/6/17.
 */

public interface ResponseListener {
    void getResponse(Object responseData);
    void getError(String errMsg);
}
