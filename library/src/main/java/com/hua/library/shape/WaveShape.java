package com.hua.library.shape;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hua.library.animation.AnimationBuilder;
import com.hua.library.rudiment.ShapeRudiment;

/**
 * Created by 华忠伟 on 2016/6/20.
 */
public class WaveShape extends ShapeRudiment {

    private float maxRadius;

    @Override
    protected void drawShape(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        if(getDrawBounds() != null) {
            maxRadius = getDrawBounds().width() / 2 - 10;
            int cx = getDrawBounds().centerX();
            int cy = getDrawBounds().centerY();
            canvas.drawCircle(cx,cy, maxRadius, paint);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float[] fractions = new float[] {0f, 1f};
        ValueAnimator animator = new AnimationBuilder(this)
                .scale(fractions, 0.1f, 1f)
                .alpha(fractions, 255, 0)
                .duration(3000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .build();
        return animator;
    }
}
