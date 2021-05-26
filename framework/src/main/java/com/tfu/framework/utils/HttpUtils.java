package com.tfu.framework.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.tfu.framework.inter.ResultCallBack;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络请求
 */
public class HttpUtils {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    //    绑定ui线程
    private static Handler handler = null;
    private volatile static HttpUtils mInstance = null;
    private OkHttpClient client;

    public HttpUtils() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         * 使用构造者模式
         * 设置连接超时
         * 读取超时
         * 写超时
         * 添加拦截器
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);
        client = builder.build();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 双重效验实现单例模式
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                mInstance = new HttpUtils();
            }
        }
        return mInstance;
    }

    /**
     * 异步get
     */
    public void get(String url, @NotNull final ResultCallBack callBack) {

        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, final IOException e) {
                onFailed(callBack, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                onSuccess(callBack, response);
            }
        });
    }


    /**
     * 异步 post json数据
     */
    public void postJson(String url, Map<String, Object> bodyParams, final ResultCallBack callBack) {
        post(url, bodyParams, null, callBack);
    }

    /**
     * 图片上传
     */
    public void postMultiPartBody(String url, MultipartBody multipartBody, final ResultCallBack callBack) {
        post(url, null, multipartBody, callBack);
    }

    private void post(String url, Map<String, Object> bodyParams, MultipartBody multipartBody, final ResultCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (bodyParams != null) {
//          RequestBody requestBody = RequestBody.create(JSON.toJSONString(bodyParams),JSON_MEDIA_TYPE);
            FormBody.Builder formBody = new FormBody.Builder();
            for (String key : bodyParams.keySet()) {
                //添加键值对
                formBody.add(key, (String) bodyParams.get(key));
            }
            FormBody requestBody = formBody.build();
            builder.post(requestBody).build();
        } else if (multipartBody != null) {
            builder.post(multipartBody);
        }
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("OkhttpRE ", "     filed  -->" + e.toString());
                onFailed(callBack, e.getMessage());

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                onSuccess(callBack, response);
            }
        });
    }


    //数据请求失败处理
    private void onFailed(final ResultCallBack callBack, final String msg) {
        if (handler != null)
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null)
                        callBack.failed(msg);
                }
            });
    }

    //数据请求成功处理
    private void onSuccess(final ResultCallBack callBack, final Response response) {
        try {
            if (response.code() == 200 && response.body() != null) {
                final String data = response.body().string();
                if (TextUtils.isEmpty(data)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.failed("数据获取为空");
                        }
                    });
                } else if (handler != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(data);
                        }
                    });
            } else {
                if (handler != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.failed(response.code() + " : " + response.message());
                        }
                    });
            }

        } catch (Throwable e) {
            callBack.failed(Objects.requireNonNull(e.getMessage()));
        }
    }


}
