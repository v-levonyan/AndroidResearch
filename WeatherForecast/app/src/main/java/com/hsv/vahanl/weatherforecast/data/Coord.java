package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Coord extends RealmObject{
    private double lat;
    private double lon;
}
