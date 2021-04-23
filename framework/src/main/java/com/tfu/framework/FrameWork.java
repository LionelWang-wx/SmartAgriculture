package com.tfu.framework;

import android.content.Context;

import com.tfu.framework.utils.SpUtils;

public class FrameWork {
    //volatile关键字可以防止jvm指令重排优化
    private volatile static FrameWork mInstance = null;

    public FrameWork() {

    }

    //双重校验锁实现单例模式
    public static FrameWork getInstance() {
        if (mInstance == null) {
            synchronized (FrameWork.class) {
                if (mInstance == null) {
                    mInstance = new FrameWork();
                }
            }
        }
        return mInstance;
    }

    public void initFrameWork(Context context) {
        SpUtils.getInstance().initSp(context);

    }

}
