package com.hsv.vahanl.weatherforecast.data;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Temperature extends RealmObject {
    private float day;
    private float min;
    private float max;
    private float night;
    private float eve;
    private float morn;

    @Override
    public String toString() {
        return "day: " + day +
                "min: " + min +
                "max: " + max +
                "night " + night +
                "eve: " + eve +
                "morn: " + morn;
    }
}
