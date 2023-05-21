package com.example.mainproject.ResponeAPI;

import com.example.mainproject.Model.*;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseUpload implements Serializable {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private User user;

    public boolean getSuccess() {
        return success;
    }

    public void setError(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
