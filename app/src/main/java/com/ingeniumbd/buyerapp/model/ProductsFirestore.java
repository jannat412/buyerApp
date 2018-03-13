package com.ingeniumbd.buyerapp.model;

/**
 * Created by Princess on 2/14/2018.
 */

public class ProductsFirestore {

    String numberOfPeople,regularPrice,offerPrice,location;

    public ProductsFirestore(String numberOfPeople, String regularPrice, String offerPrice,String location) {
        this.numberOfPeople = numberOfPeople;
        this.regularPrice = regularPrice;
        this.offerPrice = offerPrice;
        this.location = location;
    }

    public ProductsFirestore() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }
}
