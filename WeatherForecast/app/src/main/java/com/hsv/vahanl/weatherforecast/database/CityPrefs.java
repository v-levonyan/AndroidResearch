package com.hsv.vahanl.weatherforecast.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hsv.vahanl.weatherforecast.data.City;
import com.hsv.vahanl.weatherforecast.data.CityForecast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vahanl on 8/11/16.
 */
public class CityPrefs {
    private static final String PREFS_NAME = "com.hsv.vahanl.weatherforecast.database.CityPrefs";
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public CityPrefs(Context context) {
        if (settings == null) {
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        editor = settings.edit();
    }

    public City getCity(long id) {
        String cityJson = settings.getString("" + id, "");
        Gson gson = new Gson();
        City city = gson.fromJson(cityJson, City.class);
        return city;
    }

    public void setCity(City city) {
        Gson gson = new Gson();
        String cityJson = gson.toJson(city);
        String id = "" + city.getId(); // get storage key
        editor.putString(id, cityJson);
        editor.commit();
    }

    public void setCityForecast(CityForecast cityForecast) {
        Gson gson = new Gson();
        String cityForecastJson = gson.toJson(cityForecast);
        String id = "" + cityForecast.getCity().getId(); // get storage key
        editor.putString(id, cityForecastJson);
        editor.commit();
    }

    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        Map<String, ?> keys = settings.getAll();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            String cityJson = (String) entry.getValue();
            City city = gson.fromJson(cityJson, City.class);
            cities.add(city);
        }

        return cities;
    }
}
