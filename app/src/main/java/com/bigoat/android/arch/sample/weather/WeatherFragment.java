package com.bigoat.android.arch.sample.weather;

import android.view.View;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.BaseFragment;
import com.bigoat.android.arch.sample.data.Location;
import com.bigoat.android.arch.sample.databinding.WeatherFragmentBinding;
import com.bigoat.android.arch.sample.main.MainActivity;

public class WeatherFragment extends BaseFragment<WeatherFragmentBinding, WeatherViewModel> {

    @Override
    protected void myCreate(@NonNull WeatherFragmentBinding bind, @NonNull WeatherViewModel vm) {

        log("myCreate %s %s", bind, vm);


        bind.hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class)
                        .with("hello", "hello")
                        .with("vm2", new Location())
                        .go();
            }
        });


        bind.setVm(vm);

    }
}