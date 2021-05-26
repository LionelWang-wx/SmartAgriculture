package com.tfu.smartagriculture.wx.other;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.tfu.framework.inter.ResultCallBack;
import com.tfu.framework.utils.HttpUtils;
import com.tfu.framework.utils.LogUtils;
import com.tfu.framework.utils.SpUtils;
import com.tfu.smartagriculture.R;
import com.tfu.smartagriculture.wx.bean.ContentReceice;
import com.tfu.smartagriculture.wx.bean.DetectDevice;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * 加载webView图标类
 */
public class InspectionDetailsActivity extends AppCompatActivity implements OnRangeChangedListener {

    WebView wv_details;
    TextView tv_deviceName;
    TextView tv_deviceId;
    TextView tv_deviceStatus;
    TextView tv_deviceValue;
    ImageView iv_icon;
    TextView tv_connected;
    RangeSeekBar rsb_deviceNormalValue;
    RangeSeekBar rsb_deviceYValue;
    RangeSeekBar rsb_deviceRValue;

    String deviceName;
    String value;
    private ArrayList<DetectDevice> planList = new ArrayList<>();

    //当前页面自动刷新
    private Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            requestData();
            handler.postDelayed(this, 5000); //5秒刷新一次
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        initView();
        initWebView();
        initData();
    }

    private void initView() {
        wv_details = this.findViewById(R.id.wv_details);
        tv_deviceName = this.findViewById(R.id.tv_deviceName);
        tv_deviceId = this.findViewById(R.id.tv_deviceId);
        tv_deviceStatus = this.findViewById(R.id.tv_deviceStatus);
        tv_deviceValue = this.findViewById(R.id.tv_deviceValue);
        iv_icon = this.findViewById(R.id.iv_icon);
        tv_connected = this.findViewById(R.id.tv_connected);

        rsb_deviceNormalValue = this.findViewById(R.id.rsb_deviceNormalValue);
        rsb_deviceYValue = this.findViewById(R.id.rsb_deviceYValue);
        rsb_deviceRValue = this.findViewById(R.id.rsb_deviceRValue);

        rsb_deviceNormalValue.setOnRangeChangedListener(this);
        rsb_deviceYValue.setOnRangeChangedListener(this);
        rsb_deviceRValue.setOnRangeChangedListener(this);

    }

    private void initData() {

        DetectDevice detectDevice = (DetectDevice) getIntent().getSerializableExtra("detectDevice");
        deviceName = detectDevice.getTv_detectName();
        tv_deviceName.setText(deviceName);
        tv_deviceId.setText("IOT_DEVICES_" + detectDevice.getDetectDeviceId());
        tv_deviceValue.setText("自定义预警配置");
        tv_connected.setText("已连接");
        int value = Integer.parseInt(detectDevice.getTv_detectValue());
        switch (deviceName) {
            case "实时温度":
                wv_details.loadUrl("file:///android_asset/temperature.html");
                float leftNormal_T = SpUtils.getInstance().getInt("Tem_N_L", 1);
                float rightNormal_T = SpUtils.getInstance().getInt("Tem_N_R", 50);
                float leftY_T = SpUtils.getInstance().getInt("Tem_Y_L", 6);
                float rightY_T = SpUtils.getInstance().getInt("Tem_Y_R", 10);
                float leftR_T = SpUtils.getInstance().getInt("Tem_R_L", 11);
                float rightR_T = SpUtils.getInstance().getInt("Tem_R_R", 20);
                LogUtils.e("预警值：" + leftNormal_T + "__" + rightNormal_T + "__" + leftY_T + "__" + rightY_T + "__" + leftR_T + "__" + rightR_T);
                if ((value < rightNormal_T) && (value > leftNormal_T)) {
                    tv_deviceStatus.setText("状态正常");
                } else if (value >= leftY_T && value < rightY_T) {
                    tv_deviceStatus.setText("黄色预警");
                } else {
                    tv_deviceStatus.setText("红色预警");
                }
                rsb_deviceNormalValue.setProgress(leftNormal_T, rightNormal_T);
                LogUtils.e("预警值：" + leftNormal_T + "__" + rightNormal_T);
                rsb_deviceYValue.setProgress(leftY_T, rightY_T);
                rsb_deviceRValue.setProgress(leftR_T, rightR_T);
                break;
            case "实时湿度":
                wv_details.loadUrl("file:///android_asset/humidity.html");
                float leftNormal_H = SpUtils.getInstance().getInt("Hum_N_L", 1);
                float rightNormal_H = SpUtils.getInstance().getInt("Hum_N_R", 5);
                float leftY_H = SpUtils.getInstance().getInt("Hum_Y_L", 6);
                float rightY_H = SpUtils.getInstance().getInt("Hum_Y_R", 10);
                float leftR_H = SpUtils.getInstance().getInt("Hum_R_L", 11);
                float rightR_H = SpUtils.getInstance().getInt("Hum_R_R", 20);
                if ((value < rightNormal_H) && (value > leftNormal_H)) {
                    tv_deviceStatus.setText("状态正常");
                } else if (value >= leftY_H && value < rightY_H) {
                    tv_deviceStatus.setText("黄色预警");
                } else {
                    tv_deviceStatus.setText("红色预警");
                }
                rsb_deviceNormalValue.setProgress(leftNormal_H, rightNormal_H);
                rsb_deviceYValue.setProgress(leftY_H, rightY_H);
                rsb_deviceRValue.setProgress(leftR_H, rightR_H);
                break;
            case "实时PM2.5":
                wv_details.loadUrl("file:///android_asset/pm2.5.html");
                float leftNormal_P = SpUtils.getInstance().getInt("PM_N_L", 1);
                float rightNormal_P = SpUtils.getInstance().getInt("PM_N_R", 5);
                float leftY_P = SpUtils.getInstance().getInt("PM_Y_L", 6);
                float rightY_P = SpUtils.getInstance().getInt("PM_Y_R", 10);
                float leftR_P = SpUtils.getInstance().getInt("PM_R_L", 11);
                float rightR_P = SpUtils.getInstance().getInt("PM_R_R", 20);
                if ((value < rightNormal_P) && (value > leftNormal_P)) {
                    tv_deviceStatus.setText("状态正常");
                } else if (value >= leftY_P && value < rightY_P) {
                    tv_deviceStatus.setText("黄色预警");
                } else {
                    tv_deviceStatus.setText("红色预警");
                }
                rsb_deviceNormalValue.setProgress(leftNormal_P, rightNormal_P);
                rsb_deviceYValue.setProgress(leftY_P, rightY_P);
                rsb_deviceRValue.setProgress(leftR_P, rightR_P);
                break;
            case "实时烟雾":
                wv_details.loadUrl("file:///android_asset/smoke.html");
                float leftNormal_S = SpUtils.getInstance().getInt("SM_N_L", 1);
                float rightNormal_S = SpUtils.getInstance().getInt("SM_N_R", 5);
                float leftY_S = SpUtils.getInstance().getInt("SM_Y_L", 6);
                float rightY_S = SpUtils.getInstance().getInt("SM_Y_R", 10);
                float leftR_S = SpUtils.getInstance().getInt("SM_R_L", 11);
                float rightR_S = SpUtils.getInstance().getInt("SM_R_R", 20);
                if ((value < rightNormal_S) && (value > leftNormal_S)) {
                    tv_deviceStatus.setText("状态正常");
                } else if (value >= leftY_S && value < rightY_S) {
                    tv_deviceStatus.setText("黄色预警");
                } else {
                    tv_deviceStatus.setText("红色预警");
                }
                rsb_deviceNormalValue.setProgress(leftNormal_S, rightNormal_S);
                rsb_deviceYValue.setProgress(leftY_S, rightY_S);
                rsb_deviceRValue.setProgress(leftR_S, rightR_S);
                break;
        }
    }

    private void initWebView() {
        WebSettings settings = wv_details.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        //支持手势缩放
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setTextZoom(200);
        //不显示webView缩放按钮
//        settings.setDisplayZoomControls(false);
        //给webView添加js接口
//        wv_webView.addJavascriptInterface(JsBridge(this), "JsBridge")
        //设置自适应屏幕，两者合用
        //将图片调整到适合webView的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        wv_details.setInitialScale(80);
        //设置WebView可调试
        wv_details.setWebContentsDebuggingEnabled(true);

        //处理js中弹窗
        wv_details.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(InspectionDetailsActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm());
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
    }


    private void requestData() {
        HttpUtils.getInstance().get("http://121.196.173.248:9002/user/getSource", new ResultCallBack() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                ContentReceice contentReceice = gson.fromJson(data, ContentReceice.class);
                if (contentReceice.getStatus() == 200 && contentReceice.getData() != null) {
                    ContentReceice.Data resultData = contentReceice.getData();
                    switch (deviceName) {
                        case "实时温度":
                            value = resultData.getTemperature();
                            wv_details.loadUrl("javascript:changeData(" + value + ")");
                            break;
                        case "实时湿度":
                            value = resultData.getHumidity();
                            wv_details.loadUrl("javascript:changeData(" + Integer.parseInt(value) * 25 + ")");
                            break;
                        case "实时PM2.5":
                            value = resultData.getPm();
                            wv_details.loadUrl("javascript:changeData(" + value + ")");
                            break;
                        case "实时烟雾":
                            value = resultData.getSmoke();
                            wv_details.loadUrl("javascript:changeData(" + value + ")");
                            break;
                    }

                } else {
                    showToast(InspectionDetailsActivity.this, "请求数据失败");
                }
            }

            @Override
            public void failed(String msg) {
                showToast(InspectionDetailsActivity.this, msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 500);
    }


    @Override
    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        int left = (int) leftValue;
        int right = (int) rightValue;
        switch (view.getId()) {
            case R.id.rsb_deviceNormalValue:
//                int leftNormal = (int) rsb_deviceNormalValue.getMinProgress();
//                int rightNormal = (int) rsb_deviceNormalValue.getMaxProgress();
                switch (deviceName) {
                    case "实时温度":
                        SpUtils.getInstance().putInt("Tem_N_L", left);
                        SpUtils.getInstance().putInt("Tem_N_R", right);
                        LogUtils.e("存储实时温度成功");
                        break;
                    case "实时湿度":
                        SpUtils.getInstance().putInt("Hum_N_L", left);
                        SpUtils.getInstance().putInt("Hum_N_R", right);
                        break;
                    case "实时PM2.5":
                        SpUtils.getInstance().putInt("PM_N_L", left);
                        SpUtils.getInstance().putInt("PM_N_R", right);
                        break;
                    case "实时烟雾":
                        SpUtils.getInstance().putInt("SM_N_L", left);
                        SpUtils.getInstance().putInt("SM_N_R", right);
                        break;
                }
                break;

            case R.id.rsb_deviceYValue:
//                int leftY = (int) rsb_deviceYValue.getMinProgress();
//                int rightY = (int) rsb_deviceYValue.getMaxProgress();
                switch (deviceName) {
                    case "实时温度":
                        SpUtils.getInstance().putInt("Tem_Y_L", left);
                        SpUtils.getInstance().putInt("Tem_Y_R", right);
                        break;
                    case "实时湿度":
                        SpUtils.getInstance().putInt("Hum_Y_L", left);
                        SpUtils.getInstance().putInt("Hum_Y_R", right);
                        break;
                    case "实时PM2.5":
                        SpUtils.getInstance().putInt("PM_Y_L", left);
                        SpUtils.getInstance().putInt("PM_Y_R", right);
                        break;
                    case "实时烟雾":
                        SpUtils.getInstance().putInt("SM_Y_L", left);
                        SpUtils.getInstance().putInt("SM_Y_R", right);
                        break;
                }

                break;

            case R.id.rsb_deviceRValue:
//                int leftR = (int) rsb_deviceRValue.getMinProgress();
//                int rightR = (int) rsb_deviceRValue.getMaxProgress();
                switch (deviceName) {
                    case "实时温度":
                        SpUtils.getInstance().putInt("Tem_R_L", left);
                        SpUtils.getInstance().putInt("Tem_R_R", right);
                        break;
                    case "实时湿度":
                        SpUtils.getInstance().putInt("Hum_R_L", left);
                        SpUtils.getInstance().putInt("Hum_R_R", right);
                        break;
                    case "实时PM2.5":
                        SpUtils.getInstance().putInt("PM_R_L", left);
                        SpUtils.getInstance().putInt("PM_R_R", right);
                        break;
                    case "实时烟雾":
                        SpUtils.getInstance().putInt("SM_R_L", left);
                        SpUtils.getInstance().putInt("SM_R_R", right);
                        break;
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }

    @Override
    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }
}
