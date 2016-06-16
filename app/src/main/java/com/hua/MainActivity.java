package com.hua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hua.library.shape.graphics.Circle;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        Circle circle = new Circle();
        circle.setColor(0xff00897b);
        circle.start();
        iv_circle.setImageDrawable(circle);
    }
}
