package com.hsv.vahanl.weatherforecast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsv.vahanl.weatherforecast.data.CityForecast;
import com.hsv.vahanl.weatherforecast.data.DailyForecast;
import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.data.Weather;
import com.hsv.vahanl.weatherforecast.utilities.DBHelper;

import io.realm.Realm;

/**
 * Created by vahanl on 8/17/16.
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_CITY_ID = "cityId";

    private int mPage;
    private CityForecast mCityForecast;


    public static PageFragment newInstance(int page, long cityId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putLong(ARG_CITY_ID, cityId);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long cityId = getArguments().getLong(ARG_CITY_ID);

        mCityForecast = DBHelper.getForecastById(cityId);

        int pageCount = mCityForecast.getList().size();
        mPage = getArguments().getInt(ARG_PAGE) == pageCount ? pageCount - 1 : getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        DailyForecast dailyForecast = mCityForecast.getList().get(mPage);
        TextView cityTextView = (TextView) view.findViewById(R.id.city_textView);
        cityTextView.setText(mCityForecast.getCity().getName());
        TextView detailsTextView = (TextView) view.findViewById(R.id.details_textView);
        String details = "Humidity: " + dailyForecast.getHumidity() + " %"
                + " Pressure: " + dailyForecast.getPressure() + " hPa";
        detailsTextView.setText(details);

        TextView tempTextView = (TextView) view.findViewById(R.id.temp_textView);
        String temp = dailyForecast.getTemp().toString();
        tempTextView.setText(temp);
        TextView weatherTextView = (TextView) view.findViewById(R.id.weather_textView);
        Weather weather = dailyForecast.getWeather().get(0);
        weatherTextView.setText(weather.toString());
        String iconName = "_" + weather.getIcon();

        ImageView iconView = (ImageView) view.findViewById(R.id.weather_imageView);
        int iconId = getContext().getResources().getIdentifier(iconName, "drawable", getContext().getPackageName());
        iconView.setImageResource(iconId);


        TextView weatherDetailsTextView= (TextView) view.findViewById(R.id.weather_details_textView);
        String weatherDetails = "speed: " + dailyForecast.getSpeed() +
                "deg: " + dailyForecast.getDeg() +
                "clouds: "  + dailyForecast.getClouds() +
                "rain: " + dailyForecast.getRain();
        weatherDetailsTextView.setText(weatherDetails);

       return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }
}
