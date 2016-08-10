package com.hsv.vahanl.weatherforecast;

import android.app.Fragment;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vahanl on 8/1/16.
 */
public class NetworkUtils {

    private static final String ENDPOIT = "http://api.openweathermap.org";
    private static final String APP_ID = "e95e864e853fe8016042752c4c1be901";

    public static void loadCity(CitiesFragment context, String cityName) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CityApi cityApi = retrofit.create(CityApi.class);
        Call<City> call = cityApi.getCity(cityName, APP_ID);

        call.enqueue(context);
    }

}
