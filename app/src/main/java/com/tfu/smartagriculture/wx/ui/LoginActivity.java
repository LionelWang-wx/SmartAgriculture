package com.tfu.smartagriculture.wx.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tfu.framework.base.BaseActivity;
import com.tfu.framework.bmob.BmobManager;
import com.tfu.framework.bmob.IMUser;
import com.tfu.framework.entity.Constants;
import com.tfu.framework.utils.SpUtils;
import com.tfu.framework.view.LoadingView;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.gyx.GYXActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * Created on by WangXi 15/05/2021.
 * Bmob验证码登录注册页
 * Github:https://github.com/messi1240938550/SmartAgriculture.git
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    EditText edt_phoneNumber;
    EditText edt_code;
    Button btn_loginOrRegistered;
    Button btn_send_code;
    private static final int H_TIME = 1001;
    //60s倒计时
    private static int TIME = 60;
    //Handler做的倒计时
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_TIME:
                    TIME--;
                    btn_send_code.setText(TIME + "s");
                    if (TIME > 0) {
                        handler.sendEmptyMessageDelayed(H_TIME, 1000);
                    } else {
                        btn_send_code.setEnabled(true);
                        btn_send_code.setText("发送");
                        TIME = 60;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_wx);
        initView();
        initData();
    }

    @Override
    public void initView() {
        edt_phoneNumber = findViewById(R.id.edt_phoneNumber);
        edt_code = findViewById(R.id.edt_code);
        btn_send_code = findViewById(R.id.btn_send_code);
        btn_loginOrRegistered = findViewById(R.id.btn_loginOrRegistered);

        btn_send_code.setOnClickListener(this);
        btn_loginOrRegistered.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loginOrRegistered:
                login();
                break;

            case R.id.btn_send_code:
                btn_send_code.setEnabled(true);
                btn_send_code.setText("60s");
                //发送短信验证码
                sendSMS();
                break;
        }
    }


    private void login() {
        //1.判断手机号和验证码不为空
        String phoneNumber = edt_phoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(this, "手机号码不能为空");
            return;
        }
        String code = edt_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast(this, "验证码不能为空");
            return;
        }
        //显示LoadingView
        LoadingView.getInstance(this).show("正在登陆...");
        //通过手机号码一键注册或者登陆
        BmobManager.getInstance().signOrLoginByMobilePhone(phoneNumber, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null) {
                    //登录成功隐藏LoadingView
                    LoadingView.getInstance(LoginActivity.this).hide();
                    if (imUser != null) {
                        showToast(LoginActivity.this, "登陆成功");
                        //打包不同版本
//                        if (TextUtils.equals(BuildConfig.typeName,"gyx")){
                        startActivity(new Intent(LoginActivity.this, GYXActivity.class));
//                        }else if (TextUtils.equals(BuildConfig.typeName,"wx")){
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        }
                        //保存手机号码
                        SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, imUser.getMobilePhoneNumber());
                        finish();
                    } else {
                        //登录失败,返回数据为空异常
                        showToast(LoginActivity.this, "登录失败");
                    }
                } else {
                    //登录失败,并且存在异常
                    if (e.getErrorCode() == 207) {
                        showToast(LoginActivity.this, "验证码错误");
                    } else {
                        showToast(LoginActivity.this, e.toString());
                    }
                }
            }
        });


    }


    //发送短信验证码
    private void sendSMS() {
        //trim:忽略前导空白和尾部空白
        //1.获取手机号码
        String phoneNumber = edt_phoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(this, "电话号码不能为空");
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance().requestSMS(phoneNumber, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    btn_send_code.setEnabled(false);
                    handler.sendEmptyMessage(H_TIME);
                    showToast(LoginActivity.this, "请求成功");
                } else {
                    btn_send_code.setEnabled(true);
                    btn_send_code.setText("发送");
                    showToast(LoginActivity.this, "请求失败");
                }
            }
        });

    }
}