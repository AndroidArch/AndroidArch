package com.bigoat.android.arch;

import android.app.Application;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        myCreate();
    }

    public abstract void myCreate();
}
