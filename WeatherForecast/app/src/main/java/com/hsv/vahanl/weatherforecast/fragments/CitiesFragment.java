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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;
import com.hsv.vahanl.weatherforecast.network.NetworkUtils;
import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.activities.WeatherActivity;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vahanl on 8/10/16.
 */
public class CitiesFragment extends Fragment implements Callback<CityCurrentWeatherInfo> {

    private static final String TAG = "CitiesFragment";
    RecyclerView mCityRecyclerView;
    CityAdapter mCityAdapter;
    RealmResults<CityCurrentWeatherInfo> mCities;

    private Realm mRealm;

    public static Fragment newInstance() {
        return new CitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cities, container, false);
        mCityRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_city_recycler_view);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRealm = Realm.getDefaultInstance();
        mCities = mRealm.where(CityCurrentWeatherInfo.class).findAll();
        updateUi();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealm.close();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_city, menu);
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                NetworkUtils.loadCity(CitiesFragment.this, query);
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                return false;
//            }
//        });
//    }

    @Override
    public void onResponse(Call<CityCurrentWeatherInfo> call, Response<CityCurrentWeatherInfo> response) {
        CityCurrentWeatherInfo cityCurrentWeatherInfo = response.body();
//        mCityPrefs.setCity(city);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(cityCurrentWeatherInfo);
        mRealm.commitTransaction();
        updateUi();
    }

    @Override
    public void onFailure(Call<CityCurrentWeatherInfo> call, Throwable t) {
        Toast.makeText(getActivity(), "ooops", Toast.LENGTH_LONG).show();
    }

    private class CityHolder extends RecyclerView.ViewHolder
            implements View.OnTouchListener, View.OnClickListener {
        TextView mCityTextView;
        CityCurrentWeatherInfo mCityCurrentWeatherInfo;

        public CityHolder(View itemView) {
            super(itemView);
            mCityTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
            itemView.setOnTouchListener(this);
        }

        public void bindCity(CityCurrentWeatherInfo cityCurrentWeatherInfo) {
            mCityCurrentWeatherInfo = cityCurrentWeatherInfo;
            mCityTextView.setText(mCityCurrentWeatherInfo.getName());
        }

        @Override
        public void onClick(View view) {
            Intent i = WeatherActivity.newIntent(getActivity(), mCityCurrentWeatherInfo.getId());
            startActivity(i);
        }

        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    v.setPressed(true);
                    // Start action ...
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_OUTSIDE:
                    Toast.makeText(getActivity(), "ACTION_OUTSIDE", Toast.LENGTH_SHORT).show();

                case MotionEvent.ACTION_CANCEL:
                    v.setPressed(false);
                    // Stop action ...
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }

            return true;
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {
        private RealmResults<CityCurrentWeatherInfo> mCities;

        public CityAdapter(RealmResults<CityCurrentWeatherInfo> cities) {
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
            CityCurrentWeatherInfo cityCurrentWeatherInfo = mCities.get(position);
            holder.bindCity(cityCurrentWeatherInfo);
        }

        @Override
        public int getItemCount() {

            Log.i(TAG, "getItemCount");
            if (mCities.isValid()) {
                return mCities.size();
            }
            return 0;
        }

        public void deleteItem(int position) {
            mCities.deleteFromRealm(position);
            notifyItemRemoved(position);
        }

        public void setCities(RealmResults<CityCurrentWeatherInfo> cities) {
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
