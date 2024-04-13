package com.bigoat.android.arch;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 打印请求 URL 和请求方法
        System.out.println("Sending request: " + request.method() + " " + request.url());

        // 打印请求头
        System.out.println("Request headers: " + request.headers());

        // 打印请求体
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            System.out.println("Request body: " + buffer.readString(charset));
        }

        // 执行请求
        Response response = chain.proceed(request);

        // 打印响应信息
        System.out.println("Received response for " + response.request().url() + ": " + response.code());

        // 打印响应体
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String responseBodyString = responseBody.string();
            System.out.println("Response body: " + responseBodyString);
            // 由于调用了 responseBody.string() 方法，后续代码再次读取响应体时会抛出异常
            // 如果需要在日志中记录响应体，应该使用 ResponseBody.charStream() 或者 ResponseBody.byteStream() 来处理响应体
        }

        return response;
    }
}
