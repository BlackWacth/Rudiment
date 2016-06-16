package com.hua.library.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;

import com.hua.library.rudiment.RudimentDrawable;

public class AnimationUtils {

    public static void start(Animator animator) {
        if(animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    public static void stop(Animator animator) {
        if(animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    public static void start(RudimentDrawable... RudimentDrawables) {
        for(RudimentDrawable rudiment : RudimentDrawables){
            rudiment.start();
        }
    }

    public static void stop(RudimentDrawable... RudimentDrawables) {
        for(RudimentDrawable rudiment : RudimentDrawables){
            rudiment.stop();
        }
    }

    public static boolean isRunning(RudimentDrawable... RudimentDrawables) {
        for(RudimentDrawable rudiment : RudimentDrawables) {
            if(rudiment.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStarted(ValueAnimator animator) {
        return animator != null && animator.isStarted();
    }

    public static boolean isRunning(ValueAnimator animator) {
        return animator != null && animator.isRunning();
    }

}
