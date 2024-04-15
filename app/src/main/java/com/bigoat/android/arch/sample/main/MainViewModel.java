package com.bigoat.android.arch.sample.main;

import androidx.lifecycle.Observer;

import com.bigoat.android.arch.AutoArg;
import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.BaseViewModel;
import com.bigoat.android.arch.datasource.DataSourceCallback;
import com.bigoat.android.arch.sample.data.DataCallback;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.data.Result;
import com.bigoat.android.arch.sample.data.WeatherDataSource;
import com.bigoat.android.arch.sample.weather.WeatherViewModel;

import java.util.List;

public class MainViewModel extends BaseViewModel<WeatherDataSource> {
    public BaseLiveData<String> name = new BaseLiveData<>("姓名");
    public BaseLiveData<String> city = new BaseLiveData<>();

    @AutoArg
    public String string;
    @AutoArg
    public int int2;

    @AutoArg
    public BaseLiveData<String> obj;

    @AutoArg
    public String hello;

    @AutoArg
    public Location vm2;

    @Override
    protected void myCreate() {
        log("string=%s, int2=%d, obj=%s", string, int2, obj);
        log("hello=%s, vm2=%s", hello, vm2);

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
