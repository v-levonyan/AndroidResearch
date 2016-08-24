package com.hsv.vahanl.weatherforecast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hsv.vahanl.weatherforecast.R;
import com.hsv.vahanl.weatherforecast.activities.WeatherActivity;
import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    @Override
    public void onResponse(Call<CityCurrentWeatherInfo> call, Response<CityCurrentWeatherInfo> response) {
        CityCurrentWeatherInfo cityCurrentWeatherInfo = response.body();
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
            implements View.OnClickListener, View.OnLongClickListener {
        TextView mCityTextView;
        CityCurrentWeatherInfo mCityCurrentWeatherInfo;

        public CityHolder(View itemView) {
            super(itemView);
            mCityTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
        public boolean onLongClick(View v) {
            mCityAdapter.deleteItem(getAdapterPosition());
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
            if (mCities.isValid()) {
                return mCities.size();
            }
            return 0;
        }

        public void deleteItem(int position) {
            mRealm.beginTransaction();
            mCities.deleteFromRealm(position);
            mRealm.commitTransaction();
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
