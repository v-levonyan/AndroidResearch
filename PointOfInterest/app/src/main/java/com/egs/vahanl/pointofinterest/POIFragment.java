package com.egs.vahanl.pointofinterest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vahanl on 7/20/16.
 */
public class POIFragment extends Fragment {
    private static final String ARG_POI_ID = "poiId";
    private TextView mTitleTextView;

    private POI mPOI;

    public static Fragment newInstance(int poiId) {
        Bundle args = new Bundle();
        args.putInt(ARG_POI_ID, poiId);
        POIFragment fragment = new POIFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int poiId = getArguments().getInt(ARG_POI_ID);
        mPOI = POIList.getInstance(getActivity()).getPoiById(poiId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poi, container, false);
        mTitleTextView = (TextView) v.findViewById(R.id.title_textView);
        mTitleTextView.setText(mPOI.getTitle());

        return v;
    }
}
