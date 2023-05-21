package com.example.mainproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Pets {
    @SerializedName("pets_id")
    private int pets_id;
    @SerializedName("pets_name")
    private String pets_name;
    @SerializedName("weight")
    private Double weight;
    @SerializedName("age")
    private String age;
    @SerializedName("gender")
    private int gender;
    @SerializedName("price")
    private Double price;
    @SerializedName("count")
    private int count;
    @SerializedName("rated")
    private Double rated;
    @SerializedName("numOfRate")
    private int numOfRate;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;
    @SerializedName("styleList")
    private List<Style> styleList;
    @SerializedName("imagesList")
    private List<ImagesPet> imagesList;

    public Pets(){

    }

    public int getPets_id() {
        return pets_id;
    }

    public void setPets_id(int pets_id) {
        this.pets_id = pets_id;
    }

    public String getPets_name() {
        return pets_name;
    }

    public void setPets_name(String pets_name) {
        this.pets_name = pets_name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getRated() {
        return rated;
    }

    public void setRated(Double rated) {
        this.rated = rated;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
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

    public List<Style> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<Style> styleList) {
        this.styleList = styleList;
    }

    public List<ImagesPet> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImagesPet> imagesList) {
        this.imagesList = imagesList;
    }
}

