package com.tfu.smartagriculture.wx;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.tfu.framework.base.BaseActivity;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.diyview.TabView;
import com.tfu.smartagriculture.wx.fragment.CommunityFragment;
import com.tfu.smartagriculture.wx.fragment.DetectFragment;
import com.tfu.smartagriculture.wx.fragment.HomeFragment;
import com.tfu.smartagriculture.wx.fragment.MeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created on by WangXi 15/05/2021.
 * 主界面
 * Github:https://github.com/messi1240938550/SmartAgriculture.git
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";
    ViewPager vp_fragContainer;
    TabView tb_bt_home, tb_bt_detect, tb_bt_community, tb_bt_me;
    HomeFragment homeFrag;
    DetectFragment detectFrag;
    CommunityFragment communityFrag;
    MeFragment meFrag;
    private List<String> mtitls = new ArrayList<>(Arrays.asList("首页", "检测", "社区", "我的"));
    private List<TabView> mtad = new ArrayList<>();
    private int mCurTabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wx);
//        permissionDeal();
        // 后台中存储关键信息和数据
        // 旋转屏幕后在回到页面时恢复数据
        if (savedInstanceState != null) {
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS, 0);
        }
        initView();
        initData();
        for (int i = 0; i < mtad.size(); i++) {
            TabView view = mtad.get(i);
            //点击事件 切换页面
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp_fragContainer.setCurrentItem(finalI, false);

                    setCurrentTab(finalI);
                }
            });
        }
    }

    private void setCurrentTab(int pos) {    /**tab的点击切换页面*/

        for (int i = 0; i < mtad.size(); i++) {
            TabView view = mtad.get(i);
            if (i == pos) {
                view.setprogress(1);
            } else {
                view.setprogress(0);
            }

        }
    }

    @Override
    public void initView() {
        //控件初始化
        tb_bt_home = this.findViewById(R.id.tb_bt_home);
        tb_bt_detect = this.findViewById(R.id.tb_bt_detect);
        tb_bt_community = this.findViewById(R.id.tb_bt_community);
        tb_bt_me = this.findViewById(R.id.tb_bt_me);

        vp_fragContainer = this.findViewById(R.id.vp_fragContainer);

        mtad.add(tb_bt_home);
        mtad.add(tb_bt_detect);
        mtad.add(tb_bt_community);
        mtad.add(tb_bt_me);

        tb_bt_home.setIconandText(R.drawable.home_img, R.drawable._home_img, mtitls.get(0));
        tb_bt_detect.setIconandText(R.drawable.monitor_img, R.drawable._monitor_img, mtitls.get(1));
        tb_bt_community.setIconandText(R.drawable.iv_community_off, R.drawable.iv_community_on, mtitls.get(2));
        tb_bt_me.setIconandText(R.drawable.me_img, R.drawable._me_img, mtitls.get(3));

        setCurrentTab(mCurTabPos);
    }

    @Override
    public void initData() {
        initviewpagerAdapter();
    }

    private void initviewpagerAdapter() {
        vp_fragContainer.setOffscreenPageLimit(mtitls.size());
        vp_fragContainer.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 1:
                        return detectFrag = new DetectFragment();
                    case 2:
                        return communityFrag = new CommunityFragment();
                    case 3:
                        return meFrag = new MeFragment();
                }

//                Tab_fragment fragment = Tab_fragment.newInstance(mtitls.get(position));
                return homeFrag = new HomeFragment();
            }

            @Override
            public int getCount() {
                return mtitls.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                frag.remove(position);
                super.destroyItem(container, position, object);
            }
        });
        //设置ViewPager滑动
        vp_fragContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //左 -> 右  0~1 left:pas  ; rigth left+1  ; positionOffset 0~1
                //left 效果 :0~1 （1-positionOffset）；rigth 效果：0~1（positionOffset）


                //右 -> 左  1~0 left:pas  ; rigth left+1  ; positionOffset 1~0
                //left 效果 :0~1 （1-positionOffset）；rigth 效果：1~0（positionOffset）

                if (positionOffset > 0) {
                    TabView left = mtad.get(position);
                    TabView rigth = mtad.get(position + 1);

                    //回划时候的算法
                    left.setprogress(1 - (positionOffset));
                    rigth.setprogress((positionOffset));
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * position
     * 0 首页
     * 1 检测
     * 2 社区
     * 3 我的
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    /**
     * 权限处理
     */
//    private void permissionDeal(){
//        if (!checkWindowPermission(this)) {
//            requestWindowPermission(this);
//        }
//        //申明所需权限
//        String[] mStrPermission = {
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_PHONE_STATE,
//
//        };
//
//        requestPermissionsResult(new OnPermissionsResult() {
//            @Override
//            public void success() {
//                showToast(MainActivity.this, "权限申请成功");
//            }
//
//            @Override
//            public void failed(List<String> failedPermissions) {
//                if (mStrPermission.length == failedPermissions.size()) {
//                    showToast(MainActivity.this, "权限申请失败");
//                } else if (mStrPermission.length > failedPermissions.size()) {
//                    showToast(MainActivity.this, "部分权限申请失败");
//                }
//            }
//        }, mStrPermission);
//    }

    /**
     * 手机旋转横屏处理 在后台中存储关键信息和数据
     * 由于屏幕旋转之后 onCrea会重新执行 getItem没有执行会把数据清除
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_POS, vp_fragContainer.getCurrentItem());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}