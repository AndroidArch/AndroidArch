package com.bigoat.android.arch.sample.main;

import com.bigoat.android.arch.AutoArg;
import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.sample.data.DataCallback;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.my.MyViewModel;

import java.util.List;

public class MainViewModel extends MyViewModel {
    public BaseLiveData<String> name = new BaseLiveData<>("姓名");
    public BaseLiveData<String> city = new BaseLiveData<>();

    @AutoArg
    public String string;
    @AutoArg
    public int int2;

    @AutoArg
    public Location vmw;

    @AutoArg
    public String hello;

    @Override
    protected void myCreate() {
        log("MainViewModel dataSource=%b", dataSource==null);

        log("string=%s, int2=%d ", string, int2);
        log("hello=%s, vm2=", hello);

        city.observeForever(this::cityLookup);
    }

    private void cityLookup(String location) {
        log("dataSource=%b", dataSource==null);
        log("location=%s", location);

        if (dataSource == null) {
            name.value("dataSource is null");
            return;
        }
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
