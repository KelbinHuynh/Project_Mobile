package com.example.mainproject.ResponeAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ResponseSales implements Serializable {
    @SerializedName("count_order")
    private int countOrder;

    @SerializedName("sum_sales")
    private double sumSales;

    @SerializedName("dateOrder")
    private int dateOrder;

    public ResponseSales() {
    }

    public int getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(int countOrder) {
        this.countOrder = countOrder;
    }

    public double getSumSales() {
        return sumSales;
    }

    public void setSumSales(double sumSales) {
        this.sumSales = sumSales;
    }

    public int getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(int dateOrder) {
        this.dateOrder = dateOrder;
    }

    @Override
    public String toString() {
        return "ResponseSales{" +
                "countOrder=" + countOrder +
                ", sumSales=" + sumSales +
                '}';
    }


}
