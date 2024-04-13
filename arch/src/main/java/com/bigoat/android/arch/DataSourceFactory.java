package com.bigoat.android.arch;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSourceFactory {
    private static final Map<String, DataSource> STORE = new HashMap<>();

    public static <T extends DataSource> T get(Class<T> dataClass) {
        return (T) STORE.get(dataClass.getName());
    }

    public static <T extends DataSource> void create(Class<T> dataClass, String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        T dataSource = retrofit.create(dataClass);

        STORE.put(dataClass.getName(), dataSource);
    }
}
