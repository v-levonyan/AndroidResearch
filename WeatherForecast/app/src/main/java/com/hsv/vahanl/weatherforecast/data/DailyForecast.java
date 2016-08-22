package com.hsv.vahanl.weatherforecast.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class DailyForecast extends RealmObject {
    private long dt;
    private Temperature temp;
    private double pressure;
    private double humidity;
    private RealmList<Weather> weather;
    private double speed;
    private double deg;
    private double clouds;
    private double rain;
}

