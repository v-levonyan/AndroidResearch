package com.example.vahanl.customfonts.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vahanl.customfonts.R;

/**
 * Created by vahanl on 9/6/16.
 */

public class BackGroundSpinnerAdapter extends BaseAdapter {

    private Activity mContext;

    private Integer[] textBgdrawables = {R.drawable.comments_48, R.drawable.speech_bubble};


    public BackGroundSpinnerAdapter(Activity context) {
        mContext = context;
    }



    @Override
    public int getCount() {
        return textBgdrawables.length;
    }

    @Override
    public Object getItem(int position) {
        return textBgdrawables[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        ViewHolder textBackgroundViewHolder;

        if (convertView == null) {
            itemView = mContext.getLayoutInflater()
                    .inflate(R.layout.spinner_row, parent, false);
            textBackgroundViewHolder = new ViewHolder();
            textBackgroundViewHolder.imageViewTextBackground =
                    (ImageView) itemView.findViewById(R.id.spinner_image);
            itemView.setTag(textBackgroundViewHolder);
        } else {
            textBackgroundViewHolder = (ViewHolder) itemView.getTag();
        }
        textBackgroundViewHolder.imageViewTextBackground
                .setImageDrawable(mContext.getDrawable(textBgdrawables[position]));
        return itemView;
    }

    private static class ViewHolder {
        ImageView imageViewTextBackground;
    }
}
