package com.bigoat.android.arch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;

public abstract class BaseFragment<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends Fragment implements Logger {
    protected String tag;
    protected Binding bind;
    protected ViewModel vm;

    private final Bundle bundle = new Bundle();
    protected Intent intent;

    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tag = getClass().getSimpleName();

        // 创建视图
        bind = createBinding(inflater, container);
        bind.setLifecycleOwner(shareViewMode()?requireActivity():this);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 创建 ViewModel
        vm = createViewModel();
        vm.toast.observe(getViewLifecycleOwner(), this::showToast);

        injectArgs(getArguments());

        vm.myCreate();

        myCreate(bind, vm);
    }

    public BaseFragment<Binding, ViewModel> with(@NonNull String key, @NonNull Object value) {
        Utils.putBundleValue(bundle, key, value);

        return this;
    }

    public BaseFragment<Binding, ViewModel> startActivity(@NonNull Class<? extends Activity> activity) {
        intent = new Intent(requireContext(), activity);
        return this;
    }

    public void go() {
        if (intent == null) {
            throw new RuntimeException("请先设置跳转Activity eg：startActivity(Class activity)");
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void showToast(String msg) {
        if (msg != null && !msg.isEmpty()) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unchecked")
    private Binding createBinding(LayoutInflater inflater, ViewGroup container) {
        try {
            Method inflateMethod = Utils.getGenericType(getClass(), 0).getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            bind = (Binding) inflateMethod.invoke(null, inflater, container, false);
            return bind;
        } catch (Exception e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyFragment extends BaseFragment<?, ?>");
        }
    }

    private ViewModel createViewModel() {
        try {
            Class<ViewModel> viewModelClass = Utils.getGenericType(getClass(), 1);
            return new ViewModelProvider(shareViewMode()?requireActivity():this).get(viewModelClass);
        } catch (Exception e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyFragment extends BaseFragment<?, ?>");
        }
    }

    private void injectArgs(Bundle bundle) {
        Utils.injectBundle(this, bundle, AutoArg.class);
        Utils.injectBundle(vm, bundle, AutoArg.class);
    }

    protected boolean shareViewMode() {
        return false;
    }
}
