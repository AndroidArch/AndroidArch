package com.bigoat.android.arch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;

public abstract class BaseActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends AppCompatActivity implements Logger {
    protected String tag;
    protected Binding bind;
    protected ViewModel vm;

    private final Bundle bundle = new Bundle();
    protected Intent intent;

    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();

        // 创建视图
        bind = createBinding();
        bind.setLifecycleOwner(this);
        setContentView(bind.getRoot());

        // 创建 ViewModel
        vm = createViewModel();
        vm.toast.observe(this, this::showToast);

        injectArgs(getIntent().getExtras());

        vm.myCreate();

        myCreate(bind, vm);
    }

    public BaseActivity<Binding, ViewModel> with(@NonNull String key, @NonNull Object value) {
        Utils.putBundleValue(bundle, key, value);

        return this;
    }

    public BaseActivity<Binding, ViewModel> startActivity(@NonNull Class<? extends Activity> activity) {
        intent = new Intent(this, activity);
        return this;
    }

    public void go(boolean finish) {
        if (intent == null) {
            throw new RuntimeException("请先设置跳转Activity eg：startActivity(Class activity)");
        }

        intent.putExtras(bundle);
        startActivityForResult(intent, -1);
        if (finish) finish();
    }

    public void go() {
        go(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myResult(data);
    }

    protected void myResult(Intent result) {}

    public void go(Class<? extends Activity> activity) {
        startActivity(activity);
        go();
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
        vm.myDestroy();
    }

    @SuppressWarnings("unchecked")
    private Binding createBinding() {
        try {
            Method inflateMethod = Utils.getGenericType(getClass(), 0).getDeclaredMethod("inflate", LayoutInflater.class);
            bind = (Binding) inflateMethod.invoke(null, getLayoutInflater());
            return bind;
        } catch (Exception e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }

    private ViewModel createViewModel() {
        try {
            Class<ViewModel> viewModelClass = Utils.getGenericType(getClass(), 1);
            return new ViewModelProvider(this).get(viewModelClass);
        } catch (Exception e) {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }

    private void injectArgs(Bundle bundle) {
        Utils.injectBundle(this, bundle, AutoArg.class);
        Utils.injectBundle(vm, bundle, AutoArg.class);
    }

}
