package com.hua.library.shape.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.hua.library.rudiment.ContainerRudiment;
import com.hua.library.rudiment.RudimentDrawable;
import com.hua.library.shape.WaveShape;

/**
 * Created by 华忠伟 on 2016/6/17.
 */
public class Wave extends ContainerRudiment {

    @Override
    protected RudimentDrawable[] onCreateChild() {
        WaveShape[] waveShapes = new WaveShape[5];
        for(int i = 0; i < waveShapes.length; i++) {
            WaveShape wave = new WaveShape();
            wave.setAnimationDelay(100);
            waveShapes[i] = wave;
        }
        return waveShapes;
    }

    @Override
    protected void drawChild(Canvas canvas) {
        for(int i = 0; i < getChildCount(); i++) {
            int count = canvas.save();
            getChildAt(i).draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        for(int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setDrawBounds(bounds);
        }
    }
}
