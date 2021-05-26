package com.tfu.smartagriculture.wx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfu.framework.inter.ResultCallBack;
import com.tfu.framework.utils.HttpUtils;
import com.tfu.framework.utils.LogUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.base.baseAdapter.RecommendedPlanAdapter;
import com.tfu.smartagriculture.wx.bean.PlanData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements ResultCallBack {
    public static ResultCallBack resultCallBackHome;
    ViewPager vp_AD;
    RecyclerView rcv_recommendedPlan;
    private RecommendedPlanAdapter recommendedPlanAdapter;
    private int[] iconAD = new int[]{R.drawable.img_carousel3,
            R.drawable.img_carousel2,
            R.drawable.img_carousel1,
            R.drawable.iv_ad_1,
            R.drawable.iv_ad_4};
    private boolean icon = true;
    private ArrayList<ImageView> mdots = new ArrayList<>();
    //方案数据
    private List<PlanData.Plan> planList = new ArrayList<>();
    //小圆点容器
    private LinearLayout circlecan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_wx, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resultCallBackHome = this;
        initView();
        initData();
        requestData();
        initAdapter();
        initViewPager();
    }

    private void requestData() {
        HttpUtils.getInstance().get("http://121.196.173.248:9002/user/getDiscuss", new ResultCallBack() {
            @Override
            public void success(String data) {
                LogUtils.e("data" + data);
                planList.clear();
                if (TextUtils.isEmpty(data)) return;
                Gson gson = new Gson();
                PlanData planData = gson.fromJson(data, PlanData.class);
                for (PlanData.Plan plan : planData.getData()) {
                    planList.add(plan);
                }
                recommendedPlanAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(String msg) {
                showToast(getActivity(), msg);
            }
        });
    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        //推荐方案适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_recommendedPlan.setLayoutManager(linearLayoutManager);
        recommendedPlanAdapter = new RecommendedPlanAdapter(getActivity(), planList);
        rcv_recommendedPlan.setAdapter(recommendedPlanAdapter);
    }


    private void initView() {
        circlecan = getActivity().findViewById(R.id.ll_circle_can);
        vp_AD = getActivity().findViewById(R.id.vp_AD);
        rcv_recommendedPlan = getActivity().findViewById(R.id.rcv_recommendedPlan);
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
                        Toast.makeText(getContext(), "停止轮播", Toast.LENGTH_SHORT).show();
                        this.removeMessages(0);
                    }

                }

                super.handleMessage(msg);
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);

        for (int i = 0; i < iconAD.length; i++) {
            ImageView view = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 25;
            params.rightMargin = 25;
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

    @Override
    public void success(String data) {
        requestData();
        LogUtils.e("ajajajHomeFrag更新成功");
    }

    @Override
    public void failed(String msg) {
        showToast(getActivity(), msg);
    }
}