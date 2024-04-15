package com.bigoat.android.arch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel<?>> extends AppCompatActivity {
    protected String tag;
    protected Binding bind;
    protected ViewModel vm;

    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();

        // 创建视图
        bind = createBinding();
        bind.setLifecycleOwner(this);

        // 创建 ViewModel
        vm = createViewModel();

        vm.toast.observe(this, (Observer<String>) this::showToast);

        setContentView(bind.getRoot());

        myCreate(bind, vm);

        vm.myCreate();
    }

    protected void log(String log, Object... args) {
        log(tag, Log.DEBUG, log, args);
    }

    protected void log(String tag, int level, String msg, Object... args) {
        if (msg == null) { return; }
        String log = String.format(msg, args);
        switch (level) {
            case Log.DEBUG:
                Log.d(tag, log);
                break;
            case Log.INFO:
                Log.i(tag, log);
                break;
            case Log.WARN:
                Log.w(tag, log);
                break;
            case Log.ERROR:
                Log.e(tag, log);
                break;
            case Log.ASSERT:
                Log.wtf(tag, log);
                break;
            default:
                Log.v(tag, log);
                break;
        }
    }

    protected void showToast(String msg) {
        if (msg != null && !msg.isEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vm.toast.removeObservers(this);
    }

    @SuppressWarnings("unchecked")
    private Binding createBinding() {
        try {
            Method inflateMethod = getBindingClass().getDeclaredMethod("inflate", LayoutInflater.class);
            bind = (Binding) inflateMethod.invoke(null, getLayoutInflater());
            return bind;
        } catch (Exception e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }

    private ViewModel createViewModel() {
        Class<ViewModel> viewModelClass = getViewModelClass();
        return new ViewModelProvider(this).get(viewModelClass);
    }

    @SuppressWarnings("unchecked")
    private Class<Binding> getBindingClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType != null ? parameterizedType.getActualTypeArguments() : new Type[0];
        if (typeArguments.length == 2) {
            return (Class<Binding>) typeArguments[0];
        } else {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }

    @SuppressWarnings("unchecked")
    private Class<ViewModel> getViewModelClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType != null ? parameterizedType.getActualTypeArguments() : new Type[0];
        if (typeArguments.length == 2) {
            return (Class<ViewModel>) typeArguments[1];
        } else {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }
}
