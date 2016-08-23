package com.hsv.vahanl.weatherforecast.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;

@Getter

public class CityCurrentWeatherInfo extends RealmObject {
    @PrimaryKey
    private long id;
    private Coord coord;
    private RealmList<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private String name;
    private int cod;

    @Override
    public String toString() {
        return name;
    }
}
