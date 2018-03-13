package com.ingeniumbd.buyerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KamrulHasan on 1/4/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerProfile {

    @JsonProperty("geoLat")
    public String geoLat;
    @JsonProperty("geoLong")
    public String geoLong;
    @JsonProperty("address")
    public String address;
    @JsonProperty("storeName")
    public String storeName;
    @JsonProperty("category")
    public String category;
    @JsonProperty("country")
    public String country;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("cover_photo")
    public String cover_photo;
    @JsonProperty("profile_picture")
    public String profile_picture;
    public SellerProfile() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SellerProfile(String geoLat, String geoLong, String address, String storeName) {
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.address = address;
        this.storeName = storeName;
    }

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }

    public String getAddress() {
        return address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
