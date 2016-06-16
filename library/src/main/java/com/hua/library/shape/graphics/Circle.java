package com.hua.library.shape.graphics;

import com.hua.library.rudiment.RudimentDrawable;
import com.hua.library.shape.CircleContainer;
import com.hua.library.shape.CircleShape;

/**
 * Created by 华忠伟 on 2016/6/16.
 */
public class Circle extends CircleContainer {

    public static final long duration = 1000;

    @Override
    protected RudimentDrawable[] onCreateChild() {
        RudimentDrawable[] smallCircle = new RudimentDrawable[20];
        int count  = smallCircle.length;
        for(int i = 0; i < count; i++) {
            smallCircle[i] = new CircleShape();
        }
        return smallCircle;
    }

}
