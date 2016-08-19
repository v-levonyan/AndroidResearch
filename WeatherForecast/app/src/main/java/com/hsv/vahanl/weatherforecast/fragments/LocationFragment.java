package com.hsv.vahanl.weatherforecast.fragments;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hsv.vahanl.weatherforecast.data.City;
import com.hsv.vahanl.weatherforecast.database.CityPrefs;

/**
 * Created by vahanl on 8/12/16.
 */
public class LocationFragment extends SupportMapFragment {

    private static final String ARG_CITY_ID = "argsCityId";
    private GoogleMap mGoogleMap;
    private City mCity;



    public static LocationFragment newInstance(long cityId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CITY_ID, cityId);
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        long cityId = getArguments().getLong(ARG_CITY_ID);
        CityPrefs cityPrefs = new CityPrefs(getActivity().getApplicationContext());
        mCity = cityPrefs.getCity(cityId);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateUI();
            }
        });
    }



    private void updateUI() {
        if (mGoogleMap == null) {
            return;
        }
        LatLng cityPoint = new LatLng(mCity.getCoord().getLat(), mCity.getCoord().getLon());

        MarkerOptions cityMarker = new MarkerOptions().position(cityPoint);
        mGoogleMap.clear();
        mGoogleMap.addMarker(cityMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(cityPoint)
                .build();
        CameraUpdate update = CameraUpdateFactory.newLatLng(cityPoint);
        mGoogleMap.animateCamera(update);
    }
}
