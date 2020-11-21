package com.jal.todo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.jal.todo.R;

import androidx.annotation.Nullable;

/**
 * 自定义进度圆环
 */
public class CountDownView extends View {
    /**
     * 圆环宽度,默认半径的1／5
     */
    private float mRingWidth;
    /**
     * 圆环颜色,默认 #CBCBCB
     */
    private int mRingColor;

    /**
     * 圆环半径,默认：Math.min(getHeight()/2,getWidth()/2)
     */
    private float mRadius;
    /**
     * 进度颜色
     */
    private int mProgressRingColor;
    /**
     * 文字颜色
     */
    private int mTextColor;
    /**
     * 文字大小
     */
    private float mTextSize;
    /**
     * 底环画笔
     */
    private Paint mRingPaint;
    /**
     * 内环画笔
     */
    private Paint mProgressRingPaint;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingWidth = typedArray.getDimension(R.styleable.CountDownView_ring_width, 0);
        mRingColor = typedArray.getColor(R.styleable.CountDownView_ring_color, Color.parseColor("#CBCBCB"));
        mRadius = typedArray.getDimension(R.styleable.CountDownView_radius, 0);
        mProgressRingColor = typedArray.getColor(R.styleable.CountDownView_progress_ring_color, Color.parseColor("#000000"));
        mTextColor = typedArray.getColor(R.styleable.CountDownView_text_color, Color.parseColor("#000000"));
        mTextSize = typedArray.getDimension(R.styleable.CountDownView_text_size, 0);
        init();
    }

    /**
     * Text的基线
     */
    private float mTextBaseLine;

    /**
     * 初始化
     */
    private void init() {
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);// 抗锯齿效果
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);// 背景
        mRingPaint.setStrokeWidth(mRingWidth);

        mProgressRingPaint = new Paint();
        mProgressRingPaint.setAntiAlias(true);// 抗锯齿效果
        mProgressRingPaint.setStyle(Paint.Style.STROKE);
        mProgressRingPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mProgressRingPaint.setStrokeWidth(mRingWidth);
        mProgressRingPaint.setColor(mProgressRingColor);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
    }

    private CountDownTimer mCountDownTimer;
    private long mResetMillis;
    private long mTotalMillis;
    private long mMillisUntilFinished;

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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
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

    @Override
    public void onDraw(Canvas canvas) {
        if (mTextBaseLine == 0) {
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            mTextBaseLine = getHeight() / 2f - metrics.descent + (metrics.bottom - metrics.top) / 2;
        }
        // 圆心坐标,当前View的中心
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        // 底环
        canvas.drawCircle(x, y, mRadius, mRingPaint);
        int paddingX = (int) ((getWidth() - mRadius * 2) / 2);
        int paddingY = (int) ((getHeight() - mRadius * 2) / 2);
        int angle = (int) (360f * (mTotalMillis - mMillisUntilFinished) / mTotalMillis);
        int minute = (int) (mMillisUntilFinished / 1000 / 60);
        int second = (int) (mMillisUntilFinished / 1000 % 60);
        String text = (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
        canvas.drawArc(new RectF(paddingX, paddingY, getWidth() - paddingX, getHeight() - paddingY), -90, angle, false, mProgressRingPaint);
        mTextPaint.measureText(text);
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, (getWidth() - textWidth) / 2, mTextBaseLine, mTextPaint);
    }

}