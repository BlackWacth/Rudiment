package com.hua.library.shape;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.hua.library.rudiment.ContainerRudiment;
import com.hua.library.rudiment.RudimentDrawable;

/**
 * Created by 华忠伟 on 2016/6/16.
 */
public abstract class CircleContainer extends ContainerRudiment {

    @Override
    protected void drawChild(Canvas canvas) {
        for(int i = 0; i < getChildCount(); i++) {
            RudimentDrawable rudiment = getChildAt(i);
            int count = canvas.save();
            float degrees = (float) ((360.0 / getChildCount()) * i);
            canvas.rotate(degrees, getBounds().centerX(), getBounds().centerY());
            rudiment.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //在矩形中心处以最短边截取一个正方形。
        bounds = clipSquare(bounds);
        //此处以正方形的内切圆为圆形容器。
        //然后再以正方形的内切圆的内切小圆位item.

        //计算半径
        int radius = (int) (bounds.width() * Math.PI / 3.0f /getChildCount());
        Log.i("hzw", "-------------radius = " + radius);
        //以下确定的位置为正方形中心正上方的小内切圆
        int left = bounds.centerX() - radius;
        int right = bounds.centerX() + radius;
        int top = bounds.top;
        int bottom = top + radius * 2;

        for(int i = 0; i < getChildCount(); i++) {
            RudimentDrawable rudiment = getChildAt(i);
            rudiment.setDrawBounds(left, top, right, bottom);
        }
    }
}
