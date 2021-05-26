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
import com.tfu.framework.utils.HttpUtil;
import com.tfu.framework.utils.INetCallBack;
import com.tfu.framework.utils.LogUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.ContentReceiceLR;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * Created on by WangXi 15/05/2021.
 * 普通注册页
 */
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    EditText edt_phoneNumber;
    EditText edt_confirmPassword;
    EditText edt_password;
    Button btn_registered;
    CircleImageView iv_registered;
    TextView tv_goLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
        initData();
    }

    @Override
    public void initView() {
        iv_registered = findViewById(R.id.iv_registered);
        edt_phoneNumber = findViewById(R.id.edt_phoneNumber);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmPassword = findViewById(R.id.edt_confirmPassword);
        btn_registered = findViewById(R.id.btn_registered);
        tv_goLogin = this.findViewById(R.id.tv_goLogin);

        tv_goLogin.setOnClickListener(this);
        btn_registered.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registered:
                String phoneNumber = edt_phoneNumber.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String confirmPassword = edt_confirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast(this, "电话号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast(this, "密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    showToast(this, "确认密码不能为空");
                    return;
                }
                //判断两次密码输入是否相等
                if (!TextUtils.equals(password, confirmPassword)) {
                    showToast(this, "两次输入密码不一致");
                    return;
                }

                Map<String, Object> bodyParams = new HashMap<>();
                bodyParams.put("phone", phoneNumber);
                bodyParams.put("password", password);
                bodyParams.put("checkPassword", confirmPassword);
                HttpUtil.getHttpInstance().postJson("http://121.196.173.248:9002/user/zhuce?checkPassword='" + confirmPassword + "'&password='" + password + "'&phone=" + phoneNumber,
                        bodyParams,
                        new INetCallBack() {
                            @Override
                            public void success(String data) {
                                LogUtils.e(data);
                                Gson gson = new Gson();
                                ContentReceiceLR contentReceiceLR = gson.fromJson(data, ContentReceiceLR.class);
                                if (contentReceiceLR.getStatus() == 200) {
                                    if (!(TextUtils.isEmpty(contentReceiceLR.getData()))) {
                                        showToast(RegisteredActivity.this, contentReceiceLR.getData());
                                    } else {

                                        Intent i = new Intent(RegisteredActivity.this, LoginActivityPlus.class);
                                        startActivity(i);
                                        //保存手机号码
//                                      SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, phoneNumber);
                                        showToast(RegisteredActivity.this, "注册成功");
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void failed(String msg) {
                                showToast(RegisteredActivity.this, "注册失败");
                            }
                        });

                break;

            case R.id.tv_goLogin:
                Intent i = new Intent(RegisteredActivity.this, LoginActivityPlus.class);
                startActivity(i);
                break;
        }
    }
}
