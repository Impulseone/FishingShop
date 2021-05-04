package com.skynet.fish_shop.model;

public class DeliveryData {
    private String region;
    private String index;
    private String city;
    private String street;

    public DeliveryData() {
        region = "";
        index = "";
        city = "";
        street = "";
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
