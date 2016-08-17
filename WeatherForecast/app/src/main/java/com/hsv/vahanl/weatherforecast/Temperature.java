package com.hsv.vahanl.weatherforecast;

/**
 * Created by vahanl on 8/17/16.
 */
public class Temperature {
    private float day;
    private float min;
    private float max;
    private float night;
    private float eve;
    private float morn;

    public float getDay() {
        return day;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float getNight() {
        return night;
    }

    public float getEve() {
        return eve;
    }

    public float getMorn() {
        return morn;
    }

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
