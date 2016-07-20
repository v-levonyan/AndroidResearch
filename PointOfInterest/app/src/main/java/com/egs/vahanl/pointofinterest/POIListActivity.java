package com.egs.vahanl.pointofinterest;

import android.support.v4.app.Fragment;
import android.view.Menu;

public class POIListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return POIListFragment.newInstance();
    }


}
