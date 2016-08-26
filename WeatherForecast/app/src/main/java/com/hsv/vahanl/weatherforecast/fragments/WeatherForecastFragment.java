package com.hsv.vahanl.weatherforecast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

    private static final String TAG = "WeatherForecastFragment";
    private CityForecast mCityForecast;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private SharedPreferences mSharedPrefs;

    @Override
    public Fragment createFragment() {
        return new WeatherForecastFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int frcstDays = getFrcstDaysFromPrefs();
        if (isOnline()) {
            Log.i(TAG, "Online mode enabled.....");
            NetworkUtils.loadCityForecast(this, mCityCurrentWeatherInfo.getName(), frcstDays);
        } else {
            Log.i(TAG, "Offline mode enabled.....");
            mCityForecast = DBHelper.getForecastById(mCityCurrentWeatherInfo.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forecast, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);

        if (!isOnline()) {
            boolean isupdated = updateUI();
            if (!isupdated) {
                Toast.makeText(getActivity(),
                        "no offline data.Please enable connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        return v;
    }

    @Override
    public void onResponse(Call<CityForecast> call, Response<CityForecast> response) {
        mCityForecast = response.body();
        mCityForecast.setId(mCityForecast.getCity().getId());
        DBHelper.copyOrUpdate(mCityForecast);
        updateUI();
    }

    @Override
    public void onFailure(Call<CityForecast> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }

    private boolean updateUI() {
        mCityForecast = DBHelper.getForecastById(mCityCurrentWeatherInfo.getId());
        if (mCityForecast == null) {
            return false;
        }
        Log.i(TAG, "City: " + mCityForecast.getCity().getName() +
                "CityId: " + mCityForecast.getId());
        mViewPager.setAdapter(new ForecastFragmentPagerAdapter(getActivity()
                .getSupportFragmentManager(),
                mCityForecast));

        mTabLayout.setupWithViewPager(mViewPager);
        return true;
    }


    private int getFrcstDaysFromPrefs() {
        String frcstdaysStr = mSharedPrefs
                .getString(SettingsActivity
                        .SettingsFragment.KEY_PREF_FORECAST_DAYS, null);
        int frcstDays = frcstdaysStr == null ? 10 : Integer.valueOf(frcstdaysStr);
        return frcstDays;
    }

    private boolean isOnline() {
        return mSharedPrefs.getBoolean(SettingsActivity
                .SettingsFragment.KEY_PREF_CONN_STATE, false);

    }

}
