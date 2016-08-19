package com.hsv.vahanl.weatherforecast.activities;

import android.support.v4.app.Fragment;

import com.hsv.vahanl.weatherforecast.fragments.CitiesFragment;

public class CitiesActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return CitiesFragment.newInstance();
    }
}
