package org.diiage.martin.chillindijon.models;

/**
 * Created by alexandre on 16/03/18.
 */

public class Location {
    private String address;
    private String postalCode;
    private String city;
    private android.location.Location position;

    public Location() {

    }

    public Location(String address, String postalCode, String city, android.location.Location position) {
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public android.location.Location getPosition() {
        return position;
    }

    public void setPosition(android.location.Location position) {
        this.position = position;
    }
}
