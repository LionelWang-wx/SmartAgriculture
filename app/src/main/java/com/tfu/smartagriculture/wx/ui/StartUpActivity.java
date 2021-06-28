package com.tfu.smartagriculture.wx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created on by WangXi 15/05/2021.
 * 启动页
 * Github:https://github.com/messi1240938550/SmartAgriculture.git
 */
public class StartUpActivity extends AppCompatActivity {


    private static final int SKIP_MAIN = 1000;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SKIP_MAIN:
                    startMain();
                    break;
            }
        }


    };

    private void startMain() {
        //1.判断app是否第一次启动
        Intent intent = new Intent();
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true);
//        String token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "");
        if (isFirstApp) {
            //跳转引导页
            intent.setClass(this, GuideActivity.class);
            //改变Constants.SP_IS_FIRST_APP的值
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false);
        } else {
            //判断曾经是否登录过
            String phoneNumber = SpUtils.getInstance().getString(Constants.SP_PHONE_NUMBER, "");
            if (TextUtils.isEmpty(phoneNumber)) {
                //跳转登录页
                intent.setClass(this, LoginActivityPlus.class);
            } else {
                //跳转主页
                intent.setClass(this, MainActivity.class);
            }
//            if (TextUtils.isEmpty(token)) {
//                //判断Bmob是否登录
//
//                if (BmobManager.getInstance().isLogin()) {
//                    //跳转主页
//                    intent.setClass(this, GYXActivity.class);
//                } else {
//                    //跳转登录页
//                    intent.setClass(this, LoginActivityPlus.class);
//                }
//            } else {
//                //跳转主页
//                intent.setClass(this, GYXActivity.class);
//            }
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        SpUtils.getInstance().initSp(this);

//        初始化Bmob
//        FrameWork.getInstance().initFrameWork(this);
        handler.sendEmptyMessageDelayed(SKIP_MAIN, 2000);
    }
}