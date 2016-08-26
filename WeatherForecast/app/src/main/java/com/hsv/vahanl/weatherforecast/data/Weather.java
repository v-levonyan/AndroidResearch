package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Weather extends RealmObject {

    @Expose private int id;
    @Expose private String main;
    @Expose private String description;
    @Expose private String icon;
    @Override
    public String toString() {
        return description;
    }
}
