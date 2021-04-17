package com.tfu.framework.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tfu.framework.BuildConfig;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by WangXi on 2021/03/29
 * Log不光作为日志的打印，还可以记录日志 ——> File
 */
public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void v(String text) {
        if (BuildConfig.LOG_DEG && !TextUtils.isEmpty(text))
            Log.v(BuildConfig.LOG_TAG, text);
    }

    public static void d(String text) {
        if (BuildConfig.LOG_DEG && !TextUtils.isEmpty(text))
            Log.d(BuildConfig.LOG_TAG, text);
    }

    public static void i(String text) {
        if (BuildConfig.LOG_DEG && !TextUtils.isEmpty(text))
            Log.i(BuildConfig.LOG_TAG, text);
    }

    public static void w(String text) {
        if (BuildConfig.LOG_DEG && !TextUtils.isEmpty(text))
            Log.w(BuildConfig.LOG_TAG, text);
    }

    public static void e(String text) {
        if (BuildConfig.LOG_DEG && !TextUtils.isEmpty(text))
            Log.e(BuildConfig.LOG_TAG, text);
    }

    /**
     * 写入内存卡Log日志
     *
     * @param text
     */
    public static void printLogToFile(Context context, String text) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        /**
         *
         * DIRECTORY MUSIC 目录音乐
         * DIRECTORY PODCASTS 目录播客
         * DIRECTORY RINGTONES 目录铃声
         * DIRECTORY ALARMS 目录警报
         * DIRECTORY NOTIFICATIONS 目录通知
         * DIRECTORY PICTURES 目录图片
         * DIRECTORY MOVIES 目录电影
         *
         */
        //文件操作，需要进行异常处理
        try {
            //文件路径
            //获取路径方法一
            //String filePath = Environment.getExternalStorageDirectory().getPath()+"/Meet/";
            //获取路径方法二
            String filePath = context.getExternalFilesDir("MeetLog").getPath();
            String fileName = "/Meet.log";
            // 时间 + 内容
            String log = mSimpleDateFormat.format(new Date()) + ": " + text + "\n";
            //检查父路径
            //java.io.File.mkdir()：只能创建一级目录，且父目录必须存在，否则无法成功创建一个目录
            //java.io.File.mkdirs()：可以创建多级目录，父目录不一定存在
            File fileGroup = new File(filePath);
            if (!fileGroup.exists()) {
                fileGroup.mkdirs();
            }
            //创建文件
            File fileChild = new File(filePath + fileName);
            if (!fileChild.exists()) {
                fileChild.createNewFile();
            }
            /**
             * @
             */
            fos = new FileOutputStream(filePath + fileName, true);
            //编码问题 GBK 正确的存入中文
            bos = new BufferedOutputStream(fos);
            ow = new OutputStreamWriter(bos, Charset.forName("GBK"));
            bw = new BufferedWriter(ow);
            bw.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            e(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            e(e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    fos.close();
                    bos.close();
                    ow.close();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    e(e.getMessage());
                }
            }

        }

    }


}
