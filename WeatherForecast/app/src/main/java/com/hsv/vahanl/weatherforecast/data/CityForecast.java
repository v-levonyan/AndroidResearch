package com.hsv.vahanl.weatherforecast.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CityForecast extends RealmObject {
    private City city;
    private RealmList<DailyForecast> list;


}
