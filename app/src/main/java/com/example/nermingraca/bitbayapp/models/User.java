package com.example.nermingraca.bitbayapp.models;

import java.util.List;

/**
 * Created by nermingraca on 22/04/15.
 */
public class User {

    private int mId;
    private String mEmail;
    private String mPassword;
    private String mUsername;
    private List<Product> mProducts;

    public User(){};

    public User(int mId, String mEmail, String mPassword) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public User(int mId, String mEmail, String mPassword,
                String mUsername, List<Product> mProducts) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mUsername = mUsername;
        this.mProducts = mProducts;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public List<Product> getmProducts() {
        return mProducts;
    }

    public void setmProducts(List<Product> products) {
        this.mProducts = products;
    }
}
