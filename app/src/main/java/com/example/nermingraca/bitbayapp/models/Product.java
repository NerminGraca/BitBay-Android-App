package com.example.nermingraca.bitbayapp.models;

/**
 * Created by nermingraca on 14/04/15.
 */
public class Product {

    private int mId;
    private String mName;
    private double mPrice;
    private String mDescription;
    private String mOwner;
    private String thumbnailUrl;
    private int mSellerId;
    private int mQuantity;

    public Product(int mId, String mName, double mPrice, String mDescription,
                   String mOwner, String thumbnailUrl, int mSellerId, int quantity) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mDescription = mDescription;
        this.mOwner = mOwner;
        this.thumbnailUrl = thumbnailUrl;
        this.mSellerId = mSellerId;
        this.mQuantity = quantity;
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

    public String getmOwner() {
        return mOwner;
    }

    public void setmOwner(String mOwner) {
        this.mOwner = mOwner;
    }

    public String getThumbnailUrl() {
        return "http://10.0.2.2:9000" + "/assets/" + thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getmSellerId() {
        return mSellerId;
    }

    public void setmSellerId(int mSellerId) {
        this.mSellerId = mSellerId;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }
}
