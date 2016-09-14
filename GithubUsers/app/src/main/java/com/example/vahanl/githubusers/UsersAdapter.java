package com.example.vahanl.githubusers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vahanl.githubusers.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vahan on 9/13/16.
 */
public class UsersAdapter extends
        RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public static final String USER_URL = "com.example.vahanl.githubusers.userUrl";
    private static final String TAG = "UsersAdapter";


    private List<User> mUsers;
    private Context mContext;

    public UsersAdapter(List<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.setUser(user);
        TextView textView = holder.mNameTextView;
        ImageView avatar = holder.mAvatar;
        textView.setText(user.getLogin());
        String url = user.getAvatar_url();
        Log.i(TAG,"avatar url: " + url);

        Picasso.with(mContext)
                .load(url)
                .resize(100, 100)
                .centerCrop()
                .into(avatar);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView mNameTextView;
        public ImageView mAvatar;
        private Context mContext;
        private User mUser;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            itemView.setOnClickListener(this);
            mContext = context;
        }

        public void setUser(User user) {
            mUser = user;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, UserDetailsActivity.class);
            intent.putExtra(USER_URL, mUser.getHtml_url());
            mContext.startActivity(intent);
        }
    }

    public Context getContext() {
        return mContext;
    }
}
