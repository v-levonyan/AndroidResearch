package com.hsv.vahanl.weatherforecast.utilities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hsv.vahanl.weatherforecast.activities.SettingsActivity;

import io.realm.internal.Context;

/**
 * Created by vahanl on 8/29/16.
 */
public class PrefUtils {


    public static void setConnection(Activity activity, boolean isConnected) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(SettingsActivity.SettingsFragment.KEY_PREF_CONN_STATE, isConnected);
        editor.commit();
    }
}
