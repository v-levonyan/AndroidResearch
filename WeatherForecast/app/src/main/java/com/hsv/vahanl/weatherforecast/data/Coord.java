package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Coord extends RealmObject{
    @Expose private double lat;
    @Expose private double lon;
}
