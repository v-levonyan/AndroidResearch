package com.hsv.vahanl.weatherforecast.data;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Temperature extends RealmObject {
    @Expose private float day;
    @Expose private float min;
    @Expose private float max;
    @Expose private float night;
    @Expose private float eve;
    @Expose private float morn;

    @Override
    public String toString() {
        return "day: " + day + "\n" +
                "min: " + min + "\n" +
                "max: " + max + "\n" +
                "night " + night + "\n" +
                "eve: " + eve + "\n" +
                "morn: " + morn;
    }
}
