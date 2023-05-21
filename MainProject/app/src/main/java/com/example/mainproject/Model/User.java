package com.example.mainproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("gender_name")
    private String gender;
    @SerializedName("email")
    private String email;
    @SerializedName("role_name")
    private String role;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("phone")
    private String phone;
    @SerializedName("addresses")
    private String addresses;
    @SerializedName("createdAt")
    private Date createAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public User(int user_id, String username, String name, String gender, String email, String role, String avatar, String phone, String addresses, Date createAt, Date updatedAt) {
        this.user_id = user_id;
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.phone = phone;
        this.addresses = addresses;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
