package com.hsv.vahanl.weatherforecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vahanl on 8/10/16.
 */
public class CitiesFragment extends Fragment implements Callback<City> {

    public static Fragment newInstance() {
        return new CitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkUtils.loadCity(this, "London");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city, container, false);
        return v;
    }

    @Override
    public void onResponse(Call<City> call, Response<City> response) {
        City city = response.body();
    }

    @Override
    public void onFailure(Call<City> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }


}
