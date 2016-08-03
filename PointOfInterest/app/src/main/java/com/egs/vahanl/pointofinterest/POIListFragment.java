package com.egs.vahanl.pointofinterest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        updateUi();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poi, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                final List<POI> filteredPOIList = filter(mPOIs, query);
                mPOIAdapter.animateTo(filteredPOIList);
                mPOIRecyclerView.scrollToPosition(0);
                return true;
            }
        });
    }

    private List<POI> filter(List<POI> pois, String query) {
        query = query.toLowerCase();

        final List<POI> filteredPOIList = new ArrayList<>();
        for (POI poi : pois) {
            final String text = poi.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredPOIList.add(poi);
            }
        }
        return filteredPOIList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                NetworkUtils.loadPois(this);
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
        POIList.getInstance(getActivity()).addPoisToDb(mPOIs);
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

        public POI removeItem(int position) {
            final POI poi = mPOIs.remove(position);
            notifyItemRemoved(position);
            return poi;
        }

        public void addItem(int position, POI poi) {
            mPOIs.add(position, poi);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final POI poi = mPOIs.remove(fromPosition);
            mPOIs.add(toPosition, poi);
            notifyItemMoved(fromPosition, toPosition);
        }

        public void animateTo(List<POI> pois) {
            applyAndAnimateRemovals(pois);
            applyAndAnimateAdditions(pois);
            applyAndAnimateMovedItems(pois);
        }

        private void applyAndAnimateMovedItems(List<POI> newPois) {
            for (int toPosition = newPois.size() -1; toPosition >= 0; toPosition--) {
                final POI poi = newPois.get(toPosition);
                final int fromPosition = mPOIs.indexOf(poi);
                if (fromPosition >=0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }

        private void applyAndAnimateAdditions(List<POI> newPois) {
            for (int i = 0, count = newPois.size(); i < count; i++) {
                final POI poi = newPois.get(i);
                if (!mPOIs.contains(poi)) {
                    addItem(i, poi);
                }
            }
        }

        private void applyAndAnimateRemovals(List<POI> newPois) {
            for (int i = mPOIs.size() - 1; i >= 0; i-- ) {
                final POI poi = mPOIs.get(i);
                if (!newPois.contains(poi)) {
                    removeItem(i);
                }
            }
        }

        public POIAdapter(List<POI> POIs) {
            mPOIs = new ArrayList<>(POIs);
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
        mPOIs = poiList.getPOIs();

        if (mPOIs.isEmpty()) {
            NetworkUtils.loadPois(this);
        }

        if (mPOIAdapter == null) {
            mPOIAdapter = new POIAdapter(mPOIs);
            mPOIRecyclerView.setAdapter(mPOIAdapter);
        } else {
            mPOIAdapter.setPois(mPOIs);
        }
    }

}
