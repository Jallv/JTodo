package com.jal.todo.widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.jal.todo.R;

import androidx.annotation.Nullable;

public class FocusCountDownView extends View {
    public FocusCountDownView(Context context) {
        this(context, null);
    }

    public FocusCountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 底环画笔
     */
    private Paint mRingPaint;
    private Paint mLightPaint;
    private int mLightRadius;
    private int darkBlueRadius;
    private int darkBlueCx;
    private int darkBlueCy;
    private int darkBlueColor = 0xff0029ff;
    private RectF lightRedRect;
    private int lightRedColor = 0xffd712fc;
    private RectF whiteRect;
    private int whiteColor = 0xffffffff;
    private RectF lightBlueRect;
    private int lightBlueColor = 0xff1ad7ff;
    private RectF purpleRect;
    private int purpleColor = 0xff5a00ff;
    private int centerCircleRadius;
    private int mRadius;

    private void initPaint() {
        if (mRingPaint == null) {
            int w = getWidth();
            int h = getHeight();
            mRadius = (int) getResources().getDimension(R.dimen.dp_96);

            mRingPaint = new Paint();
            mRingPaint.setAntiAlias(true);

            mRingPaint.setColor(0xFF0029FF);

            mLightPaint = new Paint();
            mLightPaint.setAntiAlias(true);// 抗锯齿效果
            mLightPaint.setColor(0xFF5A00FF);
            mLightRadius = (int) getResources().getDimension(R.dimen.dp_105);
            mLightPaint.setMaskFilter(new BlurMaskFilter(mLightRadius, BlurMaskFilter.Blur.OUTER));

            darkBlueRadius = (int) getResources().getDimension(R.dimen.dp_105);//1.7619
            darkBlueCx = w / 2;
            darkBlueCy = h / 2;

            int lightW = (int) getResources().getDimension(R.dimen.dp_210);
            int lightH = (int) getResources().getDimension(R.dimen.dp_187);
            int paddingX = (w - lightW) / 2;
            int paddingY = (h - lightH) / 2;
            lightRedRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = (int) getResources().getDimension(R.dimen.dp_166);
            lightH = (int) getResources().getDimension(R.dimen.dp_147);
            paddingX = (w - lightW) / 2;
            paddingY = (h - lightH) / 2;
            whiteRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = (int) getResources().getDimension(R.dimen.dp_206);
            lightH = (int) getResources().getDimension(R.dimen.dp_185);
            paddingX = (w - lightW) / 2;
            paddingY = (h - lightH) / 2;
            lightBlueRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = (int) getResources().getDimension(R.dimen.dp_195);
            lightH = (int) getResources().getDimension(R.dimen.dp_200);
            paddingX = (w - lightW) / 2;
            paddingY = (h - lightH) / 2;
            purpleRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            mTextPaint = new Paint();
            mTextSize = getResources().getDimension(R.dimen.sp_32);
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setAntiAlias(true);
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            mTextBaseLine = getHeight() / 2f - metrics.descent + (metrics.bottom - metrics.top) / 2;
        }
    }

    private int lightRedRotate = -10;
    private int whiteRotate = 10;
    private int lightBlueRotate = 50;
    private int purpleRotate = 30;

    private void changeRotate() {
//        lightRedRotate += 2;
//        whiteRotate += 3;
//        lightBlueRotate += 4;
//        purpleRotate += 5;
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, mLightRadius, mLightPaint);
        mRingPaint.setColor(darkBlueColor);
        canvas.drawCircle(darkBlueCx, darkBlueCy, darkBlueRadius, mRingPaint);

        canvas.save();
        mRingPaint.setColor(lightRedColor);
        int translateY;
        int translateX = (int) getResources().getDimension(R.dimen.dp_2);
        canvas.translate(-translateX, -translateX);
        canvas.rotate(lightRedRotate, cx, cy);
        canvas.drawOval(lightRedRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(whiteColor);
        translateX = (int) getResources().getDimension(R.dimen.dp_15);
        canvas.translate(-translateX, -translateX);
        canvas.rotate(whiteRotate, cx, cy);
        canvas.drawOval(whiteRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(lightBlueColor);
        translateX = (int) getResources().getDimension(R.dimen.dp_2);
        canvas.translate(translateX, 0);
        canvas.rotate(lightBlueRotate, cx, cy);
        canvas.drawOval(lightBlueRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(purpleColor);
        translateY = (int) getResources().getDimension(R.dimen.dp_2);
        canvas.translate(0, translateY);
        canvas.rotate(purpleRotate, cx, cy);
        canvas.drawOval(purpleRect, mRingPaint);
        canvas.restore();

        mRingPaint.setColor(0xFF1E1C42);

        canvas.drawCircle(cx, cy, mRadius, mRingPaint);

        int minute = (int) (mMillisUntilFinished / 1000 / 60);
        int second = (int) (mMillisUntilFinished / 1000 % 60);
        String text = (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
        mTextPaint.measureText(text);
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, (getWidth() - textWidth) / 2, mTextBaseLine, mTextPaint);
        changeRotate();
    }

    private CountDownTimer mCountDownTimer;
    private long mResetMillis;
    private long mTotalMillis;
    private long mMillisUntilFinished;
    /**
     * 文字颜色
     */
    private int mTextColor = 0xFFFFFFFF;
    /**
     * 文字大小
     */
    private float mTextSize;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;
    /**
     * Text的基线
     */
    private float mTextBaseLine;

    public void setTime(long millis) {
        reset();
        mResetMillis = millis;
        mTotalMillis = millis;
        mMillisUntilFinished = mTotalMillis;
        invalidate();
    }

    public void start() {
        mCountDownTimer = new CountDownTimer(mTotalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished = millisUntilFinished;
                invalidate();
            }

            @Override
            public void onFinish() {
                reset();
            }
        };
        mCountDownTimer.start();
    }

    public void reset() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
            mTotalMillis = mResetMillis;
            mMillisUntilFinished = mTotalMillis;
            invalidate();
        }
    }
}
