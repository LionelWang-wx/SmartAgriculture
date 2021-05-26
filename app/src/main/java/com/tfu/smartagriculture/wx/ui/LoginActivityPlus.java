package com.tfu.smartagriculture.wx.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tfu.framework.base.BaseActivity;
import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.HttpUtil;
import com.tfu.framework.utils.INetCallBack;
import com.tfu.framework.utils.LogUtils;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.MainActivity;
import com.tfu.smartagriculture.wx.bean.ContentReceiceLR;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * Created on by WangXi 15/05/2021.
 * 普通登录页
 */
public class LoginActivityPlus extends BaseActivity implements View.OnClickListener {
    EditText edt_phone;
    EditText edt_code;
    Button btn_login;
    CircleImageView iv_logo;
    TextView tv_goRegistered;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_plus);
        initView();
        initData();
    }

    @Override
    public void initView() {
        iv_logo = findViewById(R.id.iv_logo);
        edt_phone = findViewById(R.id.edt_phone);
        edt_code = findViewById(R.id.edt_code);
        btn_login = findViewById(R.id.btn_login);
        tv_goRegistered = this.findViewById(R.id.tv_goRegistered);

        tv_goRegistered.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phoneNumber = edt_phone.getText().toString().trim();
                String password = edt_code.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast(this, "电话号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast(this, "密码不能为空");
                    return;
                }

                Map<String, Object> bodyParams = new HashMap<>();
                bodyParams.put("phone", phoneNumber);
                bodyParams.put("password", password);
                HttpUtil.getHttpInstance().postJson(
                        "http://121.196.173.248:9002/user/login?password='" + password + "'&phone=" + phoneNumber,
                        bodyParams,
                        new INetCallBack() {
                            @Override
                            public void success(String data) {
                                LogUtils.e(data);
                                Gson gson = new Gson();
                                ContentReceiceLR contentReceiceLR = gson.fromJson(data, ContentReceiceLR.class);
                                if (contentReceiceLR.getStatus() == 200) {
                                    showToast(LoginActivityPlus.this, "登录成功");
                                    Intent i = new Intent(LoginActivityPlus.this, MainActivity.class);
                                    startActivity(i);
                                    //保存手机号码
                                    SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, phoneNumber);
                                    finish();
                                } else {
                                    showToast(LoginActivityPlus.this, "账号或密码不正确,请重新登陆或注册");
                                }

                            }

                            @Override
                            public void failed(String msg) {
                                showToast(LoginActivityPlus.this, "登录失败");
                            }
                        });
                break;

            case R.id.tv_goRegistered:
                Intent i = new Intent(LoginActivityPlus.this, RegisteredActivity.class);
                startActivity(i);
                break;
        }

    }
}