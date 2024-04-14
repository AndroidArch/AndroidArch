package com.bigoat.android.arch.datasource;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DataSourceCallback<T> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponse(response.body());
        } else {
            try {
                assert response.errorBody() != null;
                String errorBodyString = response.errorBody().string();
                onFailure(new Throwable(errorBodyString));
            } catch (Exception e) {
                onFailure(e.getCause());
            }
        }
    }

    public abstract void onResponse(T result);

    public void onFailure(Throwable throwable) {}

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
        onFailure(throwable);
    }
}
