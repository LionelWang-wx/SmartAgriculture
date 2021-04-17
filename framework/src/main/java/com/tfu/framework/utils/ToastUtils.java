package com.tfu.framework.utils;

import android.content.Context;
import android.widget.Toast;

//Toast弹窗
public class ToastUtils {

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
