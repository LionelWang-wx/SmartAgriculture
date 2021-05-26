package com.tfu.smartagriculture.wx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tfu.framework.inter.ResultCallBack;
import com.tfu.framework.utils.HttpUtils;
import com.tfu.framework.utils.LogUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.base.baseAdapter.CommunityArticleAdapter;
import com.tfu.smartagriculture.wx.bean.PlanData;
import com.tfu.smartagriculture.wx.other.WritePlanActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * Created on by WangXi 15/05/2021.
 * 社区
 */
public class CommunityFragment extends Fragment implements View.OnClickListener, TextWatcher, ResultCallBack {
    public static ResultCallBack resultCallBackCommunity;
    RecyclerView rcv_article;
    EditText edt_search;
    ImageView iv_writePlan;
    ArrayList<PlanData.Plan> planList = new ArrayList<>();
    ArrayList<PlanData.Plan> searchList = new ArrayList<>();
    ArrayList<PlanData.Plan> planPlusList = new ArrayList<>();
    private CommunityArticleAdapter communityArticleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community_wx, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initAdapter();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        requestData();
//    }

    private void requestData() {
        HttpUtils.getInstance().get("http://121.196.173.248:9002/user/getDiscuss", new ResultCallBack() {
            @Override
            public void success(String data) {
                LogUtils.e("tag" + data);
                planList.clear();
                planPlusList.clear();
                if (TextUtils.isEmpty(data)) return;
                Gson gson = new Gson();
                PlanData planData = gson.fromJson(data, PlanData.class);
                for (PlanData.Plan plan : planData.getData()) {
                    planList.add(plan);
                }
                for (PlanData.Plan plan : planList) {
                    planPlusList.add(plan);
                }
                communityArticleAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(String msg) {
                showToast(getActivity(), msg);
            }
        });
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_article.setLayoutManager(linearLayoutManager);
        communityArticleAdapter = new CommunityArticleAdapter(getActivity(), planList);
        rcv_article.setAdapter(communityArticleAdapter);
    }

    private void initData() {
        requestData();
    }

    private void initView() {
        rcv_article = getActivity().findViewById(R.id.rcv_article);
        edt_search = getActivity().findViewById(R.id.edt_search);
        iv_writePlan = getActivity().findViewById(R.id.iv_writePlan);

        edt_search.addTextChangedListener(this);
        iv_writePlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_writePlan:
                resultCallBackCommunity = this;
                Intent intent = new Intent(getActivity(), WritePlanActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String input = edt_search.getText().toString();
        if (TextUtils.isEmpty(input)) {
            requestData();
            return;
        }

        for (PlanData.Plan plan : planPlusList) {
            if (plan.getTitle().contains(input)) {
                searchList.add(plan);
            }
        }
        if (searchList.size() <= 0) {
            planList.clear();
            communityArticleAdapter.notifyDataSetChanged();
            return;
        } else {
            planList.clear();
            for (PlanData.Plan plan : searchList) {
                planList.add(plan);
            }
            searchList.clear();
            communityArticleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    /**
     * 刷新
     *
     * @param data
     */
    @Override
    public void success(String data) {
        requestData();
        LogUtils.e("ajajajCommunityFrag更新成功");
    }

    @Override
    public void failed(String msg) {
        showToast(getActivity(), msg);
    }

}