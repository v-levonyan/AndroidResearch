package com.hsv.vahanl.weatherforecast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.adapters.ForecastFragmentPagerAdapter;
import com.hsv.vahanl.weatherforecast.data.CityForecast;
import com.hsv.vahanl.weatherforecast.database.CityForecastPrefs;
import com.hsv.vahanl.weatherforecast.network.NetworkUtils;

import io.realm.RealmQuery;
import io.realm.RealmResults;
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
        NetworkUtils.loadCityForecast(this, mCity.getName(), 10);

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
//TODO: set primary key
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(mCityForecast);
        mRealm.commitTransaction();
        mViewPager.setAdapter(new ForecastFragmentPagerAdapter(getActivity()
                .getSupportFragmentManager(),
                getActivity(),
                mCityForecast));

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onFailure(Call<CityForecast> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }
}
