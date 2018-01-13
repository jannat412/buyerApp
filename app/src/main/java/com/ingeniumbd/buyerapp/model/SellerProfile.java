package com.ingeniumbd.buyerapp.model;

/**
 * Created by KamrulHasan on 1/4/2018.
 */

public class SellerProfile {
    public String geo;
    public String address;
    public String storeName;

    public SellerProfile() {
    }

    public SellerProfile(String geo, String address, String storeName) {
        this.geo = geo;
        this.address = address;
        this.storeName = storeName;

    }
    public String getGeo() {
        return geo;
    }

    public String getAddress() {
        return address;
    }
    public String getStoreName() {
        return storeName;
    }
}
