package com.bigoat.android.arch;

import androidx.lifecycle.ViewModel;

import com.bigoat.android.arch.datasource.DataSourceFactory;

public abstract class BaseViewModel<DataSource extends com.bigoat.android.arch.datasource.DataSource> extends ViewModel implements Logger {
    protected String tag;
    protected DataSource dataSource;

    protected final BaseLiveData<String> toast = new BaseLiveData<>();

    public BaseViewModel() {
        tag = getClass().getSimpleName();

        dataSource = createDataSource();
    }

    protected DataSource createDataSource() {
        return DataSourceFactory.get(Utils.getGenericType(getClass(), 0));
    }

    protected abstract void myCreate();
    protected void myDestroy() {}

    protected void showToast(String msg) {
        toast.value(msg);
    }
}