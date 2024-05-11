package com.bigoat.android.arch;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel implements Logger {
    protected String tag;

    protected final BaseLiveData<String> toast = new BaseLiveData<>();

    public BaseViewModel() {
        tag = getClass().getSimpleName();
    }

    protected abstract void myCreate();
    protected void myDestroy() {}

    protected void showToast(String msg) {
        toast.value(msg);
    }
}