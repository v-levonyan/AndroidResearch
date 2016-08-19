package com.hsv.vahanl.weatherforecast.data;

import com.hsv.vahanl.weatherforecast.data.City;
import com.hsv.vahanl.weatherforecast.data.DailyForecast;

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
