package com.tfu.smartagriculture.wx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created on by WangXi 15/05/2021.
 * 方案数据
 */
public class PlanData {

    int status;
    String msg;
    List<Plan> data;

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

    public List<Plan> getData() {
        return data;
    }

    public void setData(List<Plan> data) {
        this.data = data;
    }

    public class Plan implements Serializable {

        //方案id
        int id;
        //作者名
        String accountName;
        //方案图片
        int iv_icon;
        //方案标题
        String title;
        //方案浏览量
        String tv_pageViews;
        //方案内容
        String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getIv_icon() {
            return iv_icon;
        }

        public void setIv_icon(int iv_icon) {
            this.iv_icon = iv_icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTv_pageViews() {
            return tv_pageViews;
        }

        public void setTv_pageViews(String tv_pageViews) {
            this.tv_pageViews = tv_pageViews;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
