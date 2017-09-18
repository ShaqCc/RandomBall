package com.bayin.randomball;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
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
        findViewById(R.id.bt_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int random = RandomUtils.getRandom(1080);
//                Point point = new Point(random, 0);
//                FlyBall flyBall = new FlyBall(MainActivity.this, R.mipmap.ball_4_2x, 4, false);
//                mBackgroundLayout.addView(flyBall);
//                flyBall.startFlyAnimation();
                mBackgroundLayout.startProduceBall();
            }
        });
    }
}
