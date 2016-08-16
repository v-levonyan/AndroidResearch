package com.hsv.vahanl.weatherforecast.data;

/**
 * Created by vahanl on 8/10/16.
 */
public class Sys {
    private double message;
    private String country;
    private long sunrise;
    private long sunset;

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}