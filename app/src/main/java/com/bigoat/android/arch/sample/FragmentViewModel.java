package com.bigoat.android.arch.sample;

import com.bigoat.android.arch.BaseLiveData;
import com.bigoat.android.arch.BaseViewModel;

public class FragmentViewModel extends BaseViewModel {
    public BaseLiveData<String>  name = new BaseLiveData<>("姓名");
}
