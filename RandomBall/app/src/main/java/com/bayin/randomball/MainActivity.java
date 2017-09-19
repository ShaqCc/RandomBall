package com.bayin.randomball;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bayin.randomball.ball.BackgroundLayout;
import com.bayin.randomball.ball.FlyBall;
import com.bayin.randomball.ball.RandomUtils;

public class MainActivity extends Activity {

    private BackgroundLayout mBackgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBackgroundLayout = (BackgroundLayout) findViewById(R.id.background);

//        mBackgroundLayout.startProduceBall();
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackgroundLayout.startProduceBall();
            }
        });
        Log.d("xxx","现在有子view："+mBackgroundLayout.getChildCount());
//
//        findViewById(R.id.bt_shooted).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBackgroundLayout.shootedBall();
//            }
//        });
    }
}
