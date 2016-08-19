package com.hsv.vahanl.weatherforecast.network;

import com.hsv.vahanl.weatherforecast.data.CityForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vahanl on 8/17/16.
 */
public interface CityForecastApi {

    @GET("/data/2.5/forecast/daily")
    Call<CityForecast> getCityForecast(
            @Query("q") String cityName,
            @Query("appid") String appId,
            @Query("units") String tempType,
            @Query("cnt") int count);
}
