package com.example.mainproject.ResponeAPI;

import com.google.gson.annotations.SerializedName;

public class ResponseToAPI {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public ResponseToAPI() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
