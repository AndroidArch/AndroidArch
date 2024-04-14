package com.bigoat.android.arch.sample.data;

import com.bigoat.android.arch.datasource.DataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataSource extends DataSource {
    // curl -L -X GET --compressed 'https://geoapi.qweather.com/v2/city/lookup?location=beij&key=2306e46252be4312bc0174911612a302'

    // 城市搜索
    @GET("/v2/city/lookup")
    Call<Result<List<Location>>> cityLookup(@Query("location") String location);
}
