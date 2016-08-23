package com.hsv.vahanl.weatherforecast.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hsv.vahanl.weatherforecast.data.CityForecast;
import com.hsv.vahanl.weatherforecast.data.DailyForecast;
import com.hsv.vahanl.weatherforecast.fragments.PageFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vahanl on 8/17/16.
 */
public class ForecastFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private List<String> tabTitles = new ArrayList<>();
    private Context mContext;
    private long mCityId;
    CityForecast mCityForecast;

    public ForecastFragmentPagerAdapter(FragmentManager fm,
                                        Context context,
                                        CityForecast cityForecast) {
        super(fm);
        mContext = context;
        mCityForecast = cityForecast;
        setTitles();
    }

    @Override
    public int getCount() {
        return mCityForecast.getList().size();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position, mCityForecast.getCity().getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    private void setTitles() {
        List<DailyForecast> dailyForecasts = mCityForecast.getList();
        DateFormat df = DateFormat.getDateTimeInstance();
        for (DailyForecast dailyForecast : dailyForecasts) {
            long date = dailyForecast.getDt();
            String updatedOn = df.format(new Date(date * 1000));
            tabTitles.add(updatedOn);
        }
    }
}
