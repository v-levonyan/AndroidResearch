package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class DailyForecast extends RealmObject {

    @Expose private long dt;
    @Expose private Temperature temp;
    @Expose private double pressure;
    @Expose private double humidity;
    @Expose private RealmList<Weather> weather;
    @Expose private double speed;
    @Expose private double deg;
    @Expose private double clouds;
    @Expose private double rain;
}

