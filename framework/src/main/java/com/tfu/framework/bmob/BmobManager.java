package com.tfu.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Bmob 管理类
 */
public class BmobManager {

    private static String BMOB_SDK_ID = "6d58398eb8e080d4c70310e14dc0a432";
    private volatile static BmobManager bmobManager = null;

    public BmobManager() {

    }

    //双重校验锁实现单例模式
    public static BmobManager getInstance() {
        if (bmobManager == null) {
            synchronized (BmobManager.class) {
                if (bmobManager == null) {
                    bmobManager = new BmobManager();
                }
            }
        }
        return bmobManager;
    }

    /**
     * Bmob初始化
     *
     * @param context
     */
    public void initBmob(Context context) {
        //第一：默认初始化
        Bmob.initialize(context, BMOB_SDK_ID);
    }

    /**
     * 请求短信验证码
     *
     * @param phoneNumber 手机号码
     * @param listener    请求回调
     */
    public void requestSMS(String phoneNumber, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phoneNumber, "", listener);
    }

    /**
     * 通过手机号码注册或者登陆
     *
     * @param phoneNumber 手机号码
     * @param code        短信验证码
     * @param listener    回调
     */
    public void signOrLoginByMobilePhone(String phoneNumber, String code, LogInListener<IMUser> listener) {
        BmobUser.signOrLoginByMobilePhone(phoneNumber, code, listener);
    }

    /**
     * 判断Bmob是否登录
     */
    public boolean isLogin() {
        return BmobUser.isLogin();
    }

}
