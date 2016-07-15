package com.egs.vahanl.photogallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;

/**
 * Created by vahanl on 7/15/16.
 */
public class PhotoPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Uri photPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        PhotoPageFragment fragment = (PhotoPageFragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            super.onBackPressed();
            return;
        }
        WebView webView = fragment.getWebView();

        if (webView == null) {
            super.onBackPressed();
            return;
        }
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
