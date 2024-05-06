package com.bigoat.android.arch.sample.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.bigoat.android.arch.BaseViewModel;
import com.bigoat.android.arch.sample.data.WeatherDataSource;

import com.bigoat.android.arch.BaseActivity;
public abstract class MyActivity<Binding extends ViewDataBinding, ViewModel extends MyViewModel> extends BaseActivity<Binding, ViewModel> {
}

