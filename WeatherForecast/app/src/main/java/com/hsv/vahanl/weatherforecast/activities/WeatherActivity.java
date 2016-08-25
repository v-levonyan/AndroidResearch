package com.hsv.vahanl.weatherforecast.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hsv.vahanl.weatherforecast.fragments.LocationFragment;
import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.fragments.WeatherFragment;

public class WeatherActivity extends AppCompatActivity {

    private static final String EXTRA_CITY_ID = "com.hsv.vahanl.weatherforecast.cityId";

    public static Intent newIntent(Context packageContext, long cityId) {
        Intent i = new Intent(packageContext, WeatherActivity.class);
        i.putExtra(EXTRA_CITY_ID, cityId);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        long cityId = getIntent().getLongExtra(EXTRA_CITY_ID, 0);

        FragmentManager fm = getSupportFragmentManager();
        Fragment locationFragment = fm.findFragmentById(R.id.location_fragment_container);
        Fragment weatherFragment = fm.findFragmentById(R.id.weather_fragment_container);

        FragmentTransaction transaction= fm.beginTransaction();

        if (weatherFragment == null) {
            weatherFragment = new WeatherFragment().newInstance(cityId);
            transaction.add(R.id.weather_fragment_container, weatherFragment);
        }

        if (locationFragment == null) {
            locationFragment = new LocationFragment().newInstance(cityId);
            transaction.add(R.id.location_fragment_container, locationFragment);
        }

        transaction.commit();

    }
}
