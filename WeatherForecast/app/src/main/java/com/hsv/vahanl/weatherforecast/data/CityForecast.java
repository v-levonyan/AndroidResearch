package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityForecast extends RealmObject {

    @PrimaryKey
    long id;
    @Expose private City city;
    @Expose private RealmList<DailyForecast> list;


}
