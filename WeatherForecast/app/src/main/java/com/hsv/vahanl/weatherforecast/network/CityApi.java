package com.hsv.vahanl.weatherforecast.network;

import com.hsv.vahanl.weatherforecast.data.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vahanl on 8/10/16.
 */
public interface CityApi {

    @GET("/data/2.5/weather")
    Call<City> getCity(@Query("q") String cityName,
                       @Query("appid") String appId,
                       @Query("units") String tempType);
}
