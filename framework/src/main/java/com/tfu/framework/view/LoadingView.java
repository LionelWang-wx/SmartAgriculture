package com.tfu.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.framework.R;
import com.tfu.framework.manager.DialogManager;
import com.tfu.framework.utils.AnimUtils;

public class LoadingView {

    private static volatile LoadingView instance = null;
    private DialogView loadingView;
    private ImageView iv_loading;
    private TextView tv_loading_text;
    private ObjectAnimator animator;

    public LoadingView(Context context) {
        loadingView = DialogManager.getInstance().initView(context, R.layout.dialog_loding);
        iv_loading = loadingView.findViewById(R.id.iv_loading);
        tv_loading_text = loadingView.findViewById(R.id.tv_loading_text);
        animator = AnimUtils.startRotation(iv_loading);
    }

    //双重校验锁实现单例模式
    public static LoadingView getInstance(Context context) {
        if (instance == null) {
            synchronized (LoadingView.class) {
                if (instance == null) {
                    instance = new LoadingView(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置提示加载文本
     *
     * @param text
     */
    public void setLoadingText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv_loading_text.setText(text);
        }
    }

    /**
     * 显示LoadView
     */
    public void show() {
        animator.start();
        DialogManager.getInstance().show(loadingView);
    }

    public void show(String text) {
        animator.start();
        setLoadingText(text);
        DialogManager.getInstance().show(loadingView);
    }

    //隐藏LoadView
    public void hide() {
        animator.pause();
        DialogManager.getInstance().hide(loadingView);
    }

    /**
     * 外部是否可以点击取消
     *
     * @param isCancel
     */
    public void setCancelable(boolean isCancel) {
        loadingView.setCancelable(isCancel);
    }

}
