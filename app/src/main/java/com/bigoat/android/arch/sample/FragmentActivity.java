package com.bigoat.android.arch.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bigoat.android.arch.BaseActivity;
import com.bigoat.android.arch.sample.databinding.FragmentActivityBinding;

public class FragmentActivity extends BaseActivity<FragmentActivityBinding, FragmentViewModel> {
    protected static final String TAG = FragmentActivity.class.getSimpleName();

    @Override
    protected void myCreate(@NonNull FragmentActivityBinding bind, @NonNull FragmentViewModel vm) {
        Log.d(TAG, "myCreate: " + this + " " + bind + " " + vm);

        bind.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FragmentActivity.this, MainActivity.class));
            }
        });
    }

}