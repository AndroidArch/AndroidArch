package com.bigoat.android.arch.sample.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.BaseActivity;
import com.bigoat.android.arch.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding, MainViewModel> {
    protected static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewModel vm) {

        log("myCreate");
        showToast("myCreate");

        bind.setVm(vm);
    }
}