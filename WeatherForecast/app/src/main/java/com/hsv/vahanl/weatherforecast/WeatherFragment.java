package com.hsv.vahanl.weatherforecast;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by vahanl on 8/12/16.
 */
public class WeatherFragment extends SupportMapFragment {

    private static final String ARG_CITY_ID = "argsCityId";
    private GoogleMap mGoogleMap;
    private City mCity;

    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    public static WeatherFragment newInstance(long cityId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CITY_ID, cityId);
        WeatherFragment fragment = new WeatherFragment();
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

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = layoutInflater.inflate(R.layout.fragment_weather, viewGroup, false);
        return v;
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
