package com.hsv.vahanl.weatherforecast.network;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;
import com.hsv.vahanl.weatherforecast.data.CityForecast;
import com.hsv.vahanl.weatherforecast.fragments.CitiesFragment;
import com.hsv.vahanl.weatherforecast.fragments.WeatherForecastFragment;

import io.realm.internal.Context;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vahanl on 8/1/16.
 */
public class NetworkUtils {

    private static final String ENDPOIT = "http://api.openweathermap.org";
    private static final String APP_ID = "e95e864e853fe8016042752c4c1be901";
    private static final String TYPE_CELSIUS = "metric";

    public static void loadCity(CitiesFragment context, String cityName) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CityApi cityApi = retrofit.create(CityApi.class);
        Call<CityCurrentWeatherInfo> call = cityApi.getCity(cityName, APP_ID, TYPE_CELSIUS);

        call.enqueue(context);
    }

    public static void loadCityForecast(WeatherForecastFragment context, String cityName, int count) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CityForecastApi cityForecastApi = retrofit.create(CityForecastApi.class);
        Call<CityForecast> call = cityForecastApi
                .getCityForecast(cityName, APP_ID, TYPE_CELSIUS, count);

        call.enqueue(context);
    }

    public static boolean isConnectedToNetwork(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
