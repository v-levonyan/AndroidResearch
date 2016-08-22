package com.hsv.vahanl.weatherforecast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hsv.vahanl.weatherforecast.data.City;
import com.hsv.vahanl.weatherforecast.database.CityPrefs;
import com.hsv.vahanl.weatherforecast.network.NetworkUtils;
import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.activities.WeatherActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vahanl on 8/10/16.
 */
public class CitiesFragment extends Fragment implements Callback<City> {

    private static final String TAG = "CitiesFragment";
    RecyclerView mCityRecyclerView;
    CityPrefs mCityPrefs;
    CityAdapter mCityAdapter;
    RealmResults<City> mCities;

    private Realm mRealm;

    public static Fragment newInstance() {
        return new CitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCityPrefs = new CityPrefs(getActivity().getApplicationContext());
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cities, container, false);
        mCityRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_city_recycler_view);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRealm = Realm.getDefaultInstance();
        mCities = mRealm.where(City.class).findAll();
        updateUi();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealm.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_city, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                NetworkUtils.loadCity(CitiesFragment.this, query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    @Override
    public void onResponse(Call<City> call, Response<City> response) {
        City city = response.body();
//        mCityPrefs.setCity(city);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(city);
        mRealm.commitTransaction();
        updateUi();
    }

    @Override
    public void onFailure(Call<City> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }

    private class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mCityTextView;
        City mCity;

        public CityHolder(View itemView) {
            super(itemView);
            mCityTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bindCity(City city) {
            mCity = city;
            mCityTextView.setText(mCity.getName());
        }

        @Override
        public void onClick(View view) {
            Intent i = WeatherActivity.newIntent(getActivity(), mCity.getId());
            startActivity(i);
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {
        private RealmResults<City> mCities;

        public CityAdapter(RealmResults<City> cities) {
            mCities = cities;
        }

        @Override
        public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CityHolder(v);
        }

        @Override
        public void onBindViewHolder(CityHolder holder, int position) {
            City city = mCities.get(position);
            holder.bindCity(city);
        }

        @Override
        public int getItemCount() {

            Log.i(TAG, "getItemCount");
            if (mCities.isValid()) {
                return mCities.size();
            }
            return 0;
        }

        public void setCities(RealmResults<City> cities) {
            mCities = cities;
        }
    }

    private void updateUi() {
        if (mCities != null) {
            if (mCityAdapter == null) {
                mCityAdapter = new CityAdapter(mCities);
                mCityRecyclerView.setAdapter(mCityAdapter);
            } else {
                mCityAdapter.setCities(mCities);
            }
            mCityAdapter.notifyDataSetChanged();
        }

    }

}
