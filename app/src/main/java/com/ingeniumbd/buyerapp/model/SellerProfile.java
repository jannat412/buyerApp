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
}
