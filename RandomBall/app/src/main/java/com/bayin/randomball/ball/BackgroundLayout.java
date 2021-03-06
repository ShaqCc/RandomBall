package com.bayin.randomball.ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
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
    private static final int SHOOTED = 666;//产生中奖的小球
    private Bitmap mBoxbitmap;
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
                case SHOOTED:
                    produceShootedBall();
                    sendEmptyMessageDelayed(SHOOTED, 1000);
                    break;
            }
        }
    };


    private int mScreenHeight;
    private int mBoxRight;
    private int mBoxTop;
    private int mBoxBottom;
    private int mBoxLeft;
    private int mLeftBall;
    private int mCenterBall;
    private int mRightBall;
    private int mAboveBallEndY;
    private int mBelowBallEndY;
    private boolean mIsShooted;
    private int index = 0;
    private int tag = 0;
    private int randomDegree = 20;
    private MyCounter myCounter;
    private int overtime = 5000;
    private boolean isFinish = false;
    private int shootedBallIndex = 2;


    /**
     * 制造小球
     */
    private void produceball() {
        int random = RandomUtils.getRandom(9);
        //随机角度
        tag++;
        float degree;
        switch (tag % 2) {
            case 0:
                degree = -RandomUtils.getRandom(randomDegree);
                break;
            default:
                degree = RandomUtils.getRandom(randomDegree);
                break;
        }
        FlyBall flyBall = new FlyBall(getContext(), arrDrawable[random], random, degree, false);
        addView(flyBall);
        flyBall.startFlyAnimation(mScreenHeight);
    }

    /**
     * 产生中奖的小球
     */
    private void produceShootedBall() {
        if (shootedBallIndex >= 0) {
            FlyBall childAt = (FlyBall) getChildAt(shootedBallIndex);
            childAt.setVisibility(VISIBLE);
            switch (shootedBallIndex) {
                case 0:
                    childAt.startFlyAnimation(mAboveBallEndY);
                    break;
                case 1:
                    childAt.startFlyAnimation(mAboveBallEndY);
                    break;
                case 2:
                    childAt.startFlyAnimation(mBelowBallEndY);
                    break;
            }
        }
        shootedBallIndex--;

//        mIsShooted = true;
//        int random = RandomUtils.getRandom(9);
//        //随机角度
//        tag++;
//        float degree;
//        switch (tag % 2) {
//            case 0:
//                degree = -RandomUtils.getRandom(randomDegree);
//                break;
//            default:
//                degree = RandomUtils.getRandom(randomDegree);
//                break;
//        }
//        FlyBall flyBall = new FlyBall(getContext(), arrDrawable[random], random, degree, mIsShooted);
//
//        switch (index) {
//            case 0:
//            case 1:
//                addView(flyBall, index + 1);
//                flyBall.startFlyAnimation(mAboveBallEndY);
//                break;
//            case 2:
//                addView(flyBall, 0);
//                flyBall.startFlyAnimation(mBelowBallEndY);
//                break;
//        }

    }

    public BackgroundLayout(Context context) {
        this(context, null);
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mScreenHeight = ScreenUtils.getScreenHeight(context);

        mBoxbitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.box);
        //箱子的范围
        mBoxLeft = (mScreenWidth - mBoxbitmap.getWidth()) / 2;
        mBoxBottom = (int) (mScreenHeight * 0.6);
        mBoxTop = mBoxBottom - mBoxbitmap.getHeight();
        mBoxRight = mBoxLeft + mBoxbitmap.getWidth();
        //初始化中奖球的起始点
        initShootedBallPosition();
        mPaint = new Paint();
        //初始化中奖小球，摆放好位置
        initShootedBall();
    }

    /**
     * 初始化中奖小球的初始点
     */
    private void initShootedBallPosition() {
        mLeftBall = (int) (mBoxLeft + mBoxbitmap.getWidth() * 0.1);
        mCenterBall = (int) (mScreenWidth / 2 - mScreenWidth * 0.07);
        mRightBall = (int) (mBoxRight - mScreenWidth * 0.12 - mBoxbitmap.getWidth() * 0.04);

        mAboveBallEndY = (int) (mBoxBottom - mBoxbitmap.getHeight() * 0.5 - mScreenWidth * 0.12);
        mBelowBallEndY = (int) (mBoxBottom - mScreenWidth * 0.12 - mBoxbitmap.getHeight() * 0.35);
    }

    /**
     * 添加，初始化中奖小球
     */
    private void initShootedBall() {
        for (int i = 0; i <= 2; i++) {
            int random = RandomUtils.getRandom(9);
            //随机角度
            tag++;
            float degree;
            switch (tag % 2) {
                case 0:
                    degree = -RandomUtils.getRandom(randomDegree);
                    break;
                default:
                    degree = RandomUtils.getRandom(randomDegree);
                    break;
            }
            //添加小球
            FlyBall ball = new FlyBall(getContext(), arrDrawable[random], random, degree, true);
            ball.setVisibility(INVISIBLE);
            addView(ball, i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBoxbitmap, mBoxLeft, mBoxTop, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        if (!isFinish) {
            if (getChildCount() > 0) {
                int childIndex = getChildCount() - 1;
                View childAt = getChildAt(childIndex);
                switch (childIndex) {
                    case 0:
                    case 1:
                    case 2:
                        getChildAt(0).layout(mRightBall, 0, mRightBall + childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                        getChildAt(1).layout(mLeftBall, 0, mLeftBall + childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                        getChildAt(2).layout(mCenterBall, 0, mCenterBall + childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                        break;
                    default:
                        int l = RandomUtils.getRandom((int) (mScreenWidth - mScreenWidth * 0.2));
                        childAt.layout(l, 0, l + childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                        break;
                }
            }
        }
    }

    /**
     * 方法入口
     */
    public void startProduceBall() {
        //每隔200毫秒产生一个小球
        myCounter = new MyCounter(overtime, 150);
        myCounter.start();
        //2秒后，产生第一个命中小球
        mHandler.sendEmptyMessageDelayed(SHOOTED, 2000);
    }

    /**
     * 产生正常的小球
     */
    public void normalBall() {
        mHandler.sendEmptyMessage(PRODUCE);
    }


    public void shootedBall() {
        mHandler.sendEmptyMessage(SHOOTED);
    }

    class MyCounter extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            isFinish = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            normalBall();
        }

        @Override
        public void onFinish() {
            myCounter.cancel();
            myCounter = null;
            isFinish = true;
        }
    }
}
