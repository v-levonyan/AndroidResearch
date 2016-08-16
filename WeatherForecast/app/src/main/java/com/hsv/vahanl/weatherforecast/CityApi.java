package com.hsv.vahanl.weatherforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vahanl on 8/10/16.
 */
public interface CityApi {

//    ?q=%7bcity%7d&appid=e95e864e853fe8016042752c4c1be901
    @GET("/data/2.5/weather")
    Call<City> getCity(@Query("q") String cityName,
                       @Query("appid") String appId,
                       @Query("units") String tempType);
}
