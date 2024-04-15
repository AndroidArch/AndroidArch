package com.bigoat.android.arch.sample.data;

import android.util.Log;

import com.bigoat.android.arch.datasource.DataSourceCallback;

public abstract class DataCallback<T> extends DataSourceCallback<Result<T>> {
    private static final String TAG = DataCallback.class.getSimpleName();

    @Override
    public void onResponse(Result<T> result) {
        if (result.code == 200) {
            onSucceed(result.location);
        } else {
            onFailed("请求失败！");
        }
    }

    // 成功了
    public abstract void onSucceed(T result);

    // 失败了
    public void onFailed(String error) {
        Log.d(TAG, error);
    }
}
