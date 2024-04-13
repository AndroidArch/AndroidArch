package com.bigoat.android.arch.sample;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bigoat.android.arch.BaseFragment;
import com.bigoat.android.arch.sample.databinding.ShareViewmodelFragmentBinding;

public class ShareViewModelFragment extends BaseFragment<ShareViewmodelFragmentBinding, MainViewModel>{
    protected static final String TAG = ShareViewModelFragment.class.getSimpleName();
    @Override
    protected void myCreate(@NonNull ShareViewmodelFragmentBinding bind, @NonNull MainViewModel vm) {
        Log.d(TAG, "myCreate: " + this + " " + bind + " " + vm);

    }
}