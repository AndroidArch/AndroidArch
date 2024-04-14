package com.bigoat.android.arch.sample.data;

import com.bigoat.android.arch.datasource.DataSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteDataSource extends DataSource {
    @GET("users/{user}/repos")
    Call<String> listRepos(@Path("user") String user);
}
