package com.tfu.framework.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * created by weiqishan on 2020/10/9
 */
public final class HttpUtil {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    protected static OkHttpClient client = null;
    //绑定ui线程
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static HttpUtil httpUtil = null;
    //这里是用的创建引擎接口的token
//    729173f61b0811ea889d02550a28003b
    String token = "";

    private HttpUtil() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);//读取超时
        builder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
        builder.writeTimeout(10, TimeUnit.SECONDS);//写入超时
        builder.addInterceptor(loggingInterceptor);
        client = builder.build();
    }


    public static HttpUtil getHttpInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    public static OkHttpClient getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return client;
    }


    /**
     * 异步get
     */
    public void get(String url, final INetCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("token", token);
        final Request request = builder.get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                onFailed(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccess(callBack, response);
            }
        });
    }


    /**
     * 异步 post json数据
     */
    public void postJson(String url, Map<String, Object> bodyParams, final INetCallBack callBack) {
        post(url, bodyParams, null, callBack);
    }

    /**
     * 图片上传
     */
    public void postMultiPartBody(String url, MultipartBody multipartBody, final INetCallBack callBack) {
        post(url, null, multipartBody, callBack);
    }

    private void post(String url, Map<String, Object> bodyParams, MultipartBody multipartBody, final INetCallBack callBack) {

        Request.Builder builder = new Request.Builder();
        if (!TextUtils.isEmpty(token))
            builder.addHeader("token", token);
        builder.url(url);
        if (bodyParams != null) {
            RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, JSON.toJSONString(bodyParams));
            builder.post(requestBody).build();
        } else if (multipartBody != null) {
            builder.post(multipartBody);
        }
        Request request = builder.build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onFailed(callBack, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccess(callBack, response);
            }
        });
    }


    //数据请求失败处理
    private void onFailed(final INetCallBack callBack, final String msg) {
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
    private void onSuccess(final INetCallBack callBack, final Response response) {
        try {
            if (response.code() == 200 && response.body() != null) {
                final String data = response.body().string();
                if (TextUtils.isEmpty(data)) {
                    onFailed(callBack, "数据获取未空");
                } else if (handler != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(data);
                        }
                    });
            } else {
                onFailed(callBack, response.code() + " : " + response.message());
            }

        } catch (Throwable e) {
            onFailed(callBack, e.getMessage());
        }
    }


}
