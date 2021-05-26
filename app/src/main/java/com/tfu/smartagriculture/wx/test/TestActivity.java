package com.tfu.smartagriculture.wx.test;

import android.os.Bundle;
import android.util.Log;

import com.tfu.framework.inter.ResultCallBack;
import com.tfu.framework.utils.HttpUtils;
import com.tfu.smartagriculture.wx.http.HttpAddConfig;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Map<String,Object> bodyParams = new HashMap<>();
//        bodyParams.put("password","123456");
//        bodyParams.put("phone","157305");
//        HttpUtils.getInstance().postJson(HttpAddConfig.getLoginUrl(), bodyParams, new ResultCallBack() {
//            @Override
//            public void success(String data) {
//                Log.e("tag",data);
//            }
//
//            @Override
//            public void failed(String msg) {
//                Log.e("tag",msg);
//            }
//        });

        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("checkPassword", "123456");
        bodyParams.put("password", "123456");
        bodyParams.put("phone", "1573051");
        HttpUtils.getInstance().postJson(HttpAddConfig.getRegisteredUrl(), bodyParams, new ResultCallBack() {
            @Override
            public void success(String data) {
                Log.e("tag", data);
            }

            @Override
            public void failed(String msg) {
                Log.e("tag", msg);
            }
        });
    }


}
