package com.hsv.vahanl.weatherforecast;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherForecastActivity extends AppCompatActivity implements Callback<CityForecast> {
    private CityForecast mCityForecast;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cityName = getIntent().getStringExtra(WeatherFragment.EXTRA_CITY_NAME);
        NetworkUtils.loadCityForecast(this, cityName, 10);
        setContentView(R.layout.fragment_forecast);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
    }

    @Override
    public void onResponse(Call<CityForecast> call, Response<CityForecast> response) {
        mCityForecast = response.body();
        CityForecastPrefs cityForecastPrefs = new CityForecastPrefs(getApplicationContext());
        cityForecastPrefs.setCityForecast(mCityForecast);
        mViewPager.setAdapter(new ForecastFragmentPagerAdapter(getSupportFragmentManager(),
                WeatherForecastActivity.this, mCityForecast.getCity().getId()));

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFailure(Call<CityForecast> call, Throwable t) {
        Toast.makeText(this, "ooops", Toast.LENGTH_LONG).show();
    }
}
