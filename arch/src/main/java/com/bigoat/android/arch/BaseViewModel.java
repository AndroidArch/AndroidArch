package com.bigoat.android.arch;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseViewModel<DataSource extends com.bigoat.android.arch.DataSource> extends ViewModel {
    protected DataSource dataSource;

    protected abstract void myCreate();

    public BaseViewModel() {
        dataSource = createDataSource();
    }

    protected DataSource createDataSource() {
        return DataSourceFactory.get(getDataSourceClass());
    }

    private Class<DataSource> getDataSourceClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType != null ? parameterizedType.getActualTypeArguments() : new Type[0];
        if (typeArguments.length == 1) {
            return (Class<DataSource>) typeArguments[0];
        } else {
            throw new RuntimeException("Please configure the correct generic parameters, eg: MyActivity extends BaseActivity<?, ?>");
        }
    }
}


/**
 * onCreate()
 * 您必须实现此回调，它会在系统创建您的 activity 时触发。您的实现应初始化 activity 的基本组件：例如，您的应用应在此处创建视图并将数据绑定到列表。最重要的是，您必须在此方法内调用 setContentView()，以定义 activity 界面的布局。
 *
 * onCreate() 完成后，下一个回调始终为 onStart()。
 *
 * onStart()
 * 当 onCreate() 退出时，activity 会进入“已开始”状态，并且对用户可见。此回调包含 activity 进入前台并进入可交互状态所需的最后准备工作。
 *
 * onResume()
 * 系统会在 activity 开始与用户互动之前调用此回调。此时，activity 位于 activity 堆栈的顶部，并捕获所有用户输入。应用的大部分核心功能都是在 onResume() 方法中实现的。
 *
 * onPause() 回调始终在 onResume() 之后调用。
 *
 * onPause()
 * 当 activity 失去焦点并进入“已暂停”状态时，系统会调用 onPause()。例如，当用户点按“返回”或“最近”按钮时，就会出现此状态。当系统为您的 activity 调用 onPause() 时，从技术上来讲，这意味着您的 activity 仍然部分可见，但大多数情况下，这表明用户正在离开该 activity，并且该 activity 很快就会进入“已停止”或“已恢复”状态。
 *
 * 如果用户希望界面更新，则处于“已暂停”状态的 activity 可以继续更新界面。此类 activity 的示例包括显示导航地图屏幕或播放媒体播放器的 activity。即使此类 activity 失去焦点，用户也会希望其界面继续更新。
 *
 * 您不应使用 onPause() 保存应用或用户数据、进行网络调用或执行数据库事务。如需了解如何保存数据，请参阅 保存和恢复 activity 状态。
 *
 * onPause() 执行完毕后，下一个回调为 onStop() 或 onResume()，具体取决于 activity 进入“已暂停”状态后发生的情况。
 *
 * onStop()
 * 当 activity 对用户不再可见时，系统会调用 onStop()。这可能是因为 activity 正在被销毁、新的 activity 正在启动，或者现有 activity 正在进入“已恢复”状态并覆盖了已停止的 activity。 在所有这些情况下，已停止的 activity 将完全不再可见。
 *
 * 系统调用的下一个回调是 onRestart()（如果 activity 重新与用户互动），或 onDestroy()（如果此 activity 完全终止）。
 *
 * onRestart()
 * 当处于“已停止”状态的 activity 即将重启时，系统会调用此回调。onRestart() 会从 activity 停止时恢复其状态。
 *
 * 此回调后跟 onStart()。
 *
 * onDestroy()
 * 系统会在销毁 activity 之前调用此回调。
 *
 * 此回调是 activity 接收的最后一个回调。 通常，实现 onDestroy() 可确保在 activity 或包含该 activity 的进程被销毁时释放 activity 的所有资源。
 *
 * 本部分只是简要地介绍了该主题。如需详细了解 activity 生命周期及其回调，请参阅 activity 生命周期。
 */