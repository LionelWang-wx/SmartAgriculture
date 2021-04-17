package com.tfu.framework.utils;

import android.view.View;

public class ViewUtils {

    /**
     * 设置View的三种状态 gone visible invisible
     */

    public static void goneV(View... views) {
        if (views == null && views.length == 0) return;
        //使用增强型for循环遍历views
        for (View view : views) {
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void visibleV(View... views) {
        if (views == null && views.length == 0) return;
        for (View view : views) {
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void invisibleV(View... views) {
        if (views == null && views.length == 0) return;
        for (View view : views) {
            if (view != null) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
