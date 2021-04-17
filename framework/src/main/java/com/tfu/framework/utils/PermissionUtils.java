package com.tfu.framework.utils;

/**
 * Android权限工具类
 */
public class PermissionUtils {

    private static volatile PermissionUtils mInstance = null;

    /**
     * 双重效验实现单例模式
     *
     * @return
     */
    private static PermissionUtils getInstance() {
        if (mInstance == null) {
            synchronized (PermissionUtils.class) {
                if (mInstance == null) {
                    mInstance = new PermissionUtils();
                }
            }
        }
        return mInstance;
    }


}
