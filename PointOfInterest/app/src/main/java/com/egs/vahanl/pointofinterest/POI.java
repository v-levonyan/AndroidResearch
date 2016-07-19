package com.egs.vahanl.pointofinterest;

//import java.awt.geom.Point2D.Point2D.Float;

import android.graphics.PointF;

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
