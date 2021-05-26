package com.tfu.framework.manager;

import android.content.Context;
import android.view.Gravity;

import com.tfu.framework.R;
import com.tfu.framework.view.DialogView;


/**
 * 提示框管理类
 */
public class DialogManager {

    private static volatile DialogManager mInstance = null;

    public DialogManager() {

    }

    //双重校验锁实现单例模式
    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext, int layout) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, Gravity.CENTER);
    }

    public DialogView initView(Context mContext, int layout, int gravity) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, gravity);
    }


    public void show(DialogView dialogView) {
        if (dialogView != null) {
            if (!dialogView.isShowing()) {
                dialogView.show();
            }
        }
    }

    public void hide(DialogView dialogView) {
        if (dialogView != null) {
            if (dialogView.isShowing()) {
//                  dialogView.hide();
                dialogView.dismiss();
            }
        }
    }


}
