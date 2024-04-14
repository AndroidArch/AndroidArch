package com.bigoat.android.arch.datasource;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParameterInterceptor implements Interceptor {

    private final String name;
    private final String value;

    public ParameterInterceptor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        // 添加公共请求参数
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(name, value)
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
