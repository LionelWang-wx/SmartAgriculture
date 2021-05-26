package com.tfu.smartagriculture.wx.other;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tfu.framework.base.BaseActivity;
import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.HttpUtil;
import com.tfu.framework.utils.INetCallBack;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.ContentReceiceLR;

import java.util.HashMap;
import java.util.Map;

import static com.tfu.framework.utils.ToastUtils.showToast;
import static com.tfu.smartagriculture.wx.fragment.CommunityFragment.resultCallBackCommunity;
import static com.tfu.smartagriculture.wx.fragment.HomeFragment.resultCallBackHome;
/**
 * Created on by WangXi 15/05/2021.
 * 写方案类
 */

public class WritePlanActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_commit;
    EditText edt_title;
    EditText edt_content;

    public WritePlanActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_plan);
        initView();
        initData();
    }

    @Override
    public void initView() {
        tv_commit = findViewById(R.id.tv_commit);
        edt_title = findViewById(R.id.edt_title);
        edt_content = findViewById(R.id.edt_content);

        tv_commit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                String title = edt_title.getText().toString().trim();
                String content = edt_content.getText().toString().trim();
                String phoneNumber = SpUtils.getInstance().getString(Constants.SP_PHONE_NUMBER, "");
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast(this, "错误,用户未登录");
                }
                Map<String, Object> bodyParams = new HashMap<>();
                bodyParams.put("accountName", phoneNumber);
                bodyParams.put("remark", content);
                bodyParams.put("title", title);
                HttpUtil.getHttpInstance().postJson("http://121.196.173.248:9002/user/discuss", bodyParams, new INetCallBack() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        ContentReceiceLR contentReceiceLR = gson.fromJson(data, ContentReceiceLR.class);
                        if (contentReceiceLR.getStatus() == 200) {
                            resultCallBackCommunity.success(String.valueOf(contentReceiceLR.getStatus()));
                            resultCallBackHome.success(String.valueOf(contentReceiceLR.getStatus()));
                            showToast(WritePlanActivity.this, "发表成功");
                            finish();
                        } else {
                            resultCallBackCommunity.failed("发表失败");
                            resultCallBackHome.failed("发表失败");
                        }
                    }

                    @Override
                    public void failed(String msg) {
                        resultCallBackCommunity.failed(msg);
                        resultCallBackHome.failed(msg);
                    }
                });
                break;
        }
    }
}