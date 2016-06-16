package com.hua.library.rudiment;

import android.animation.ValueAnimator;
import android.graphics.Canvas;

import com.hua.library.animation.AnimationUtils;

/**
 * Created by 华忠伟 on 2016/6/15.
 */
public abstract class ContainerRudiment extends RudimentDrawable {

    private RudimentDrawable[] mRudimentDrawables;
    private int color;

    public ContainerRudiment() {
        mRudimentDrawables = onCreateChild();
        initCallBack();
        onChildCreated(mRudimentDrawables);
    }

    protected abstract RudimentDrawable[] onCreateChild();

    protected void onChildCreated(RudimentDrawable[] rudimentDrawables){

    }

    private void initCallBack() {
        if(mRudimentDrawables != null) {
            for (RudimentDrawable rudiment : mRudimentDrawables) {
                rudiment.setCallback(this);
            }
        }
    }

    public int getChildCount() {
        return mRudimentDrawables != null ? mRudimentDrawables.length : 0;
    }

    public RudimentDrawable getChildAt(int index) {
        if(mRudimentDrawables == null) {
            return null;
        }
        if(index < 0 || index >= mRudimentDrawables.length) {
            return null;
        }
        return mRudimentDrawables[index];
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        for(RudimentDrawable rudiment : mRudimentDrawables) {
            rudiment.setColor(color);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        drawChild(canvas);
    }

    protected abstract void drawChild(Canvas canvas);

    @Override
    public void start() {
        super.start();
        AnimationUtils.start(mRudimentDrawables);
    }

    @Override
    public void stop() {
        super.stop();
        AnimationUtils.stop(mRudimentDrawables);
    }

    @Override
    public boolean isRunning() {
        return super.isRunning() || AnimationUtils.isRunning(mRudimentDrawables);
    }
}
