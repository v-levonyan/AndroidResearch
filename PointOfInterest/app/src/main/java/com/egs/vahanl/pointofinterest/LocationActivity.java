package com.egs.vahanl.pointofinterest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by vahanl on 8/1/16.
 */
public class LocationActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;

    private static final String EXTRA_GEOCOORDINATES = "com.egs.vahanl.pointofinterest.geocoord";

    public static Intent newIntent(Context packageContext, String geocoordinates) {
        Intent i = new Intent(packageContext, LocationActivity.class);
        i.putExtra(EXTRA_GEOCOORDINATES, geocoordinates);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        String geocoord = getIntent().getStringExtra(EXTRA_GEOCOORDINATES);
        return LocationFragment.newInstance(geocoord);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();

        int errorCode = availability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {

            Dialog errorDialog = availability.getErrorDialog(this, errorCode, REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
            errorDialog.show();
        }

    }


}
