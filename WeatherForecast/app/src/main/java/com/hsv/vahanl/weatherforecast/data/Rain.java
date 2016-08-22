package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Rain extends RealmObject {
    @SerializedName("3h")
    private double h3;
}
