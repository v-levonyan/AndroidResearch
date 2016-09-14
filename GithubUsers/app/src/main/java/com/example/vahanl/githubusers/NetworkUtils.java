package com.example.vahanl.githubusers;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.vahanl.githubusers.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vahanl on 8/1/16.
 */
public class NetworkUtils {

    public static String ENDPOINT = "https://api.github.com";

    public static void loadUsers(GithubUsersListActivity context) {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GithubApiInterface githubApiInterface = retrofit.create(GithubApiInterface.class);
        Call<List<User>> call = githubApiInterface.usersList();
        call.enqueue(context);
    }

    public static boolean isConnectedToNetwork(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
