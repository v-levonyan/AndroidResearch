package com.egs.vahanl.pointofinterest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by vahanl on 7/20/16.
 */
public class POIActivity extends SingleFragmentActivity {
    private static final String EXTRA_POI_ID = "com.egs.vahanl.pointofinterest.poi_id";

    public static Intent newIntent(Context packageContext, int poiId) {
        Intent i = new Intent(packageContext, POIActivity.class);
        i.putExtra(EXTRA_POI_ID, poiId);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        int poiId = (int) getIntent().getSerializableExtra(EXTRA_POI_ID);
        return POIFragment.newInstance(poiId);
    }
}
