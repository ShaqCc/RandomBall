package com.bayin.randomball.ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bayin.randomball.R;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/9/18.
 ****************************************/

public class BackgroundLayout extends FrameLayout {

    private static final int PRODUCE = 1;//产生小球
    private Bitmap mBitmap;
    private Paint mPaint;
    private int mScreenWidth;
    private final int[] arrDrawable = {R.mipmap.ball_0_2x, R.mipmap.ball_1_2x, R.mipmap.ball_2_2x, R.mipmap.ball_3_2x
            , R.mipmap.ball_4_2x, R.mipmap.ball_5_2x, R.mipmap.ball_6_2x, R.mipmap.ball_7_2x
            , R.mipmap.ball_8_2x, R.mipmap.ball_9_2x};


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRODUCE:
                    produceball();
                    break;
            }
        }
    };
    /**
     * 制造小球
     */
    private void produceball() {
        int random = RandomUtils.getRandom(9);
        FlyBall flyBall = new FlyBall(getContext(), arrDrawable[random], random, false);
        addView(flyBall);
        flyBall.startFlyAnimation();
    }

    public BackgroundLayout(Context context) {
        this(context, null);
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.box);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 350, 700, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() > 0) {
            View childAt = getChildAt(getChildCount() - 1);
            int l = RandomUtils.getRandom(mScreenWidth);
            childAt.layout(l, 0, l + childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
        }
    }

    public void startProduceBall() {
        mHandler.sendEmptyMessage(PRODUCE);
    }
}
