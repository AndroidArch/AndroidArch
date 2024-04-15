package com.bigoat.android.arch;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.bigoat.android.arch.datasource.DataSourceFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseViewModel<DataSource extends com.bigoat.android.arch.datasource.DataSource> extends ViewModel {
    protected String tag;
    protected DataSource dataSource;

    protected final BaseLiveData<String> toast = new BaseLiveData<>();

    public BaseViewModel() {
        tag = getClass().getSimpleName();

        dataSource = createDataSource();
    }

    protected DataSource createDataSource() {
        return DataSourceFactory.get(getDataSourceClass());
    }

    protected abstract void myCreate();

    protected void log(String log, Object... args) {
        log(tag, Log.DEBUG, log, args);
    }

    protected void log(String tag, int level, String msg, Object... args) {
        if (msg == null) { return; }
        String log = String.format(msg, args);
        switch (level) {
            case Log.DEBUG:
                Log.d(tag, log);
                break;
            case Log.INFO:
                Log.i(tag, log);
                break;
            case Log.WARN:
                Log.w(tag, log);
                break;
            case Log.ERROR:
                Log.e(tag, log);
                break;
            case Log.ASSERT:
                Log.wtf(tag, log);
                break;
            default:
                Log.v(tag, log);
                break;
        }
    }

    protected void showToast(String msg) {
        toast.value(msg);
    }

    @SuppressWarnings("unchecked")
    private Class<DataSource> getDataSourceClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType != null ? parameterizedType.getActualTypeArguments() : new Type[0];
        if (typeArguments.length == 1) {
            return (Class<DataSource>) typeArguments[0];
        } else {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }
}