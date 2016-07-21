package com.egs.vahanl.pointofinterest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vahanl on 7/20/16.
 */
public class POIFragment extends Fragment
        implements Callback<POI> {

    private static final String TAG = "POIFragment";
    private static final String ARG_POI_ID = "poiId";

    private POI mPOI;

    private static final Map<TextView, String> mTextFields = new HashMap<>();
    private TextView mTitleTextView;
    private TextView mAddressTextView;
    private TextView mTransportTextView;
    private TextView mEmailTextView;
    private TextView mGeoCoordTextView;
    private TextView mDescriptionTextView;
    private TextView mPhoneTextView;

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
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POIApi.ENDPOIT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        POIApi poiApi = retrofit.create(POIApi.class);

        Call<POI> call = poiApi.getPoint(poiId);

        call.enqueue(this);

    }

    private void setTextFields(POI poi) {
        if (poi == null) {
            return;
        }
        mTitleTextView.setText(poi.getTitle());
        mTransportTextView.setText(poi.getTransport());
        mEmailTextView.setText(poi.getTitle());
        mGeoCoordTextView.setText(poi.getGeocoordinates());
        mDescriptionTextView.setText(poi.getDescription());
        mPhoneTextView.setText(poi.getPhone());
        mAddressTextView.setText(poi.getAddress());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poi, container, false);
        mTitleTextView = (TextView) v.findViewById(R.id.title_textView);
        mAddressTextView = (TextView) v.findViewById(R.id.address_textView);
        mTransportTextView = (TextView) v.findViewById(R.id.transport_textView);
        mEmailTextView = (TextView) v.findViewById(R.id.email_textView);
        mGeoCoordTextView = (TextView) v.findViewById(R.id.geocoordinates_textView);
        mDescriptionTextView = (TextView) v.findViewById(R.id.desciption_textView);
        mPhoneTextView = (TextView) v.findViewById(R.id.phone_textView);

        return v;
    }


    @Override
    public void onResponse(Call<POI> call, Response<POI> response) {
        mPOI = response.body();
        setTextFields(mPOI);
        POIList.getInstance(getActivity()).addPOIToDb(mPOI);
    }

    @Override
    public void onFailure(Call<POI> call, Throwable t) {
//        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        int poiId = getArguments().getInt(ARG_POI_ID);
        mPOI = POIList.getInstance(getActivity()).getPoi(poiId);
        setTextFields(mPOI);
    }
}
