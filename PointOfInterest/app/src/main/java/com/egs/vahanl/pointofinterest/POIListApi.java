package com.egs.vahanl.pointofinterest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vahanl on 7/20/16.
 */
public interface POIListApi {
    @GET("http://t21services.herokuapp.com/points")
    Call<RetroPoiList> loadPois(@Query("tagged") String tags);
}
