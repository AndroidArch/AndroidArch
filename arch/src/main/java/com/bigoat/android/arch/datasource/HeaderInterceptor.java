package com.bigoat.android.arch.datasource;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private final String name;
    private final String value;

    public HeaderInterceptor(String name, String value) {
        this.name = name;
        this.value = value;
    }
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header(name, value);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
