package com.hsv.vahanl.weatherforecast;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class WeatherActivity extends SingleFragmentActivity {

    private static final String EXTRA_CITY_ID = "com.hsv.vahanl.weatherforecast.cityId";

    public static Intent newIntent(Context packageContext, long cityId) {
        Intent i = new Intent(packageContext, WeatherActivity.class);
        i.putExtra(EXTRA_CITY_ID, cityId);
        return i;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather;
    }

    @Override
    public Fragment createFragment() {

        long cityId = getIntent().getLongExtra(EXTRA_CITY_ID, 0);
        return WeatherFragment.newInstance(cityId);
    }
}
