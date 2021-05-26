package com.tfu.smartagriculture.wx.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.ui.LoginActivityPlus;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    CircleImageView iv_userHeader;
    TextView tv_goBackLogin;

    View icd_call;
    ImageView iv_call;
    TextView tv_call;

    View icd_collect;
    ImageView iv_collect;
    TextView tv_collect;

    View icd_about;
    ImageView iv_about;
    TextView tv_about;

    View icd_set;
    ImageView iv_set;
    TextView tv_set;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me_wx, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        iv_call.setImageResource(R.drawable.iv_call);
        tv_call.setText("联系客服");

        iv_collect.setImageResource(R.drawable.iv_collect);
        tv_collect.setText("收藏");

        iv_about.setImageResource(R.drawable.iv_about);
        tv_about.setText("关于");

        iv_set.setImageResource(R.drawable.iv_set);
        tv_set.setText("设置");

    }

    private void initView() {
        iv_userHeader = getActivity().findViewById(R.id.iv_userHeader);

        tv_goBackLogin = getActivity().findViewById(R.id.tv_goBackLogin);
        icd_call = getActivity().findViewById(R.id.icd_call);
        iv_call = icd_call.findViewById(R.id.iv_icon);
        tv_call = icd_call.findViewById(R.id.tv_funName);

        icd_collect = getActivity().findViewById(R.id.icd_collect);
        iv_collect = icd_collect.findViewById(R.id.iv_icon);
        tv_collect = icd_collect.findViewById(R.id.tv_funName);

        icd_about = getActivity().findViewById(R.id.icd_about);
        iv_about = icd_about.findViewById(R.id.iv_icon);
        tv_about = icd_about.findViewById(R.id.tv_funName);

        icd_set = getActivity().findViewById(R.id.icd_set);
        iv_set = icd_set.findViewById(R.id.iv_icon);
        tv_set = icd_set.findViewById(R.id.tv_funName);

        iv_userHeader.setOnClickListener(this);
        tv_goBackLogin.setOnClickListener(this);
        icd_call.setOnClickListener(this);
        icd_collect.setOnClickListener(this);
        icd_about.setOnClickListener(this);
        icd_set.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_userHeader:

                break;
            case R.id.tv_goBackLogin:
                SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, "");
                Intent intent = new Intent(getActivity(), LoginActivityPlus.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.icd_call:
                callPhone("15730587500");
                break;

            case R.id.icd_collect:

                break;

            case R.id.icd_about:

                break;

            case R.id.icd_set:

                break;
        }
    }


    /**
     *  * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *  * @param phoneNum 电话号码
     *  
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        getActivity().startActivity(intent);
    }

}