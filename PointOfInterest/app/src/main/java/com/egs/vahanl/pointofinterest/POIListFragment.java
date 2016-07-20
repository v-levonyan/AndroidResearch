package com.egs.vahanl.pointofinterest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.IOException;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
//        new FetchItemTask().execute();
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
                //TODO: use retrofit

                Gson gson = new GsonBuilder().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://t21services.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                POIApi poiApi = retrofit.create(POIApi.class);

                Call<RetroPoiList> call = poiApi.loadPois("points");

                call.enqueue(this);



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    public void onResponse(Call<RetroPoiList> call, Response<RetroPoiList> response) {
        mPOIAdapter = new POIAdapter(response.body().list);
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
    }

    private class FetchItemTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String urlString = "http://t21services.herokuapp.com/points";
                POIFetcher poiFetcher = new POIFetcher();
                String result = poiFetcher.doGetRequest(urlString);
                List<POI> pois = poiFetcher.getItems(result);
                POIList.getInstance(getActivity()).setPOIs(pois);

//                String result = poiFetcher.fetchItem(urlString, 1);
//                POI poi = poiFetcher.getItem(result);
                Log.i(TAG, "Fetch contents of url: maagic!!!" + pois.get(1).getGeocoordinates());
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch url: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
        }
    }

    private void updateUI() {
        POIList poiList = POIList.getInstance(getActivity());
        List<POI> pois= poiList.getPOIs();
        mPOIAdapter = new POIAdapter(pois);
        mPOIRecyclerView.setAdapter(mPOIAdapter);
    }


}
