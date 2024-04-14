package com.bigoat.android.arch.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DataSourceFactory {
    // 使用 LinkedHashMap 以保持插入顺序
    public final Map<String, DataSource> STORE = new HashMap<>();

    private DataSourceFactory() {}

    // 获取单例实例
    @SuppressWarnings("unchecked")
    public static <T extends DataSource> T get(Class<T> dataClass) {
        return (T) getInstance().STORE.get(dataClass.getName());
    }

    // 创建 DataSource 实例的构建器
    public static <T extends DataSource> Builder<T> create(Class<T> serverClass) {
        return new Builder<>(serverClass);
    }

    // 获取单例实例的方法
    private static DataSourceFactory getInstance() {
        return Holder.INSTANCE;
    }

    // 静态内部类实现单例模式
    private static class Holder {
        private static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }

    // DataSource 构建器类
    public static final class Builder<T extends DataSource> {
        final Class<T> serverClass;
        String baseUrl;
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        List<Interceptor> interceptors = new ArrayList<>();
        List<Converter.Factory> converters = new ArrayList<>();

        // 构造方法
        private Builder(Class<T> serverClass) {
            this.serverClass = serverClass;
        }

        // 设置基本 URL
        public Builder<T> baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        // 设置请求头
        public Builder<T> headers(Map<String, String> headers) {
            if (headers != null) {
                this.headers = headers;
            }
            return this;
        }

        // 添加单个请求头
        public Builder<T> header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        // 设置请求参数
        public Builder<T> params(Map<String, String> params) {
            if (params != null) {
                this.params = params;
            }
            return this;
        }

        // 添加单个请求参数
        public Builder<T> param(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        // 添加拦截器
        public Builder<T> addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        // 添加转换器
        public Builder<T> addConverter(Converter.Factory converter) {
            this.converters.add(converter);
            return this;
        }

        // 构建 DataSource 实例
        public T build() {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            // 添加请求头拦截器
            for (String key : headers.keySet()) {
                okHttpBuilder.addInterceptor(new HeaderInterceptor(key, headers.get(key)));
            }

            // 添加请求参数拦截器
            for (String key : params.keySet()) {
                okHttpBuilder.addInterceptor(new ParameterInterceptor(key, params.get(key)));
            }

            // 添加其他拦截器
            for (Interceptor interceptor : interceptors) {
                okHttpBuilder.addInterceptor(interceptor);
            }

            // 创建 Retrofit 实例
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpBuilder.build());

            // 添加转换器
            for (Converter.Factory converter : converters) {
                retrofitBuilder.addConverterFactory(converter);
            }

            Retrofit retrofit = retrofitBuilder.build();

            // 创建 DataSource 实例
            T t = retrofit.create(serverClass);

            // 将 DataSource 实例存储到 Map 中
            getInstance().STORE.put(serverClass.getName(), t);

            return t;
        }

    }

}
