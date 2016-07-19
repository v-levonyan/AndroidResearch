package com.egs.vahanl.pointofinterest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahan on 7/18/16.
 */
public class POIFetcher {

    public String doGetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        Log.d("response: ", response.body().toString());
        return response.body().string();
    }

    public String fetchItem(String url, int itemId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl appendUrl = HttpUrl.parse(url).newBuilder()
                .addPathSegment(Integer.toString(itemId))
                .build();

        Request request = new Request.Builder().url(appendUrl).build();
        Response response = client.newCall(request).execute();
        Log.d("response: ", response.body().toString());
        return response.body().string();
    }

    public POI getItem(String jsonString) {
        Gson gson = new Gson();
        POI poi = gson.fromJson(jsonString, POI.class);
        return poi;
    }

    public List<POI> getItems(String jsonString) {
        Gson gson = new Gson();
        ListWrapper listWrapper = gson.fromJson(jsonString, ListWrapper.class);
        List<POI> pois = listWrapper.getList();
        return pois;
    }

    private class ListWrapper {
        private List<POI> list = new ArrayList<>();

        public List<POI> getList() {
            return list;
        }
    }
}
