package com.example.mainproject.ResponeAPI;

import com.example.mainproject.Model.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseRegister implements Serializable {
    @SerializedName("success")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private User user;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
