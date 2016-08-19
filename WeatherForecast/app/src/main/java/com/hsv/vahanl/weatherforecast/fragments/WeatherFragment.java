package com.hsv.vahanl.weatherforecast.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsv.vahanl.weatherforecast.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by vahanl on 8/16/16.
 */
public class WeatherFragment extends CustomFragment {

    public static final String EXTRA_CITY_NAME = "com.hsv.vahanl.weatherforecast.cityName";

    TextView mCityField;
    TextView mUpdatedField;
    TextView mDetailsField;
    TextView mCurrentTemperatureField;
    ImageView mWeatherIcon;

    @Override
    public Fragment createFragment() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        mCityField = (TextView) v.findViewById(R.id.city_field);
        mCityField.setText(mCity.getName());
        mCityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= fm.beginTransaction();
                Fragment weatherForecastFragment =
                        new WeatherForecastFragment().newInstance(mCity.getId());
                transaction.replace(R.id.weather_fragment_container, weatherForecastFragment);
                transaction.commit();

            }
        });
        mUpdatedField = (TextView) v.findViewById(R.id.updated_field);
        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(mCity.getDt() * 1000));
        mUpdatedField.setText(updatedOn);
        mDetailsField = (TextView) v.findViewById(R.id.details_field);
        String description = mCity.getWeather().get(0).getDescription();
        String humidity = String.valueOf(mCity.getMain().getHumidity());
        String pressure = String.valueOf(mCity.getMain().getPressure());
        mDetailsField.setText(description + "/n"
                + "Humidity: " + humidity + " %"
                + "Pressure: " + pressure + " hPa");
        mCurrentTemperatureField = (TextView) v.findViewById(R.id.current_temperature_field);
        String temperature = String.format("%.2f", mCity.getMain().getTemp()) + " C";
        mCurrentTemperatureField.setText(temperature);
        mWeatherIcon = (ImageView) v.findViewById(R.id.weather_icon);

        String iconName ="_" + mCity.getWeather().get(0).getIcon();

        Context context = mWeatherIcon.getContext();
        int iconId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        mWeatherIcon.setImageResource(iconId);

//        Drawable iconDrawable = AppUtils.resizeImage(context, iconId, 500, 500);
//        mWeatherIcon.setImageDrawable(iconDrawable);

        return v;
    }


}
