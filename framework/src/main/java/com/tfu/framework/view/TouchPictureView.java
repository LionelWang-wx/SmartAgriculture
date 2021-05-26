package com.tfu.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tfu.framework.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 自定义图片验证View
 */
public class TouchPictureView extends View {

    //背景
    private Bitmap bgBitmap;
    //背景画笔
    private Paint paintBg;

    //空白块
    private Bitmap nullBitmap;
    //空白块画笔
    private Paint paintNull;

    //移动方块
    private Bitmap moveBitmap;
    //移动画笔
    private Paint paintMove;

    //View的宽高
    private int mWidth;
    private int mHeight;

    //方块大小
    private int CARD_SIZE = 200;
    //方块坐标
    private int LINE_W, LINE_H = 0;

    //移动方块横坐标
    private int moveX = 200;
    //误差值
    private int errorValues = 10;

    private OnViewResultListener onViewResultListener;

    public TouchPictureView(Context context) {
        super(context);
        init();
    }

    public TouchPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //得到OnViewResultListener接口的实例
    public void setOnViewResultListener(OnViewResultListener onViewResultListener) {
        this.onViewResultListener = onViewResultListener;
    }

    private void init() {
        paintBg = new Paint();
        paintNull = new Paint();
        paintMove = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawNullCard(canvas);
        drawMoveCard(canvas);
    }

    /**
     * 绘制移动方块
     *
     * @param canvas
     */
    private void drawMoveCard(Canvas canvas) {
        //截取空白块位置坐标的Bitmap图像
        moveBitmap = Bitmap.createBitmap(bgBitmap, LINE_W, LINE_H, CARD_SIZE, CARD_SIZE);
        //绘制在View上 如果使用LINE_W, LINE_H，那会和空白块重叠
        canvas.drawBitmap(moveBitmap, moveX, LINE_H, paintMove);
    }

    /**
     * 绘制空白方块
     *
     * @param canvas
     */
    private void drawNullCard(Canvas canvas) {
        //1.获取图片
        nullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_null_card);
        //2.计算值
        CARD_SIZE = nullBitmap.getWidth();
        //99 / 3 = 33 * 2 = 66
        LINE_W = mWidth / 2;
        //除以2并不是中心
        LINE_H = mHeight / 2 - (CARD_SIZE / 2);
        //3.绘制
        canvas.drawBitmap(nullBitmap, LINE_W, LINE_H, paintNull);
    }


    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //1.获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg);
        //2.创建一个空的Bitmap Bitmap w h = View w h
        bgBitmap = bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //3.将图片绘制到空的Bitmap
        Canvas bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawBitmap(bitmap, null, new Rect(0, 0, mWidth, mHeight), paintBg);
        //4.将bgBitmap绘制到View上
        canvas.drawBitmap(bgBitmap, null, new Rect(0, 0, mWidth, mHeight), paintBg);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断点击的坐标是否是方块的内部，如果是就可以拖动
                //onTouch return true down-move-up三个动作 返回false 监听down
                if (!((event.getX() > moveX && event.getX() < (moveX + CARD_SIZE)) && (event.getY() > LINE_H && event.getY() < (LINE_H + CARD_SIZE)))) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //防止越界
                if (event.getX() > 0 && event.getX() < (mWidth - CARD_SIZE)) {
                    moveX = (int) event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //验证结果
                if (event.getX() > (LINE_W - errorValues) && event.getX() < (LINE_W + errorValues)) {
                    if (onViewResultListener != null) {
                        onViewResultListener.onResult(true);
                        //成功重置
                        moveX = 200;
//                            invalidate();
                    }
                } else {
                    if (onViewResultListener != null) {
                        onViewResultListener.onResult(false);
                        //失败重置
                        moveX = 200;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    //View结果监听
    public interface OnViewResultListener {
        void onResult(boolean result);
    }

}
