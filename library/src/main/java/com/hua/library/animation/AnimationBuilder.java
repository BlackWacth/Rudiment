package com.hua.library.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.hua.library.evaluator.ArgbEvaluator;
import com.hua.library.interpolator.KeyFrameInterpolator;
import com.hua.library.rudiment.RudimentDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 华忠伟 on 2016/6/16.
 */
public class AnimationBuilder {

    private RudimentDrawable mRudimentDrawable;
    private List<PropertyValuesHolder> mPropertyValuesHolders;
    private Interpolator mInterpolator;
    private int repeatCount = Animation.INFINITE;
    private long duration = 500;

    public AnimationBuilder(RudimentDrawable rudimentDrawable) {
        mRudimentDrawable = rudimentDrawable;
        if(mPropertyValuesHolders == null) {
            mPropertyValuesHolders = new ArrayList<>();
        }else {
            mPropertyValuesHolders.clear();
        }
    }

    public AnimationBuilder scale(float[] fractions, float... scale) {
        holderFloat(RudimentDrawable.SCALE, fractions, scale);
        return this;
    }

    public AnimationBuilder scaleX(float[] fractions, float... scaleX) {
        holderFloat(RudimentDrawable.SCALE_X, fractions, scaleX);
        return this;
    }

    public AnimationBuilder scaleY(float[] fractions, float... scaleY) {
        holderFloat(RudimentDrawable.SCALE_Y, fractions, scaleY);
        return this;
    }

    public AnimationBuilder rotate(float[] fractions, float... rotate) {
        holderFloat(RudimentDrawable.ROTATE, fractions, rotate);
        return this;
    }

    public AnimationBuilder rotateX(float[] fractions, float... rotateX) {
        holderFloat(RudimentDrawable.ROTATE_X, fractions, rotateX);
        return this;
    }

    public AnimationBuilder rotateY(float[] fractions, float... rotateY) {
        holderFloat(RudimentDrawable.ROTATE_Y, fractions, rotateY);
        return this;
    }

    public AnimationBuilder translateX(float[] fractions, float... translateX) {
        holderFloat(RudimentDrawable.TRANSLATE_X, fractions, translateX);
        return this;
    }

    public AnimationBuilder translateY(float[] fractions, float... translateY) {
        holderFloat(RudimentDrawable.TRANSLATE_Y, fractions, translateY);
        return this;
    }

    public AnimationBuilder translateXPercentage(float[] fractions, float... translateXPercentage) {
        holderFloat(RudimentDrawable.TRANSLATE_X_PERCENTAGE, fractions, translateXPercentage);
        return this;
    }

    public AnimationBuilder translateYPercentage(float[] fractions, float... translateYPercentage) {
        holderFloat(RudimentDrawable.TRANSLATE_Y_PERCENTAGE, fractions, translateYPercentage);
        return this;
    }

    public AnimationBuilder alpha(float[] fractions, int... alpha) {
        holderInt(RudimentDrawable.ALPHA, fractions, alpha);
        return this;
    }

    public AnimationBuilder color(float[] fractions, int... color) {
        holderArgb(RudimentDrawable.COLOR, fractions, color);
        return this;
    }

    public AnimationBuilder interpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
        return this;
    }

    public AnimationBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    public AnimationBuilder repeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public AnimationBuilder easeInOut(float... fractions) {
        interpolator(KeyFrameInterpolator.easeInOut(fractions));
        return this;
    }

    private void ensurePair(int fractionsLength, int valuesLength) {
        if(fractionsLength != valuesLength) {
            throw new IllegalStateException(String.format(Locale.getDefault(),
                    "The fractions.length must equal values.length, " +
                            "fraction.length[%d], values.length[%d]",
                    fractionsLength, valuesLength));
        }
    }

    /**
     * 关键帧动画，对属性在动画时间内的节点给指定的值
     * @param property 属性
     * @param fractions 时间节点的百分比，范围0~1
     * @param values 值
     * @return
     */
    public PropertyValuesHolder holderInt(Property property, float[] fractions, int[] values) {
        ensurePair(fractions.length, values.length);
        Keyframe[] keyframes = new Keyframe[fractions.length];
        for(int i = 0; i < fractions.length; i++) {
            keyframes[i] = Keyframe.ofInt(fractions[i], values[i]);
        }
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe(property, keyframes);
        mPropertyValuesHolders.add(holder);
        return holder;
    }

    /**
     * 关键帧动画，对属性在动画时间内的节点给指定的值
     * @param property 属性
     * @param fractions 时间节点的百分比，范围0~1
     * @param values 值
     * @return
     */
    public PropertyValuesHolder holderFloat(Property property, float[] fractions, float[] values) {
        ensurePair(fractions.length, values.length);
        Keyframe[] keyframes = new Keyframe[fractions.length];
        for(int i = 0; i < fractions.length; i++) {
            keyframes[i] = Keyframe.ofFloat(fractions[i], values[i]);
        }
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe(property, keyframes);
        mPropertyValuesHolders.add(holder);
        return holder;
    }

    /**
     * 改变ARGB，改变颜色
     * 关键帧动画，对属性在动画时间内的节点给指定的值
     * @param property 属性
     * @param fractions 时间节点的百分比，范围0~1
     * @param values 值
     * @return
     */
    public PropertyValuesHolder holderArgb(Property property, float[] fractions, int[] values) {
        ensurePair(fractions.length, values.length);
        Keyframe[] keyframes = new Keyframe[fractions.length];
        for(int i = 0; i < fractions.length; i++) {
            keyframes[i] = Keyframe.ofInt(fractions[i], values[i]);
        }
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe(property, keyframes);
        holder.setEvaluator(ArgbEvaluator.getInstance());
        mPropertyValuesHolders.add(holder);
        return holder;
    }

    public ObjectAnimator build() {
        PropertyValuesHolder[] holders = new PropertyValuesHolder[mPropertyValuesHolders.size()];
        holders = mPropertyValuesHolders.toArray(holders);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mRudimentDrawable, holders);
        objectAnimator.setDuration(duration);
        objectAnimator.setRepeatCount(repeatCount);
        objectAnimator.setInterpolator(mInterpolator);
        return objectAnimator;
    }
}
