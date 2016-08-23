package com.hsv.vahanl.weatherforecast.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.fragments.CitiesFragment;
import com.hsv.vahanl.weatherforecast.network.NetworkUtils;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    CitiesFragment mCitiesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        autocompleteFragment.setFilter(typeFilter);

        FragmentManager fm = getSupportFragmentManager();
        mCitiesFragment = (CitiesFragment) fm.findFragmentById(R.id.fragment_container);

        if (mCitiesFragment == null) {
            mCitiesFragment = (CitiesFragment) CitiesFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container, mCitiesFragment).commit();
        }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                NetworkUtils.loadCity(mCitiesFragment, place.getName().toString());
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}