package com.hsv.vahanl.weatherforecast;

import java.util.List;

/**
 * Created by vahanl on 8/17/16.
 */
public class CityForecast {
    private City city;
    private List<DailyForecast> list;

    public City getCity() {
        return city;
    }

    public List<DailyForecast> getList() {
        return list;
    }
}
