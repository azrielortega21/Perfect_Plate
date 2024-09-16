package com.mobdeve.s14.group4;

public class Address {
    private String street;
    private String state;
    private String city;
    private String postalCode;

    public Address(){

    }

    public Address(String street, String city, String state, String postalCode){
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public String getStreet(){
        return this.street;
    }

    public String getState(){
        return this.state;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isValid(){
        boolean isValid = true;

        if (this.street.isEmpty()){
            isValid = false;
        }

        if (this.city.isEmpty()){
            isValid = false;
        }

        if (this.state.isEmpty()){
            isValid = false;
        }

        if (this.postalCode.isEmpty()){
            isValid = false;
        }

        return isValid;
    }
}
