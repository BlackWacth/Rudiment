package com.hua.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 华忠伟 on 2016/6/20.
 */
public class WaveView extends View implements Runnable{

    private Interpolator mInterpolator = new LinearInterpolator();
    private long mDuration = 2000;
    private float mInitialRadius = 30f;
    private float mMaxRadius;
    private float mMaxRadiusRate = 0.85f;
    private int mSpeed = 500;

    private List<Circle> mCircles = new ArrayList<>();
    private boolean mIsRunning;
    private boolean mMaxRadiusSet;

    private Paint mPaint;
    private long mLastCreateTime;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(!mMaxRadiusSet) {
            mMaxRadius = Math.min(w - getPaddingLeft() - getPaddingRight(), h - getPaddingTop() - getPaddingBottom()) * mMaxRadiusRate / 2.0f;
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        setStyle(Paint.Style.STROKE);
    }

    public void setStyle(Paint.Style style) {
        mPaint.setStyle(style);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setMaxRadiusRate(float maxRadiusRate) {
        mMaxRadiusRate = maxRadiusRate;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        if(mInterpolator == null) {
            mInterpolator = new LinearInterpolator();
        }
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setInitialRadius(float initialRadius) {
        mInitialRadius = initialRadius;
        mMaxRadiusSet = true;
    }

    public void setMaxRadius(float maxRadius) {
        mMaxRadius = maxRadius;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    private void newCircle() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - mLastCreateTime < mSpeed) {
            return;
        }
        Circle circle = new Circle();
        mCircles.add(circle);
        invalidate();
        mLastCreateTime = currentTime;
    }

    public void start() {
        if(!mIsRunning) {
            mIsRunning = true;
            this.run();
        }
    }

    public void stop() {
        mIsRunning = false;
    }

    @Override
    public void run() {
        newCircle();
        postDelayed(this, mSpeed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<Circle> iterator = mCircles.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            if(System.currentTimeMillis() - circle.mCreateTime < mDuration) {
                mPaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, circle.getCurrentRadius(), mPaint);
            } else {
                iterator.remove();
            }
        }
        if(mCircles.size() > 0) {
            postInvalidateDelayed(10);
        }
    }

    private class Circle {
        private long mCreateTime;

        public Circle() {
            this.mCreateTime = System.currentTimeMillis();
        }

        public float timeOfDuration() {
            long currentTime = System.currentTimeMillis();
            return (currentTime - mCreateTime) * 1.0f / mDuration;
        }

        public int getAlpha() {
            float percent = timeOfDuration();
            return (int) ((1.0f - mInterpolator.getInterpolation(percent)) * 255);
        }

        public float getCurrentRadius() {
            float percent = timeOfDuration();
            return mInitialRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mInitialRadius);
        }
    }
}
