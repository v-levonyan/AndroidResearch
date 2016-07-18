package com.egs.vahanl.pointofinterest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIFragment extends Fragment {

    private RecyclerView mPOIRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        POIList.getInstance(getActivity());
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
}
