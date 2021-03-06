package com.egs.vahanl.pointofinterest;


/**
 * Created by vahanl on 7/18/16.
 */
public class POI {
    private int id;
    private String title;
    private String address;
    private String transport;
    private String email;
    private String geocoordinates;
    private String description;
    private String phone;

    public POI() {

    }

    public POI(
            int id,
            String title,
            String address,
            String transport,
            String email,
            String geocoordinates,
            String description,
            String phone
    ) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.transport = transport;
        this.email = email;
        this.geocoordinates = geocoordinates;
        this.description = description;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return title + " : " + geocoordinates;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getTransport() {
        return transport;
    }

    public String getEmail() {
        return email;
    }

    public String getGeocoordinates() {
        return geocoordinates;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGeocoordinates(String geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
