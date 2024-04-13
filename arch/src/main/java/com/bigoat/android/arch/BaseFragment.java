package com.bigoat.android.arch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<Binding extends ViewBinding, ViewModel extends BaseViewModel> extends Fragment {

    protected Binding bind;
    protected ViewModel vm;

    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = createBinding(inflater, container);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = createViewModel();
        myCreate(bind, vm);
    }

    @SuppressWarnings("unchecked")
    private Binding createBinding(LayoutInflater inflater, ViewGroup container) {
        try {
            Method inflateMethod = getBindingClass().getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            return (Binding) inflateMethod.invoke(null, inflater, container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyFragment extends com.bigoat.android.arch.BaseFragment<?, ?>", e);
        }
    }

    private ViewModel createViewModel() {
        Class<ViewModel> viewModelClass = getViewModelClass();
        return new ViewModelProvider(requireActivity()).get(viewModelClass);
    }

    @SuppressWarnings("unchecked")
    private Class<Binding> getBindingClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType != null ? parameterizedType.getActualTypeArguments() : new Type[0];
        if (typeArguments.length == 2) {
            return (Class<Binding>) typeArguments[0];
        } else {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyFragment extends com.bigoat.android.arch.BaseFragment<?, ?>");
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
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyFragment extends com.bigoat.android.arch.BaseFragment<?, ?>");
        }
    }
}
