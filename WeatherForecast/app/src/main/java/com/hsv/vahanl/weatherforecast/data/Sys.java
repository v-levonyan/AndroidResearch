package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Sys extends RealmObject {
    private double message;
    private String country;
    private long sunrise;
    private long sunset;
}