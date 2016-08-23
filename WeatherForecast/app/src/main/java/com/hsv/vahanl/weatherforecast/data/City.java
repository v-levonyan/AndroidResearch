package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;

@Getter
public class City extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private Coord coord;
    private String country;
    private long population;
}