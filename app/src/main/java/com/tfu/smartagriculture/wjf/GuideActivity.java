package com.tfu.smartagriculture.wjf;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.framework.base.BasePageAdapter;
import com.tfu.framework.base.BaseUIActivity;
import com.tfu.smartagriculture.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * 引导页
 */
public class GuideActivity extends BaseUIActivity {


    private ImageView iv_music_switch;
    private TextView tv_guide_skip;
    private ImageView iv_guide_point_1;
    private ImageView iv_guide_point_2;
    private ImageView iv_guide_point_3;
    private ViewPager mViewPager;

    private View view1, view2, view3;
    private List<View> mPageList = new ArrayList<>();

    private BasePageAdapter basePageAdapter;

    private ImageView iv_guide_star;
    private ImageView iv_guide_night;
    private ImageView iv_guide_smile;

//    private MediaPlayerManager guiMusic;

    private ObjectAnimator animator;

    /**
     * 1.ViewPager : 适配器|帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
//        initView();


    }

//    private void initView() {
//        iv_music_switch = (ImageView) findViewById(R.id.iv_music_switch);
//        tv_guide_skip = (TextView) findViewById(R.id.tv_guide_skip);
//        iv_guide_point_1 = (ImageView) findViewById(R.id.iv_guide_point_1);
//        iv_guide_point_2 = (ImageView) findViewById(R.id.iv_guide_point_2);
//        iv_guide_point_3 = (ImageView) findViewById(R.id.iv_guide_point_3);
//        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
//
//
//        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
//        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
//        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);
//        mPageList.add(view1);
//        mPageList.add(view2);
//        mPageList.add(view3);
//
//        iv_music_switch.setOnClickListener(this);
//        tv_guide_skip.setOnClickListener(this);
//
//        //预加载
//        mViewPager.setOffscreenPageLimit(mPageList.size());
//        //设置适配器
//        basePageAdapter = new BasePageAdapter(mPageList);
//        mViewPager.setAdapter(basePageAdapter);
//
//        //帧动画
//        iv_guide_star = view1.findViewById(R.id.iv_guide_star);
//        iv_guide_night = view2.findViewById(R.id.iv_guide_night);
//        iv_guide_smile = view3.findViewById(R.id.iv_guide_smile);
//
//        //播放帧动画
//        AnimationDrawable animationDrawable1 = (AnimationDrawable) iv_guide_star.getBackground();
//        animationDrawable1.start();
//
//        AnimationDrawable animationDrawable2 = (AnimationDrawable) iv_guide_night.getBackground();
//        animationDrawable2.start();
//
//        AnimationDrawable animationDrawable3 = (AnimationDrawable) iv_guide_smile.getBackground();
//        animationDrawable3.start();
//
//        //小圆点逻辑
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                seletePoint(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        //歌曲的逻辑
//        startMusic();
//
//
//    }
//
//    //播放音乐
//    private void startMusic() {
//        guiMusic = new MediaPlayerManager();
//        guiMusic.setLooping(true);
//        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.guide);
//        guiMusic.startPlay(assetFileDescriptor);
//
//        //旋转动画
//        animator = AnimUtils.startRotation(iv_music_switch);
//        animator.start();
//    }
//
//    private void seletePoint(int position) {
//        switch (position) {
//            case 0:
//                iv_guide_point_1.setImageResource(R.drawable.img_guide_point_p);
//                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
//                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
//                break;
//            case 1:
//                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
//                iv_guide_point_2.setImageResource(R.drawable.img_guide_point_p);
//                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
//                break;
//            case 2:
//                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
//                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
//                iv_guide_point_3.setImageResource(R.drawable.img_guide_point_p);
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_music_switch:
//                if (guiMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE) {
//                    animator.start();
//                    guiMusic.continuePlay();
//                    iv_music_switch.setImageResource(R.drawable.img_guide_music);
//                } else if (guiMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PLAY) {
//                    animator.pause();
//                    guiMusic.pausePlay();
//                    iv_music_switch.setImageResource(R.drawable.img_guide_music_off);
//                }
//                break;
//            case R.id.tv_guide_skip:
//                startActivity(new Intent(this, com.myapp.meet.ui.LoginActivity.class));
//                finish();
//                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        guiMusic.stopPlay();
//    }
}