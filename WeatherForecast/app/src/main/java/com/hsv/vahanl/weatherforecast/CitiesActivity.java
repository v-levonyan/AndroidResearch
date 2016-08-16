package com.hsv.vahanl.weatherforecast;

import android.support.v4.app.Fragment;

public class CitiesActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return CitiesFragment.newInstance();
    }
}
