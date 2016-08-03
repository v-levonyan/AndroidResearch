package com.egs.vahanl.pointofinterest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Button mCallButton;

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
        NetworkUtils.loadPoi(this, poiId);
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

        mGeoCoordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = LocationActivity.newIntent(getActivity(), mPOI.getGeocoordinates());
                startActivity(i);
            }
        });

        mDescriptionTextView = (TextView) v.findViewById(R.id.desciption_textView);
        mPhoneTextView = (TextView) v.findViewById(R.id.phone_textView);
        mCallButton = (Button) v.findViewById(R.id.button_call);
        mCallButton.setVisibility(View.GONE);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mPOI.getPhone().toLowerCase();
                phoneNumber = phoneNumber.split("[;|?]", 2)[0];
                if (isValidPhone(phoneNumber)) {
                    Uri phoneUri = Uri.parse(phoneNumber);
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(phoneUri);
                    startActivity(i);
                }
            }
        });
        return v;
    }

    private boolean isValidPhone(String phone) {
        //TODO: do more
        return phone.matches("^[a-zA-Z0-9_:+? ]*$");
    }


    @Override
    public void onResponse(Call<POI> call, Response<POI> response) {
        mPOI = response.body();
        setTextFields(mPOI);
        POIList.getInstance(getActivity()).updatePOIDb(mPOI);
        mCallButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(Call<POI> call, Throwable t) {
        int poiId = getArguments().getInt(ARG_POI_ID);
        mPOI = POIList.getInstance(getActivity()).getPoi(poiId);
        setTextFields(mPOI);
    }
}
