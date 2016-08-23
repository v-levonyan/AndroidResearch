package com.hsv.vahanl.weatherforecast.network;

import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;
import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vahanl on 8/10/16.
 */
public interface CityApi {

    @GET("/data/2.5/weather")
    Call<CityCurrentWeatherInfo> getCity(@Query("q") String cityName,
                                         @Query("appid") String appId,
                                         @Query("units") String tempType);
}
