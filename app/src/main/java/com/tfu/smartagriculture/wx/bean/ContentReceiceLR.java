package com.tfu.smartagriculture.wx.bean;
/**
 * Created on by WangXi 15/05/2021.
 * 网络数据解析类
 */
public class ContentReceiceLR {

    int status;
    String msg;
    String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
