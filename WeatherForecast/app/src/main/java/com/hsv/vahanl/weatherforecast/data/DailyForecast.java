package com.hsv.vahanl.weatherforecast.data;

import com.hsv.vahanl.weatherforecast.data.Temperature;
import com.hsv.vahanl.weatherforecast.data.Weather;

import java.util.List;

/**
 * Created by vahanl on 8/17/16.
 */
public class DailyForecast {
    private long dt;
    private Temperature temp;
    private double pressure;
    private double humidity;
    private List<Weather> weather;
    private double speed;
    private double deg;
    private double clouds;
    private double rain;

    public long getDt() {
        return dt;
    }

    public Temperature getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    public double getClouds() {
        return clouds;
    }

    public double getRain() {
        return rain;
    }
}

