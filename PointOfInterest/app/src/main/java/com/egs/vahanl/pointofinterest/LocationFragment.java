package com.egs.vahanl.pointofinterest;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by vahanl on 8/1/16.
 */
public class LocationFragment extends SupportMapFragment {

    private static final String TAG = "LocationFragment";

    private static final String ARG_COORD = "coord";

    private GoogleMap mMap;
    private String mGeocoord;


    public static LocationFragment newInstance(String geocoord) {
        Bundle args = new Bundle();
        args.putString(ARG_COORD, geocoord);
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGeocoord = getArguments().getString(ARG_COORD);
        Log.i(TAG, "Received coords: " + mGeocoord);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    private void updateUI() {
        if (mMap == null) {
            return;
        }
        String [] latlon = mGeocoord.split(",");
        double lat = Double.parseDouble(latlon[0]);
        double lon = Double.parseDouble(latlon[1]);
        LatLng itemPoint = new LatLng(lat, lon);

        MarkerOptions itemMarker = new MarkerOptions().position(itemPoint);
        mMap.clear();
        mMap.addMarker(itemMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(itemPoint)
                .build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mMap.animateCamera(update);
    }
}
