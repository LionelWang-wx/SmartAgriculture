package com.tfu.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * 沉浸式实现
 */
public class SystemUIUtils {


    public static void fixSystemUI(Activity activity) {
        int VERSION = Build.VERSION.SDK_INT;
        //适配Android 5.0
        if (VERSION >= Build.VERSION_CODES.LOLLIPOP && VERSION < Build.VERSION_CODES.R) {
            //获取最顶层View
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //适配android 11.0
//        if (VERSION >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            WindowInsetsController controller = activity.getWindow().getInsetsController();
//            if (controller != null) {
//                controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
//                controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
//                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
//            }
//        }
    }
}
