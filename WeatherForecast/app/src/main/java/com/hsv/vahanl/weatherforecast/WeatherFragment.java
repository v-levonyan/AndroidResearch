package com.hsv.vahanl.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by vahanl on 8/16/16.
 */
public class WeatherFragment extends Fragment {

    private static final String ARG_CITY_ID = "argsCityId";
    public static final String EXTRA_CITY_NAME = "com.hsv.vahanl.weatherforecast.cityName";

    TextView mCityField;
    TextView mUpdatedField;
    TextView mDetailsField;
    TextView mCurrentTemperatureField;
    ImageView mWeatherIcon;
    City mCity;

    public static WeatherFragment newInstance(long cityId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CITY_ID, cityId);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long cityId = getArguments().getLong(ARG_CITY_ID);
        CityPrefs cityPrefs = new CityPrefs(getActivity().getApplicationContext());
        mCity = cityPrefs.getCity(cityId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        mCityField = (TextView) v.findViewById(R.id.city_field);
        mCityField.setText(mCity.getName());
        mCityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), WeatherForecastActivity.class);
                i.putExtra(EXTRA_CITY_NAME, mCity.getName());
                startActivity(i);
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
