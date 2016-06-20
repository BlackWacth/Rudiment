package com.hua;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.hua.library.shape.WaveShape;
import com.hua.library.shape.graphics.Circle;
import com.hua.library.shape.graphics.Wave;
import com.hua.weight.WaveView;

public class MainActivity extends AppCompatActivity {

//    private ImageView iv_circle;
//    private Wave wave;
//    private WaveShape mWaveShape;

    private WaveView mWaveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        iv_circle = (ImageView) findViewById(R.id.iv_circle);
////        Circle circle = new Circle();
////        circle.setColor(0xff00897b);
////        circle.start();
//        wave = new Wave();
//        wave.setColor(0xff00897b);
//        wave.start();
////        mWaveShape = new WaveShape();
////        mWaveShape.setColor(0xff00897b);
////        mWaveShape.start();
//        iv_circle.setImageDrawable(wave);
////        iv_circle.animate().scaleX(10f).scaleY(10f).setDuration(2000).start();
        mWaveView = (WaveView) findViewById(R.id.wave_view);
        mWaveView.setDuration(5000);
        mWaveView.setStyle(Paint.Style.STROKE);
        mWaveView.setSpeed(400);
        mWaveView.setColor(0xff00897b);
        mWaveView.setInterpolator(new AccelerateDecelerateInterpolator());
        mWaveView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWaveView.stop();
//        wave.stop();
//        mWaveShape.stop();
    }
}
