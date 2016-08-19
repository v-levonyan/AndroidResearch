package com.hsv.vahanl.weatherforecast.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hsv.vahanl.weatherforecast.data.CityForecast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vahanl on 8/17/16.
 */
public class CityForecastPrefs {
    private static final String PREFS_NAME = "com.hsv.vahanl.weatherforecast.CityForcastPrefs";
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public CityForecastPrefs(Context context) {
        if (settings == null) {
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        editor = settings.edit();
    }

    public CityForecast getCity(long id) {
        String cityForecastJson = settings.getString("" + id, "");
        Gson gson = new Gson();
        CityForecast cityForecast = gson.fromJson(cityForecastJson, CityForecast.class);
        return cityForecast;
    }


    public void setCityForecast(CityForecast cityForecast) {
        Gson gson = new Gson();
        String cityForecastJson = gson.toJson(cityForecast);
        String id = "" + cityForecast.getCity().getId(); // get storage key
        editor.putString(id, cityForecastJson);
        editor.commit();
    }

    public List<CityForecast> getCities() {
        List<CityForecast> citiesForecast = new ArrayList<>();
        Map<String, ?> keys = settings.getAll();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            String cityForecastJson = (String) entry.getValue();
            CityForecast city = gson.fromJson(cityForecastJson, CityForecast.class);
            citiesForecast.add(city);
        }

        return citiesForecast;
    }
}
