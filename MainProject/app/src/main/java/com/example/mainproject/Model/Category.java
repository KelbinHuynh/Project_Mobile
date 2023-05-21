package com.example.mainproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Category implements Serializable {
        @SerializedName("category_id")
        private int category_id;
        @SerializedName("category_name")
        private String category_name;
        @SerializedName("isDeleted")
        private int isDeleted;

        @SerializedName("createdAt")
        private Date createAt;
        @SerializedName("updatedAt")
        private Date updatedAt;

        @SerializedName("style")
        private List<Style> styleList;

        public Category() {
        }

        public int getCategory_id() {
                return category_id;
        }

        public void setCategory_id(int category_id) {
                this.category_id = category_id;
        }

        public String getCategory_name() {
                return category_name;
        }

        public void setCategory_name(String category_name) {
                this.category_name = category_name;
        }

        public int isDeleted() {
                return isDeleted;
        }

        public void setDeleted(int deleted) {
                isDeleted = deleted;
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

        public List<Style> getStyleList() {
                return styleList;
        }

        public void setStyleList(List<Style> styleList) {
                this.styleList = styleList;
        }
}
