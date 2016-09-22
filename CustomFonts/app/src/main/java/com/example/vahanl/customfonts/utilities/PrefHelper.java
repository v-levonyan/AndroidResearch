package com.example.vahanl.customfonts.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vahanl on 9/5/16.
 */
public class PrefHelper {

    private static PrefHelper ourInstance;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public static String KEY_PREF_FONT = "font";

    public static PrefHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PrefHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    private PrefHelper(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public void puString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();

    }
}
