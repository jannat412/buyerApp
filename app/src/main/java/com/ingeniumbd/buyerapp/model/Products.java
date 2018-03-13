package com.ingeniumbd.buyerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Princess on 2/10/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {

    @JsonProperty("offerTitle")
    public String offerTitle;
    @JsonProperty("endDate")
    public String endDate;
    @JsonProperty("startDate")
    public String startDate;
    @JsonProperty("location")
    public String location;
    @JsonProperty("numberOfPeople")
    public String numberOfPeople;
    @JsonProperty("regularPrice")
    public String regularPrice;
    @JsonProperty("offerPrice")
    public String offerPrice;
    @JsonProperty("id")
    public String id;
    @JsonProperty("productImageOne")
    public String productImageOne;
    @JsonProperty("userId")
    public String userId;

    public Products(){

    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImageOne() {
        return productImageOne;
    }

    public void setProductImageOne(String productImageOne) {
        this.productImageOne = productImageOne;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
