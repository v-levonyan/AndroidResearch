package com.egs.vahanl.pointofinterest;

import android.support.v4.app.Fragment;

public class POIListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return POIListFragment.newInstance();
    }
}
