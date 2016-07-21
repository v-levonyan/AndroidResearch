package com.egs.vahanl.pointofinterest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIListFragment extends Fragment implements
        Callback<RetroPoiList> {

    private static final String TAG = "POIListFragment";

    private RecyclerView mPOIRecyclerView;
    private POIAdapter mPOIAdapter;
    private List<POI> mPOIs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    public static Fragment newInstance() {
        return new POIListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_poi, container, false);
        mPOIRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_poi_recycler_view);
        mPOIRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poi, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                Gson gson = new GsonBuilder().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(POIApi.ENDPOIT)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                POIListApi poiApi = retrofit.create(POIListApi.class);
                Call<RetroPoiList> call = poiApi.loadPois("points");
                call.enqueue(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResponse(Call<RetroPoiList> call, Response<RetroPoiList> response) {
        mPOIs = response.body().list;
        mPOIAdapter = new POIAdapter(mPOIs);
        mPOIRecyclerView.setAdapter(mPOIAdapter);
    }

    @Override
    public void onFailure(Call<RetroPoiList> call, Throwable t) {
        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

    }


    private class POIHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private POI mPOI;

        public POIHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView;
        }

        public void bindPOI(POI poi) {
            mPOI = poi;
            mTitleTextView.setText(poi.getTitle());
        }

        @Override
        public void onClick(View view) {
            Intent i = POIActivity.newIntent(getActivity(), mPOI.getId());
            startActivity(i);
        }
    }

    private class POIAdapter extends RecyclerView.Adapter<POIHolder> {
        private List<POI> mPOIs;

        public POIAdapter(List<POI> POIs) {
            mPOIs = POIs;
        }

        @Override
        public POIHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new POIHolder(v);
        }

        @Override
        public void onBindViewHolder(POIHolder holder, int position) {
            POI poi = mPOIs.get(position);
            holder.bindPOI(poi);
        }

        @Override
        public int getItemCount() {
            return mPOIs.size();
        }

        public void setPois(List<POI> pois) {
            mPOIs = pois;
        }
    }

    public void updateUi() {
        //TODO:think about
        POIList poiList = POIList.getInstance(getActivity());
        List<POI> pois = poiList.getPOIs();

        if (mPOIAdapter == null) {
            mPOIAdapter = new POIAdapter(pois);
            mPOIRecyclerView.setAdapter(mPOIAdapter);
        } else {
            mPOIAdapter.setPois(pois);
            mPOIAdapter.notifyDataSetChanged();
        }
    }

}
