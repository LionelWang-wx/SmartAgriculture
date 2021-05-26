package com.tfu.smartagriculture.wx.diyview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.smartagriculture.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
/**
 * Created on by WangXi 15/05/2021.
 * 底部菜单栏
 */
public class TabView extends FrameLayout {
    private static final int COLOR_DEFAULT = Color.parseColor("#bfbfbf");
    private static final int COLOR_SELECT = Color.parseColor("#1296db");
    private ImageView mIvicon;
    private ImageView mIviconselect;
    private TextView te_tab_view;

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.tab_view, this);


        /**
         *  灰色在上 1
         */

        mIvicon = findViewById(R.id.iv_icon);
        mIviconselect = findViewById(R.id.iv_icon_select);
        te_tab_view = findViewById(R.id.tb_text);

        setprogress(0);
    }
    //设置 icon和text
    //第一种 抽取自定义属性，通过xml设置
    //第二种 直接对外开放方法设置

    public void setIconandText(int icon, int iconselect, String text) {
        //这里我用的第二种方法
        mIvicon.setImageResource(icon);
        mIviconselect.setImageResource(iconselect);
        te_tab_view.setText(text);
    }

    public void setprogress(float progrss) {
        mIvicon.setAlpha(1 - progrss);


        mIviconselect.setAlpha(progrss);
        te_tab_view.setTextColor(evaluate(progrss, COLOR_DEFAULT, COLOR_SELECT));
    }

    private int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;

        int endInt = (Integer) endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }

}
