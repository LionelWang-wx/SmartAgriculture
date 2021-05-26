package com.tfu.smartagriculture.wx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.tfu.framework.inter.ResultCallBack;
import com.tfu.framework.utils.HttpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.base.baseAdapter.DetectAdapter;
import com.tfu.smartagriculture.wx.bean.ContentReceice;
import com.tfu.smartagriculture.wx.bean.DetectDevice;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * Created on by WangXi 15/05/2021.
 * 检测
 */
public class DetectFragment extends Fragment {

    Spinner sp_Scenes;
    RecyclerView rcv_detects;
    private DetectAdapter detectAdapter;

    private ArrayList<DetectDevice> planList = new ArrayList<>();

    //当前页面自动刷新
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
//            requestData();
            handler.postDelayed(this, 5000); //5秒刷新一次
            requestData();
        }
    };

    private void requestData() {
        HttpUtils.getInstance().get("http://121.196.173.248:9002/user/getSource", new ResultCallBack() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                ContentReceice contentReceice = gson.fromJson(data, ContentReceice.class);
                if (contentReceice.getStatus() == 200 && contentReceice.getData() != null) {
                    planList.clear();
                    ContentReceice.Data resultData = contentReceice.getData();
                    DetectDevice temperature = new DetectDevice(1, R.drawable.iv_tp, "实时温度", resultData.getTemperature());
                    planList.add(temperature);
                    DetectDevice humidity = new DetectDevice(2, R.drawable.iv_humidity_sensor, "实时湿度", resultData.getHumidity());
                    planList.add(humidity);
                    DetectDevice pm = new DetectDevice(3, R.drawable.iv_pm_sensor, "实时PM2.5", resultData.getPm());
                    planList.add(pm);
                    DetectDevice smoke = new DetectDevice(4, R.drawable.iv_smoke_sensor, "实时烟雾", resultData.getSmoke());
                    planList.add(smoke);
                    detectAdapter.notifyDataSetChanged();
                } else {
                    showToast(getActivity(), "请求数据失败");
                }
            }

            @Override
            public void failed(String msg) {
                showToast(getActivity(), msg);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detect_wx, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        //初始化切换场景适配器
        String[] mItems = new String[]{"大棚1号", "基地2", "基地3", "添加场景"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Scenes.setAdapter(adapter);
        sp_Scenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //初始化检测适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_detects.setLayoutManager(linearLayoutManager);
        detectAdapter = new DetectAdapter(getActivity(), planList);
        rcv_detects.setAdapter(detectAdapter);
    }

    private void initData() {
        requestData();
        handler.postDelayed(runnable, 5000);
    }

    private void initView() {
        sp_Scenes = getActivity().findViewById(R.id.sp_Scenes);
        rcv_detects = getActivity().findViewById(R.id.rcv_detects);
    }
}