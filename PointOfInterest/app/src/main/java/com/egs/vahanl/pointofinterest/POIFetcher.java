package com.egs.vahanl.pointofinterest;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
}
