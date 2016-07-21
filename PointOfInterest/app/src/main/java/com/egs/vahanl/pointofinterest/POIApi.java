package com.egs.vahanl.pointofinterest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vahanl on 7/21/16.
 */
public interface POIApi {
    String ENDPOIT = "http://t21services.herokuapp.com";

    @GET("/points/{id}")
    Call<POI> getPoint(@Path("id") int id);
}
