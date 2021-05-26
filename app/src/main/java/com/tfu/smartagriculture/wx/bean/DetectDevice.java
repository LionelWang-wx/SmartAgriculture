package com.tfu.smartagriculture.wx.bean;

/**
 * Created on by WangXi 15/05/2021.
 * 网络数据解析类
 * 实现了Serializable接口，方便Intent传递
 */
import java.io.Serializable;

public class DetectDevice implements Serializable {
    //检测的设备id
    int detectDeviceId;
    //检测图标
    int iv_detectIcon;
    //检测类型名
    String tv_detectName;
    //检测值
    String tv_detectValue;

    public DetectDevice(int detectDeviceId, int iv_detectIcon, String tv_detectName, String tv_detectValue) {
        this.detectDeviceId = detectDeviceId;
        this.iv_detectIcon = iv_detectIcon;
        this.tv_detectName = tv_detectName;
        this.tv_detectValue = tv_detectValue;
    }

    public int getDetectDeviceId() {
        return detectDeviceId;
    }

    public void setDetectDeviceId(int detectDeviceId) {
        this.detectDeviceId = detectDeviceId;
    }

    public int getIv_detectIcon() {
        return iv_detectIcon;
    }

    public void setIv_detectIcon(int iv_detectIcon) {
        this.iv_detectIcon = iv_detectIcon;
    }

    public String getTv_detectName() {
        return tv_detectName;
    }

    public void setTv_detectName(String tv_detectName) {
        this.tv_detectName = tv_detectName;
    }

    public String getTv_detectValue() {
        return tv_detectValue;
    }

    public void setTv_detectValue(String tv_detectValue) {
        this.tv_detectValue = tv_detectValue;
    }
}
