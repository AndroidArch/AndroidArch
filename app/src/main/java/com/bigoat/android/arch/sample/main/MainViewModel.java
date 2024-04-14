package com.bigoat.android.arch.sample.main;

import androidx.lifecycle.Observer;

import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.BaseViewModel;
import com.bigoat.android.arch.datasource.DataSourceCallback;
import com.bigoat.android.arch.sample.data.DataCallback;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.data.Result;
import com.bigoat.android.arch.sample.data.WeatherDataSource;

import java.util.List;

public class MainViewModel extends BaseViewModel<WeatherDataSource> {
    public BaseLiveData<String> name = new BaseLiveData<>("姓名");
    public BaseLiveData<String> city = new BaseLiveData<>();

    @Override
    protected void myCreate() {
        city.observeForever(this::cityLookup);


    }

    private void cityLookup(String location) {
        dataSource.cityLookup(location).enqueue(new DataCallback<List<Location>>() {
            @Override
            public void onSucceed(List<Location> result) {
                name.value(result.get(0).toString());
            }

            @Override
            public void onFailed(String error) {
                name.value(error);
            }
        });
    }
}
