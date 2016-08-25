package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;

@Getter

public class City extends RealmObject {
    @PrimaryKey
    @Expose private long id;
    @Expose private String name;
    @Expose private Coord coord;
    @Expose private String country;
    @Expose private long population;
}