package com.hua.library.rudiment;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 华忠伟 on 2016/6/15.
 */
public abstract class ShapeRudiment extends RudimentDrawable {

    private Paint mPaint;
    private int mUseColor;
    private int mBaseColor;

    public ShapeRudiment() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mUseColor);
    }

    @Override
    public int getColor() {
        return mBaseColor;
    }

    @Override
    public void setColor(int color) {
        this.mBaseColor = color;
        updateUseColor();
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        updateUseColor();
    }

    public int getUseColor() {
        return mUseColor;
    }

    private void updateUseColor() {
        //默认是FF，即使外部设置了颜色有alpha(0x804fc3f7), 对这个没有影响，出发使用setAlpha进行设置
        int alpha = getAlpha();
        alpha += alpha >> 7;
        final int baseAlpha = mBaseColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        mUseColor = (mBaseColor << 8 >>> 8) | (useAlpha << 24);
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        mPaint.setColor(mUseColor);
        drawShape(canvas, mPaint);
    }

    protected abstract void drawShape(Canvas canvas, Paint paint);
}