package com.tfu.smartagriculture.gyx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.ui.LoginActivityPlus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class GYXActivity extends AppCompatActivity {
    ViewPager vp_AD;
    TextView tv_goBackLogin;
    private int[] iconAD = new int[]{R.drawable.iv_ad1_gyx, R.drawable.iv_ad2_gyx, R.drawable.iv_ad3_gyx, R.drawable.iv_ad1_gyx, R.drawable.iv_ad2_gyx};
    private boolean icon = true;
    private ArrayList<ImageView> mdots = new ArrayList<>();
    //小圆点容器
    private LinearLayout circlecan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_y_x);
        vp_AD = this.findViewById(R.id.vp_AD);
        circlecan = this.findViewById(R.id.ll_circle_can);
        tv_goBackLogin = this.findViewById(R.id.tv_goBackLogin);
        initData();
        initViewPager();
        tv_goBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, "");
                Intent intent = new Intent(GYXActivity.this, LoginActivityPlus.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void initData() {
        /**
         * viewpager 初始化
         */
        vp_AD.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return iconAD.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = new View(container.getContext());

                view.setBackgroundResource(iconAD[position]);
                ViewGroup.LayoutParams viewGroup = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //强制复写
                container.removeView((View) object);

            }
        });
    }

    private void initViewPager() {
        vp_AD.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    vp_AD.setCurrentItem(iconAD.length - 2, false);

                } else if (position == iconAD.length - 1) {
                    vp_AD.setCurrentItem(1, false);
                }
                setDotsImgs();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                if (icon == true) {
                    if (msg.what == 0) {
                        this.removeMessages(0);
                        vp_AD.setCurrentItem(vp_AD.getCurrentItem() + 1, true);
                        this.sendEmptyMessageDelayed(0, 2000);

                    }
                } else if (icon == false) {
                    if (this != null) {
                        Toast.makeText(GYXActivity.this, "停止轮播", Toast.LENGTH_SHORT).show();
                        this.removeMessages(0);
                    }

                }

                super.handleMessage(msg);
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);

        for (int i = 0; i < iconAD.length; i++) {
            ImageView view = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 30;
            params.rightMargin = 30;
            view.setLayoutParams(params);
            mdots.add(view);
            circlecan.addView(view);
        }

    }


    private void setDotsImgs() {
        for (int i = 0; i < mdots.size(); i++) {
            if (i == vp_AD.getCurrentItem() - 1) {
                mdots.get(i).setBackgroundResource(R.drawable.img_page);
            } else {
                mdots.get(i).setBackgroundResource(R.drawable.img_pagenow);
            }
        }
    }


}