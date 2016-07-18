package com.egs.vahanl.pointofinterest;

//import java.awt.geom.Point2D.Point2D.Float;

import android.graphics.PointF;

/**
 * Created by vahanl on 7/18/16.
 */
public class POI {
    private int mId;
    private String mTitle;
    private String mAddress;
    private String mTransport;
    private String mEmail;
    private PointF mLocation;
    private String mDescription;
    private String mPhone;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getTransport() {
        return mTransport;
    }

    public String getEmail() {
        return mEmail;
    }

    public PointF getLocation() {
        return mLocation;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setTransport(String transport) {
        mTransport = transport;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setLocation(PointF location) {
        mLocation = location;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
