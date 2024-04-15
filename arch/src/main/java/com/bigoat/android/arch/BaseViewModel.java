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
        try {
            return DataSourceFactory.get(Utils.getGenericType(getClass(), 0));
        } catch (Exception e) {
            loge("Please configure the correct generic parameters, eg: MyViewModel extends BaseViewModel<?>");
        }

        return null;
    }

    protected abstract void myCreate();
    protected void myDestroy() {}

    protected void showToast(String msg) {
        toast.value(msg);
    }
}