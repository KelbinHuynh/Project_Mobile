package com.example.mainproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Cart {
    @SerializedName("cart_id")
    private int cart_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;
    @SerializedName("cart_detail")
    private List<CartDetail> cart_detail;

    public Cart() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartDetail> getCart_detail() {
        return cart_detail;
    }

    public void setCart_detail(List<CartDetail> cart_detail) {
        this.cart_detail = cart_detail;
    }
}
