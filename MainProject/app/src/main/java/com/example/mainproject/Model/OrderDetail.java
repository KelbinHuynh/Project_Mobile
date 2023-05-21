package com.example.mainproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OrderDetail {
    @SerializedName("orderdetail_id")
    private int orderdetail_id;
    @SerializedName("pets_id")
    private int pets_id;
    @SerializedName("count_SP")
    private int count_SP;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;
    @SerializedName("pets")
    private Pets pets;

    public OrderDetail() {
    }

    public int getOrderdetail_id() {
        return orderdetail_id;
    }

    public void setOrderdetail_id(int orderdetail_id) {
        this.orderdetail_id = orderdetail_id;
    }

    public int getPets_id() {
        return pets_id;
    }

    public void setPets_id(int pets_id) {
        this.pets_id = pets_id;
    }

    public int getCount_SP() {
        return count_SP;
    }

    public void setCount_SP(int count_SP) {
        this.count_SP = count_SP;
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

    public Pets getPets() {
        return pets;
    }

    public void setPets(Pets pets) {
        this.pets = pets;
    }
}
