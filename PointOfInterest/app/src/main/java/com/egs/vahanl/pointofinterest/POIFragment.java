package com.egs.vahanl.pointofinterest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIFragment extends Fragment {

    private static final String TAG = "POIFragment";

    private RecyclerView mPOIRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemTask().execute();
    }

    public static Fragment newInstance() {
        return new POIFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poi, container, false);
        mPOIRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_poi_recycler_view);
        mPOIRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private class FetchItemTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String result = new POIFetcher().doGetRequest("http://t21services.herokuapp.com/points");
                Log.i(TAG, "Fetch contents of url: " + result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch url: ", ioe);
            }
            return null;
        }
    }
}
