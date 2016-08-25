package com.hsv.vahanl.weatherforecast.activities;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.hsv.vahanl.weatherforecast.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }


    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        public static final String KEY_PREF_CONN_STATE = "pref_connection_state";
        public static final String KEY_PREF_FORECAST_DAYS = "pref_forecastDays";


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            switch (key) {
                case KEY_PREF_CONN_STATE:
                    boolean isEnabled = sharedPreferences.getBoolean(key, false);


                    WifiManager wifiManager = (WifiManager)this
                            .getContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(isEnabled);
                    break;
                case KEY_PREF_FORECAST_DAYS:
                    Preference connPref = findPreference(key);
                    connPref.setSummary(sharedPreferences.getString(key, "shat lavaaaa"));
                    break;
            }


        }
    }
}