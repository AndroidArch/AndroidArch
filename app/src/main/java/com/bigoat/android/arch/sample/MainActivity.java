package com.bigoat.android.arch.sample;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.BaseActivity;
import com.bigoat.android.arch.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding, MainViewModel> {
    protected static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewModel vm) {
        Log.d(TAG, "myCreate: " + this + " " + bind + " " + vm);

        bind.name.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FragmentActivity.class)));


        bind.setVm(vm);
    }
}