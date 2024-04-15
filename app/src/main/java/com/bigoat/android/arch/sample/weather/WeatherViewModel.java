package com.bigoat.android.arch.sample.weather;

import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.BaseViewModel;
import com.bigoat.android.arch.datasource.DataSource;
import com.bigoat.android.arch.sample.data.DataCallback;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.data.WeatherDataSource;

import java.util.List;

public class WeatherViewModel extends BaseViewModel<WeatherDataSource> {

    public BaseLiveData<String> location = new BaseLiveData<>("XXXXX");

    @Override
    protected void myCreate() {
        dataSource.cityLookup("guiyang").enqueue(new DataCallback<List<Location>>() {
            @Override
            public void onSucceed(List<Location> result) {
                location.value(result.get(0).name);
            }

        });

    }
}