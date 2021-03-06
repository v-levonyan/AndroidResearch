package com.example.vahanl.githubusers;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vahanl.githubusers.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUsersListActivity extends AppCompatActivity
        implements Callback<List<User>> {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private UsersAdapter mAdapter;
    private List<User> mUsers;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mNetworkInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_users_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNetworkInfoTextView = (TextView) findViewById(R.id.network_info);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.ultimate_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setRefreshing(true);
        NetworkUtils.loadUsers(this);

    }

    void refreshItems() {
        NetworkUtils.loadUsers(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                final List<User> filteredUserList = filter(mUsers, query);
                mAdapter.animateTo(filteredUserList);
                mRecyclerView.scrollToPosition(0);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        mNetworkInfoTextView.setVisibility(View.GONE);
        mUsers = response.body();

        updateUi();
        mAdapter.clear();
        mAdapter.addAll(mUsers);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void updateUi() {
        if (mAdapter == null) {
            mAdapter = new UsersAdapter(this);
       }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        updateUi();
        mSwipeRefreshLayout.setRefreshing(false);
        mNetworkInfoTextView.setVisibility(View.VISIBLE);
    }

    private List<User> filter(List<User> users, String query) {
        query = query.toLowerCase();

        final List<User> filteredUserList = new ArrayList<>();
        for (User user : users) {
            final String text = user.getLogin().toLowerCase();
            if (text.contains(query)) {
                filteredUserList.add(user);
            }
        }
        return filteredUserList;
    }
}
