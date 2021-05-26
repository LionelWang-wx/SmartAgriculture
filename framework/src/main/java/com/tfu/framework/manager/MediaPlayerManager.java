package com.tfu.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

/**
 * 媒体播放类
 */
public class MediaPlayerManager {
    public static final int MEDIA_STATUS_PLAY = 0;
    public static final int MEDIA_STATUS_PAUSE = 1;
    public static final int MEDIA_STATUS_STOP = 2;
    private static final int PROGRESS = 100;
    public int MEDIA_STATUS = MEDIA_STATUS_STOP;
    public OnMusicProgressListener onMusicProgressListener;
    MediaPlayerManager mediaPlayerManager;
    MediaPlayer mediaPlayer;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    if (onMusicProgressListener != null) {
                        int currentPosition = getCurrentPosition();
                        int pos = (int) (((float) currentPosition / (float) getDuration()) * 100);
                        onMusicProgressListener.onProgress(currentPosition, pos);
                        handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    }
                    break;
            }
        }
    };

    public MediaPlayerManager() {
        mediaPlayer = new MediaPlayer();
    }


    //判断是否开始播放
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    //开始播放
    public void startPlay(AssetFileDescriptor path) {
        try {
            mediaPlayer.reset();
            //同步准备音视频资源
            mediaPlayer.setDataSource(path.getFileDescriptor(), path.getStartOffset(), path.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            handler.sendEmptyMessage(PROGRESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暂停播放
    public void pausePlay() {
        if (isPlaying()) {
            mediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            handler.removeMessages(PROGRESS);
        }
    }

    //继续播放
    public void continuePlay() {
        if (!isPlaying()) {
            mediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            handler.sendEmptyMessage(PROGRESS);
        }
    }


    //停止播放
    public void stopPlay() {
        if (isPlaying()) ;
        {
            mediaPlayer.stop();
            MEDIA_STATUS = MEDIA_STATUS_STOP;
            handler.removeMessages(PROGRESS);
        }
    }


    //是否循环
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    //跳转位置
    public void seekTo(int ms) {
        mediaPlayer.seekTo(ms);
    }

    //获取当前位置
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    //获取总时长
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    //监听播放结束
    public void setOnComplteionListener(MediaPlayer.OnCompletionListener listener) {
        mediaPlayer.setOnCompletionListener(listener);
    }

    //监听播放错误
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mediaPlayer.setOnErrorListener(listener);
    }

    //监听播放进度
    public void setOnProgressListener(OnMusicProgressListener listener) {
        onMusicProgressListener = listener;
    }

    public interface OnMusicProgressListener {
        void onProgress(int progress, int pos);
    }


}
