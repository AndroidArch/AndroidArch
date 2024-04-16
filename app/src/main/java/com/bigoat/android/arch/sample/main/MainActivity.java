package com.bigoat.android.arch.sample.main;


import android.view.View;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.AutoArg;
import com.bigoat.android.arch.BaseActivity;
import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding, MainViewModel> {
    @AutoArg
    public String string;
    @AutoArg
    public Integer int2;


    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewModel vm) {

        log("string=%s, int2=%d", string, int2);

        bind.hello.setOnClickListener(view -> {
            startActivity(MainActivity.class)
                    .with("string", "string")
                    .with("int2", 123)
                    .go();
        });

        bind.setVm(vm);
    }

}