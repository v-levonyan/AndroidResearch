package com.hsv.vahanl.weatherforecast.data;

/**
 * Created by vahanl on 8/10/16.
 */
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "main: " + main +
                "description: " + description;
    }
}
