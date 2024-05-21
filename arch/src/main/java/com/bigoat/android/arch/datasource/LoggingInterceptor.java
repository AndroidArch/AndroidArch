package com.bigoat.android.arch.datasource;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {

    private static String TAG = "HTTP";

    public LoggingInterceptor() {
    }

    public LoggingInterceptor(String tag) {
        TAG = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 打印请求 URL 和请求方法
        Log.d(TAG, "Sending request: " + request.method() + " " + request.url());

        // 打印请求头
        Log.d(TAG, "Request headers: " + request.headers());

        // 打印请求体
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            String requestBodyString = buffer.readString(charset);

            try {
                JSONObject jsonObject = new JSONObject(requestBodyString);
                String requestBodyPrettyPrinted = jsonObject.toString(4); // 4 is the number of spaces to indent
                Log.d(TAG, "Request body:\n" + requestBodyPrettyPrinted);
            } catch (JSONException e) {
                Log.e(TAG, "Failed to parse request body as JSON", e);
            }
        }

        // 执行请求
        Response response = chain.proceed(request);

        // 打印响应信息
        Log.d(TAG, "Received response for " + response.request().url() + ": " + response.code());

        // 打印响应体
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            // 使用 ResponseBody.string() 方法读取响应体并打印
            String responseBodyString = responseBody.string();

            try {
                JSONObject jsonObject = new JSONObject(responseBodyString);
                String responseBodyPrettyPrinted = jsonObject.toString(4); // 4 is the number of spaces to indent
                Log.d(TAG, "Response body:\n" + responseBodyPrettyPrinted);
            } catch (JSONException e) {
                Log.e(TAG, "Failed to parse response body as JSON", e);
            }

            // 将读取后的响应体重新设置到 Response 中，因为 ResponseBody.string() 方法只能调用一次
            responseBody = ResponseBody.create(responseBody.contentType(), responseBodyString);

            // 将修改后的 Response 返回，避免抛出异常
            return response.newBuilder().body(responseBody).build();
        }

        return response;
    }
}
