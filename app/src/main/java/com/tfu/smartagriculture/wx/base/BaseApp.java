package com.tfu.smartagriculture.wx.base;

import android.app.Application;

import com.tfu.framework.FrameWork;

/**
 *
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Application的优化
         * 1.必要的组件在程序主页去初始化
         * 2.如果组件一定要在App中初始化，那么尽可能的延时
         * 3.非必要的组件，子线程中初始化
         */
        //初始化Bmob
        FrameWork.getInstance().initFrameWork(this);

    }

}
