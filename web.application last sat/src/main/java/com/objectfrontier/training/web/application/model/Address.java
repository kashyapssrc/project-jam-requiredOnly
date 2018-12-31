package com.objectfrontier.training.web.application.model;

public class Address {

    long id ;
    private String street;
    private String city;
    private int pincode;

    public Address() {}

    public Address(String street, String city, int pincode) {
        this.street = street;
        this.city = city;
        this.pincode = pincode;
    }

    public Address(long id, String street, String city, int pincode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
    }

    public long getId() {
        return this.id;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public int getPincode() {
        return this.pincode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", street=" + street + ", city=" + city + ", pincode=" + pincode + "]";
    }
}
