package com.hsv.vahanl.weatherforecast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hsv.vahanl.weatherforecast.R;

/**
 * Created by vahanl on 8/12/16.
 */
public class LocationFragment extends CustomFragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mGoogleMapView;

    @Override
    public Fragment createFragment() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_map, container, false);
        mGoogleMapView = (MapView) v.findViewById(R.id.viewMap);
        mGoogleMapView.onCreate(savedInstanceState);
        mGoogleMapView.onResume();
        mGoogleMapView.getMapAsync(this);
        return v;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        updateUI();
    }

    private void updateUI() {
        if (mGoogleMap == null) {
            return;
        }
        LatLng cityPoint = new LatLng(mCityCurrentWeatherInfo.getCoord().getLat(), mCityCurrentWeatherInfo.getCoord().getLon());

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
