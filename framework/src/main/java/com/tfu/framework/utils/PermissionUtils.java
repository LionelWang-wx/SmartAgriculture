package com.tfu.framework.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import static com.tfu.framework.entity.Constants.PERMISSION_WINDOW_REQUEST_CODE;
import static com.tfu.framework.entity.Constants.REQUESTCODE;

/**
 * Android权限工具类
 */
public class PermissionUtils {

    private static volatile PermissionUtils mInstance = null;
    //未授权的权限集合
    public static List<String> unPermissions;
    //请求失败的权限集合
    public static List<String> failedPermissions;

    /**
     * 双重效验实现单例模式
     *
     * @return
     */
    public static PermissionUtils getInstance() {
        if (mInstance == null) {
            synchronized (PermissionUtils.class) {
                if (mInstance == null) {
                    mInstance = new PermissionUtils();
                    unPermissions = new ArrayList<>();
                    failedPermissions = new ArrayList<>();
                }
            }
        }
        return mInstance;
    }


    /**
     * 请求权限
     */
    public static void requestPermissions(Activity activity, String... permissions) {
        if (activity != null && permissions != null && permissions.length > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(permissions, REQUESTCODE);
            }
        }
    }

    /**
     * 判断是否需要申请权限
     */
    public static List<String> checkPermissions(Activity activity, String... permissions) {
        //清空未授权权限集合,便于第二次收集权限的准确性
        unPermissions.clear();
        if (activity != null && permissions != null && permissions.length > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (String permission : permissions) {
                    int check = activity.checkSelfPermission(permission);
                    if (check != PackageManager.PERMISSION_GRANTED) {
                        unPermissions.add(permission);
                    }
                }
            }
        }
        //这里返回的未授权权限集合可能为空,调用时需要进行处理
        return unPermissions;
    }

    /**
     * 请求窗口权限
     */
    public static void requestWindowPermission(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                , Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, PERMISSION_WINDOW_REQUEST_CODE);
    }

    /**
     * 判断窗口权限
     *
     * @param activity
     * @return
     */
    public static boolean checkWindowPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(activity);
        }
        return true;
    }

}
