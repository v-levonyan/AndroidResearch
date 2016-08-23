package com.hsv.vahanl.weatherforecast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;
import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;

import io.realm.Realm;

/**
 * Created by vahanl on 8/19/16.
 */
public abstract class CustomFragment extends Fragment {
    private static final String ARG_CITY_ID = "argsCityId";
    private static final String TAG = "CustomFragment";

    protected CityCurrentWeatherInfo mCityCurrentWeatherInfo;
    protected Realm mRealm;

    public abstract Fragment createFragment();

    public Fragment newInstance(long cityId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CITY_ID, cityId);
        Fragment fragment = createFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long cityId = getArguments().getLong(ARG_CITY_ID);
        mRealm = Realm.getDefaultInstance();
        mCityCurrentWeatherInfo = mRealm.where(CityCurrentWeatherInfo.class).equalTo("id", cityId).findFirst();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
