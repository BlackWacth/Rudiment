package com.hua.library.rudiment;

import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;

import com.hua.library.animation.AnimationUtils;
import com.hua.library.property.FloatProperty;
import com.hua.library.property.IntProperty;

/**
 * Created by 华忠伟 on 2016/6/15.
 */
public abstract class RudimentDrawable extends Drawable implements Drawable.Callback, Animatable, ValueAnimator.AnimatorUpdateListener {

    /**缩放 */
    private float scale = 1f;
    private float scaleX = 1f;
    private float scaleY = 1f;

    /**基准点，以(pivotX, pivotY)为不动点，进行缩放，旋转等操作 */
    private float pivotX;
    private float pivotY;

    /**旋转 */
    private float rotate;
    private float rotateX;
    private float rotateY;

    /**平移 */
    private float translateX;
    private float translateY;

    /**平移百分比 */
    private float translateXPercentage;
    private float translateYPercentage;

    /**透明度 */
    private int alpha = 255;

    /**颜色 */
    protected int color;

    private ValueAnimator mValueAnimator;
    private int mAnimationDelay;

    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    private Rect mDrawBounds = ZERO_BOUNDS_RECT;

    private Camera mCamera;
    private Matrix mMatrix;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        setScaleX(scale);
        setScaleY(scale);
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

    public float getRotateX() {
        return rotateX;
    }

    public void setRotateX(float rotateX) {
        this.rotateX = rotateX;
    }

    public float getRotateY() {
        return rotateY;
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }

    public float getTranslateXPercentage() {
        return translateXPercentage;
    }

    public void setTranslateXPercentage(float translateXPercentage) {
        this.translateXPercentage = translateXPercentage;
    }

    public float getTranslateYPercentage() {
        return translateYPercentage;
    }

    public void setTranslateYPercentage(float translateYPercentage) {
        this.translateYPercentage = translateYPercentage;
    }

    public int getAnimationDelay() {
        return mAnimationDelay;
    }

    public RudimentDrawable setAnimationDelay(int animationDelay) {
        mAnimationDelay = animationDelay;
        return this;
    }

    public Rect getDrawBounds() {
        return mDrawBounds;
    }

    public void setDrawBounds(Rect drawBounds) {
        setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom);
    }

    public void setDrawBounds(int left, int top, int right, int bottom) {
        this.mDrawBounds = new Rect(left, top, right, bottom);
        setPivotX(getDrawBounds().centerX());
        setPivotY(getDrawBounds().centerY());
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public abstract int getColor();

    public abstract void setColor(int color);

    /**
     * 创建动画
     * @return
     */
    public abstract ValueAnimator onCreateAnimation();

    /**
     * 获取动画对象，并添加UpdateListener监听器，和动画延迟时间
     * @return
     */
    public ValueAnimator obtainAnimation() {
        if(mValueAnimator == null) {
            mValueAnimator = onCreateAnimation();
        }
        if(mValueAnimator != null) {
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.setStartDelay(mAnimationDelay);
        }
        return mValueAnimator;
    }

    @Override
    public void start() {
        if(AnimationUtils.isStarted(mValueAnimator)) {
            return ;
        }
        mValueAnimator = obtainAnimation();
        if(mValueAnimator == null) {
            return ;
        }

        AnimationUtils.start(mValueAnimator);
        invalidateSelf();
    }

    @Override
    public void stop() {
        if(AnimationUtils.isStarted(mValueAnimator)) {
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.end();
            reset();
        }
    }

    protected void reset() {
        scale = 1f;
        scaleX = 1f;
        scaleY = 1f;
        rotate = 0;
        rotateX = 0;
        rotateY = 0;
        translateX = 0;
        translateY = 0;
        translateXPercentage = 0;
        translateYPercentage = 0;
        alpha = 255;
    }

    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(mValueAnimator);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        final Callback callback = getCallback();
        if(callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setDrawBounds(bounds);
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {

    }

    @Override
    public void draw(Canvas canvas) {
        float tx = getTranslateX();
        float ty = getTranslateY();
        tx = tx == 0f ? getBounds().width() * getTranslateXPercentage() : tx;
        ty = ty == 0f ? getBounds().height() * getTranslateYPercentage() : ty;

        canvas.translate(tx, ty);
        canvas.scale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
        canvas.rotate(getRotate(), getPivotX(), getPivotY());

        if(getRotateX() != 0f || getRotateY() != 0f) {
            mCamera.save();
            mCamera.rotateX(getRotateX());
            mCamera.rotateY(getRotateY());
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-getPivotX(), -getPivotY());
            mMatrix.postTranslate(getPivotX(), getPivotY());
            mCamera.restore();
            canvas.concat(mMatrix);
        }
        drawSelf(canvas);
    }

    protected abstract void drawSelf(Canvas canvas);

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.RGBA_8888;
    }

    /**
     * 以矩形的中心为中心点，切割一个正方形。
     * 正方形的边长为矩形的最短边。
     * @param rect
     * @return
     */
    public Rect clipSquare(Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int min = Math.min(w, h);
        int cx = rect.centerX();
        int cy = rect.centerY();
        int r = min / 2;
        return new Rect(cx - r, cy - r, cx + r, cy + r);
    }

    public static final Property<RudimentDrawable, Float> SCALE = new FloatProperty<RudimentDrawable>("scale") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setScale(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getScale();
        }
    };

    public static final Property<RudimentDrawable, Float> SCALE_X = new FloatProperty<RudimentDrawable>("scaleX") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setScaleX(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getScaleX();
        }
    };

    public static final Property<RudimentDrawable, Float> SCALE_Y = new FloatProperty<RudimentDrawable>("scaleY") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setScaleY(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getScaleY();
        }
    };

    public static final Property<RudimentDrawable, Float> ROTATE = new FloatProperty<RudimentDrawable>("rotate") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setRotate(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getRotate();
        }
    };

    public static final Property<RudimentDrawable, Float> ROTATE_X = new FloatProperty<RudimentDrawable>("rotateX") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setRotateX(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getRotateX();
        }
    };

    public static final Property<RudimentDrawable, Float> ROTATE_Y = new FloatProperty<RudimentDrawable>("rotateY") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setRotateY(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getRotateY();
        }
    };

    public static final Property<RudimentDrawable, Float> TRANSLATE_X = new FloatProperty<RudimentDrawable>("translateX") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setTranslateX(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getTranslateX();
        }
    };

    public static final Property<RudimentDrawable, Float> TRANSLATE_Y = new FloatProperty<RudimentDrawable>("translateY") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setTranslateY(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getTranslateY();
        }
    };

    public static final Property<RudimentDrawable, Float> TRANSLATE_X_PERCENTAGE = new FloatProperty<RudimentDrawable>("translateXPercentage") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setTranslateXPercentage(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getTranslateXPercentage();
        }
    };

    public static final Property<RudimentDrawable, Float> TRANSLATE_Y_PERCENTAGE = new FloatProperty<RudimentDrawable>("translateYPercentage") {
        @Override
        public void setValue(RudimentDrawable object, float value) {
            object.setTranslateYPercentage(value);
        }

        @Override
        public Float get(RudimentDrawable object) {
            return object.getTranslateYPercentage();
        }
    };

    public static final Property<RudimentDrawable, Integer> ALPHA = new IntProperty<RudimentDrawable>("alpha") {
        @Override
        public void setValue(RudimentDrawable object, int value) {
            object.setAlpha(value);
        }

        @Override
        public Integer get(RudimentDrawable object) {
            return object.getAlpha();
        }
    };

    public static final Property<RudimentDrawable, Integer> COLOR = new IntProperty<RudimentDrawable>("color") {
        @Override
        public void setValue(RudimentDrawable object, int value) {
            object.setColor(value);
        }

        @Override
        public Integer get(RudimentDrawable object) {
            return object.getColor();
        }
    };
}