package com.hsv.vahanl.weatherforecast.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.activities.SettingsActivity;
import com.hsv.vahanl.weatherforecast.adapters.ForecastFragmentPagerAdapter;
import com.hsv.vahanl.weatherforecast.data.CityForecast;
import com.hsv.vahanl.weatherforecast.network.NetworkUtils;
import com.hsv.vahanl.weatherforecast.utilities.DBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vahanl on 8/19/16.
 */
public class WeatherForecastFragment extends CustomFragment implements Callback<CityForecast> {

    private CityForecast mCityForecast;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    public Fragment createFragment() {
        return new WeatherForecastFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSharedPrefs
                = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String frcstdaysStr = mSharedPrefs
                .getString(SettingsActivity
                        .SettingsFragment.KEY_PREF_FORECAST_DAYS, "");
        int frcstDays = Integer.valueOf(frcstdaysStr);
        NetworkUtils.loadCityForecast(this, mCityCurrentWeatherInfo.getName(), frcstDays);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forecast, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);

        return v;
    }

    @Override
    public void onResponse(Call<CityForecast> call, Response<CityForecast> response) {
        mCityForecast = response.body();
        mCityForecast.setId(mCityForecast.getCity().getId());
//TODO: set primary key
//        CityForecast cityForecast = DBHelper.getForecastById(mCityForecast.getCity().getId());
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(mCityForecast);
        mRealm.commitTransaction();

//        if (cityForecast == null) {
//            mRealm.beginTransaction();
//            mRealm.copyToRealm(mCityForecast);
//            mRealm.commitTransaction();
//        }
        mViewPager.setAdapter(new ForecastFragmentPagerAdapter(getActivity()
                .getSupportFragmentManager(),
                mCityForecast));

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onFailure(Call<CityForecast> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }
}
