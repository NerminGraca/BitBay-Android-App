package com.example.nermingraca.bitbayapp.models;

/**
 * Created by nermingraca on 14/04/15.
 */
public class Product {

    private int mId;
    private String mName;
    private double mPrice;
    private String mDescription;

    public Product(int mId, String mName, double mPrice, String mDescription) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mDescription = mDescription;
    }

    @Override
    public String toString() {
        return "Name: " + mName + '\n' +
               "Price: " + mPrice;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
