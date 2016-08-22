package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Main extends RealmObject {
    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double sea_level;
    private double grnd_level;

}