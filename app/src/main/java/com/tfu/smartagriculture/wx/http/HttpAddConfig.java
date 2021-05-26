package com.tfu.smartagriculture.wx.http;

/**
 * Created on by WangXi 15/05/2021.
 * Api常量类
 */
public class HttpAddConfig {

    public static final String token = "0c2386892c86cb95eab34da5f1c75ffed2ed789ad346da0576ce78ac497ce922";

    public static final String BASEURL = "http://121.196.173.248:9002";

    //登录
    public static String getLoginUrl() {
        return BASEURL + "/user/login?";
    }

    //注册
    public static String getRegisteredUrl() {
        return BASEURL + "/user/zhuce?";
    }

}
