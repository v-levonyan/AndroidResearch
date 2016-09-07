package com.example.vahanl.customfonts.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vahanl.customfonts.R;

/**
 * Created by vahanl on 9/7/16.
 */

public class TextAlignSpinnerAdapter extends BaseAdapter {

    private Activity mContext;

    private Integer[] textAlignIcons =
            {R.drawable.left_align, R.drawable.center_align, R.drawable.right_align};

    public TextAlignSpinnerAdapter(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return textAlignIcons.length;
    }

    @Override
    public Object getItem(int position) {
        return textAlignIcons[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder textAlignViewHolder;

        if (convertView == null) {
            itemView = mContext.getLayoutInflater()
                    .inflate(R.layout.spinner_row, parent, false);
            textAlignViewHolder = new ViewHolder();
            textAlignViewHolder.imageViewTextAlign =
                    (ImageView) itemView.findViewById(R.id.spinner_image);
            itemView.setTag(textAlignViewHolder);
        } else {
            textAlignViewHolder = (ViewHolder) itemView.getTag();
        }
        textAlignViewHolder.imageViewTextAlign
                .setImageDrawable(mContext.getDrawable(textAlignIcons[position]));
        return itemView;
    }

    private static class ViewHolder {
        ImageView imageViewTextAlign;
    }
}
