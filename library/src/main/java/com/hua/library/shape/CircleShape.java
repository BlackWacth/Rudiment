package com.hua.library.shape;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.hua.library.animation.AnimationBuilder;
import com.hua.library.rudiment.ShapeRudiment;

/**
 * Created by 华忠伟 on 2016/6/16.
 */
public class CircleShape extends ShapeRudiment{

    public static final long duration = 1000;

    @Override
    protected void drawShape(Canvas canvas, Paint paint) {
        if(getDrawBounds() != null) {
            int radius = Math.min(getDrawBounds().width(), getDrawBounds().height()) / 2;
            int cx = getDrawBounds().centerX();
            int cy = getDrawBounds().centerY();
            canvas.drawCircle(cx,cy, radius, paint);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float[] fractions = new float[] {0f, 0.5f, 1f};
        AnimationBuilder builder = new AnimationBuilder(this);
        builder.easeInOut(fractions)
                .scale(fractions, 0.2f, 1f, 0.2f)
                .color(fractions, 0xFFB3E5FC, 0xFF00695C, 0xFFB3E5FC)
                .duration(duration)
                .repeatCount(ValueAnimator.INFINITE);
        return builder.build();
    }
}
