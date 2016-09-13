package com.example.vahanl.githubusers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.widget.Toast;

import com.example.vahanl.githubusers.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUsersListActivity extends AppCompatActivity
        implements Callback<List<User>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_users_list);
        NetworkUtils.loadUsers(this);
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        List<User> users = response.body();
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Toast.makeText(this, "Failed to load users", Toast.LENGTH_LONG);
    }
}
