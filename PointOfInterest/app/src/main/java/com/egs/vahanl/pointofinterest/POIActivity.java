package com.egs.vahanl.pointofinterest;

import android.support.v4.app.Fragment;

public class POIActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return POIFragment.newInstance();
    }
}
