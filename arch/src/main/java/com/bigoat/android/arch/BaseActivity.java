package com.bigoat.android.arch;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends AppCompatActivity {
    protected Binding bind;
    protected ViewModel vm;

    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建视图
        bind = createBinding();
        bind.setLifecycleOwner(this);

        // 创建 ViewModel
        vm = createViewModel();
        vm.myCreate();

        setContentView(bind.getRoot());

        myCreate(bind, vm);

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
