package com.egs.vahanl.pointofinterest;

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

    public static void loadPoi(POIFragment context, int poiId) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POIApi.ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        POIApi poiApi = retrofit.create(POIApi.class);
        Call<POI> call = poiApi.getPoint(poiId);

        call.enqueue(context);
    }

    public static void loadPois(POIListFragment context) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POIApi.ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        POIListApi poiListApi = retrofit.create(POIListApi.class);
        Call<RetroPoiList> call = poiListApi.loadPois("points");
        call.enqueue(context);
    }
}
