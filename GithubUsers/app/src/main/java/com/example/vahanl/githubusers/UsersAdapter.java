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

import java.util.ArrayList;
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

    public UsersAdapter(Context context) {
        mUsers = new ArrayList<>();
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

    public User removeItem(int position) {
        final User user = mUsers.remove(position);
        notifyItemRemoved(position);
        return user;
    }

    public void addItem(int position, User user) {
        mUsers.add(position, user);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final User user = mUsers.remove(fromPosition);
        mUsers.add(toPosition, user);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<User> users) {
        applyAndAnimateRemovals(users);
        applyAndAnimateAdditions(users);
        applyAndAnimateMovedItems(users);
    }

    private void applyAndAnimateMovedItems(List<User> newUsers) {
        for (int toPosition = newUsers.size() -1; toPosition >= 0; toPosition--) {
            final User user = newUsers.get(toPosition);
            final int fromPosition = mUsers.indexOf(user);
            if (fromPosition >=0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void applyAndAnimateAdditions(List<User> newUsers) {
        for (int i = 0, count = newUsers.size(); i < count; i++) {
            final User user = newUsers.get(i);
            if (!mUsers.contains(user)) {
                addItem(i, user);
            }
        }
    }

    private void applyAndAnimateRemovals(List<User> newUsers) {
        for (int i = mUsers.size() - 1; i >= 0; i-- ) {
            final User user = mUsers.get(i);
            if (!newUsers.contains(user)) {
                removeItem(i);
            }
        }
    }

    public void clear() {
        mUsers.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<User> users) {
        mUsers.addAll(users);
        notifyDataSetChanged();
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
}
