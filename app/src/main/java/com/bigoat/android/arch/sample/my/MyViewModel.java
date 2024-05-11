package com.bigoat.android.arch.sample.my;

import com.bigoat.android.arch.BaseViewModel;
import com.bigoat.android.arch.datasource.DataSourceFactory;
import com.bigoat.android.arch.sample.data.WeatherDataSource;

public abstract class MyViewModel extends BaseViewModel {
    protected WeatherDataSource dataSource = DataSourceFactory.get(WeatherDataSource.class);

    public MyViewModel() {
    }
}
