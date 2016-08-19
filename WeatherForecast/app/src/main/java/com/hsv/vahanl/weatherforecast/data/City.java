package com.hsv.vahanl.weatherforecast.data;

import com.hsv.vahanl.weatherforecast.data.Clouds;
import com.hsv.vahanl.weatherforecast.data.Coord;
import com.hsv.vahanl.weatherforecast.data.Main;
import com.hsv.vahanl.weatherforecast.data.Rain;
import com.hsv.vahanl.weatherforecast.data.Sys;
import com.hsv.vahanl.weatherforecast.data.Weather;
import com.hsv.vahanl.weatherforecast.data.Wind;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahanl on 8/10/16.
 */
public class City {
    private Coord coord;
    private List<Weather> weather = new ArrayList<>();
    private String base;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private long id;
    private String name;
    private int cod;

    public City() {

    }

    public long getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return name;
    }
}
