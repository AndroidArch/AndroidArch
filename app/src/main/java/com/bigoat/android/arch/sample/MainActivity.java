package com.bigoat.android.arch.sample;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.BaseActivity;
import com.bigoat.android.arch.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding, MainViewModel> {

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewModel vm) {

        bind.setVm(vm);
    }
}