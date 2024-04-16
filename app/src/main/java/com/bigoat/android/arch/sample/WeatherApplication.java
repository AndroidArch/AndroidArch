package com.bigoat.android.arch.sample;

import android.app.Application;

import com.bigoat.android.arch.datasource.DataSourceFactory;
import com.bigoat.android.arch.sample.data.WeatherDataSource;

import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherApplication extends Application {
    private static final String BASE_URL = "https://geoapi.qweather.com";
    private static final String KEY = "2306e46252be4312bc0174911612a302";
    @Override
    public void onCreate() {
        super.onCreate();

//        DataSourceFactory
//                .create(RemoteDataSource.class).baseUrl(BASE_URL).build();

        DataSourceFactory
                .create(WeatherDataSource.class)
                .baseUrl(BASE_URL)
                .param("key", KEY)
                .addConverter(GsonConverterFactory.create())
                .build();

    }
}
