package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Weather extends RealmObject {
    private int id;
    private String main;
    private String description;
    private String icon;
    @Override
    public String toString() {
        return "main: " + main +
                "description: " + description;
    }
}
