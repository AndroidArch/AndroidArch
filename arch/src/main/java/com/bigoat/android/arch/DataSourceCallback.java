package com.bigoat.android.arch;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class DataSourceCallback<T> implements retrofit2.Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponse(response.body());
        } else {
            try {
                onFailure(new Throwable(response.errorBody().string()));
            } catch (IOException e) {
                onFailure(e.getCause());
            }
        }
    }

    public abstract void onResponse(T t);

    public void onFailure(Throwable throwable) {
        Log.d("DataCallback", throwable.toString());
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onFailure(throwable);
    }
}
