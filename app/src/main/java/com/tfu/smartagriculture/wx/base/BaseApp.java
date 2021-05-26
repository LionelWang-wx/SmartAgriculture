package com.tfu.smartagriculture.wx.base;

import android.app.Application;

/**
 * Created on by WangXi 15/05/2021.
 * 说明：最开始准备使用Bmob后端云，所以里面有很多Bmob的使用，最后没有采用Bmob后端云，但代码中也没有根除
 * 这个类是用来初始化组件的
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

//        FrameWork.getInstance().initFrameWork(this);

    }

}
