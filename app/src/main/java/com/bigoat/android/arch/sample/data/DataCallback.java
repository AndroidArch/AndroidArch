package com.bigoat.android.arch.sample.data;

import com.bigoat.android.arch.datasource.DataSourceCallback;

public abstract class DataCallback<T> extends DataSourceCallback<Result<T>> {

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
    public abstract void onFailed(String error);
}
