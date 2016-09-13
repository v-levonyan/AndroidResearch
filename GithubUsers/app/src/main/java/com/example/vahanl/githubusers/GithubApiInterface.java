package com.example.vahanl.githubusers;

import com.example.vahanl.githubusers.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vahanl on 9/13/16.
 */

public interface GithubApiInterface {



//    @GET("users/{username}")
//    Call<User> getUser(@Path("username") String username);

    @GET("users")
    Call<List<User>> usersList();
}
