package com.muhammadyaseen.classifiedapp;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadPost {
    private String mImageUrl;
    public String title;
    public String description;
    public String price;
    public String country;
    public String city;
    public String number;


    public UploadPost() {
    }

    public UploadPost(String mImageUrl, String title, String description, String price, String country, String city, String number) {
        this.mImageUrl = mImageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
        this.country = country;
        this.city = city;
        this.number = number;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
