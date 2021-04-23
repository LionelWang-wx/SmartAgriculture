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
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.framework.bmob.BmobManager;
import com.myapp.framework.bmob.IMUser;
import com.myapp.framework.entity.Constants;
import com.myapp.framework.manager.DialogManager;
import com.myapp.framework.utils.SpUtils;
import com.myapp.framework.view.DialogView;
import com.myapp.framework.view.LoadingView;
import com.myapp.framework.view.TouchPictureView;
import com.myapp.meet.MainActivity;
import com.myapp.meet.base.BaseActivity;
import com.tfu.smartagriculture.R;

import androidx.annotation.NonNull;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

import static com.myapp.framework.utils.ToastUtils.showToast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int H_TIME = 1001;
    //60s倒计时
    private static int TIME = 60;
    ImageView iv_wxLogin, iv_wbLogin, iv_qqLogin;
    /**
     * 1.点击发送的按钮，弹出一个提示框，图片验证码，验证通过之后
     * 2.!发送验证码，@同时按钮变成不可点击，@按钮开始倒计时，倒计时结束，@按钮可点击，@文字变成“发送”
     * 3.通过手机号码和验证码进行登录
     * 4.登录成功之后获取本地对象
     */

    private EditText et_phone;
    private EditText et_code;
    private Button btn_send_code;
    private Button btn_login;
    private TextView tv_test_login;
    private DialogView dialogView;
    private TouchPictureView tv_picture;
    private String phoneNumber;

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
                        btn_send_code.setText(R.string.text_login_send);
                        TIME = 60;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
        //从SP中得到phoneNumber
        phoneNumber = SpUtils.getInstance().getString(Constants.SP_PHONE_NUMBER, "");
        et_phone.setText(phoneNumber);
        dialogView = DialogManager.getInstance().initView(this, R.layout.dialog_code_view);
        tv_picture = dialogView.findViewById(R.id.tv_picture);
        //监听图片验证
        tv_picture.setOnViewResultListener(result -> {
            if (result) {
                //验证通过后隐藏图片验证框
                DialogManager.getInstance().hide(dialogView);
                btn_send_code.setEnabled(false);
                btn_send_code.setText("60s");
                //发送短信验证码
                sendSMS();
            } else {
                showToast(this, "验证失败");
            }
        });
    }

    private void initView() {
        tv_test_login = findViewById(R.id.tv_test_login);
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        btn_send_code = findViewById(R.id.btn_send_code);
        btn_login = findViewById(R.id.btn_login);
        iv_wxLogin = findViewById(R.id.iv_wxLogin);
        iv_qqLogin = findViewById(R.id.iv_qqLogin);
        iv_wbLogin = findViewById(R.id.iv_wbLogin);

        tv_test_login.setOnClickListener(this);
        btn_send_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        iv_wxLogin.setOnClickListener(this);
        iv_qqLogin.setOnClickListener(this);
        iv_wbLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                //判断手机号码不为空
                phoneNumber = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast(this, getString(R.string.text_login_phone_null));
                    return;
                }
                //弹出图片验证框
                DialogManager.getInstance().show(dialogView);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_test_login:

                break;
            case R.id.iv_wxLogin:
                //微信登录

                break;
            case R.id.iv_qqLogin:
                //QQ登录


                break;
            case R.id.iv_wbLogin:
                //微博登录


                break;
            default:

                break;
        }
    }

    private void login() {
        //1.判断手机号和验证码不为空
        String phoneNumber = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(this, getString(R.string.text_login_phone_null));
            return;
        }
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast(this, getString(R.string.text_login_code_null));
            return;
        }
        //显示LoadingView
        LoadingView.getInstance(this).show(getString(R.string.text_login_now_login_text));
        //通过手机号码一键注册或者登陆
        BmobManager.getInstance().signOrLoginByMobilePhone(phoneNumber, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null) {
                    //登录成功隐藏LoadingView
                    LoadingView.getInstance(com.myapp.meet.ui.LoginActivity.this).hide();
                    if (imUser != null) {
                        showToast(com.myapp.meet.ui.LoginActivity.this, "登陆成功");
                        startActivity(new Intent(com.myapp.meet.ui.LoginActivity.this, MainActivity.class));
                        //保存手机号码
                        SpUtils.getInstance().putString(Constants.SP_PHONE_NUMBER, imUser.getMobilePhoneNumber());
                        finish();
                    } else {
                        //登录失败,返回数据为空异常
                        showToast(com.myapp.meet.ui.LoginActivity.this, "登录失败");
                    }
                } else {
                    //登录失败,并且存在异常
                    if (e.getErrorCode() == 207) {
                        showToast(com.myapp.meet.ui.LoginActivity.this, getString(R.string.text_login_code_error));
                    } else {
                        showToast(com.myapp.meet.ui.LoginActivity.this, e.toString());
                    }
                }
            }
        });


    }


    //发送短信验证码
    private void sendSMS() {
        //trim:忽略前导空白和尾部空白
        //1.获取手机号码
        phoneNumber = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(this, getString(R.string.text_login_phone_null));
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance().requestSMS(phoneNumber, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    btn_send_code.setEnabled(false);
                    handler.sendEmptyMessage(H_TIME);
                    showToast(com.myapp.meet.ui.LoginActivity.this, getString(R.string.text_user_resuest_succeed));
                } else {
                    btn_send_code.setEnabled(true);
                    btn_send_code.setText("发送");
                    showToast(com.myapp.meet.ui.LoginActivity.this, getString(R.string.text_user_resuest_fail));
                }
            }
        });

    }
}